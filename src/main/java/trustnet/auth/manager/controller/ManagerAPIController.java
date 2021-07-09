package trustnet.auth.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.manager.code.ManagerResultEnum;
import trustnet.auth.manager.controller.dto.ControllerInfoDTO;
import trustnet.auth.manager.controller.dto.ControllerSettingFileDTO;
import trustnet.auth.manager.controller.dto.DBInfoHistoryDTO;
import trustnet.auth.manager.controller.dto.ManagerCheckInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerCreateFileDTO;
import trustnet.auth.manager.controller.dto.ManagerDBCheckDTO;
import trustnet.auth.manager.controller.dto.ManagerDBInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerHistoryInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.manager.service.ManagerService;
import trustnet.auth.manager.service.vo.ControllerInfoVO;
import trustnet.auth.manager.service.vo.ControllerSettingFileVO;
import trustnet.auth.manager.service.vo.DBInfoHistoryVO;
import trustnet.auth.manager.service.vo.ManagerCreateFileVO;
import trustnet.auth.manager.service.vo.ManagerDBInfoVO;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.user.controller.except.TokenValidationException;
import trustnet.auth.zone.code.ZoneResultEnum;
import trustnet.auth.zone.controller.dto.ZoneInfoResponseDTO;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerAPIController {

	private final ManagerService enrollService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;

	@PostMapping("/manager")
	public CommonResponseDTO<Object> enrollManager(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerInfoDTO managerInfoDTO, BindingResult errors)
			throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("Post Manager => " + securityVO.getUser_id());
		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}
		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);

		String name = vo.getTam_name();
		String ip = vo.getTam_local_ip();
		int tam_port = vo.getTam_local_port();
		int tam_adm_port = vo.getTam_adm_port();
		String license = Integrity.generateTAMlicense(ip);
		String hmac = Integrity.generateTAMDbIntegrity(name, ip, tam_port, tam_adm_port,
				license);
		vo.setTam_license(license);
		vo.setHmac(hmac);

		boolean result = enrollService.saveManagerInfo(vo);
		ManagerResultEnum resultEnum = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(resultEnum);

		if (!result) {
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		if (securityVO.getUser_no() == 0)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		ManagerInfoVO findVO = enrollService.findManager(vo);
		ManagerHistoryInfoVO historyVO = modelMapper.map(findVO,
				ManagerHistoryInfoVO.class);
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		historyVO.setAction("C");
		result = enrollService.saveManagerHistoryInfo(historyVO);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/manager")
	public CommonResponseDTO<Object> checkManager(
			@Validated ManagerCheckInfoDTO managerInfoDTO, BindingResult errors) {

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);
		boolean result = enrollService.isFindManager(vo);
		ManagerResultEnum err = result ? ManagerResultEnum.EXISTMANAGER
				: ManagerResultEnum.NOEXISTMANAGER;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(err);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/manager")
	public CommonResponseDTO<Object> updateManager(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerInfoDTO managerInfoDTO, BindingResult errors)
			throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("Put Manager => " + securityVO.getUser_id());
		if (managerInfoDTO.getTam_local_port() == 0
				|| managerInfoDTO.getTam_adm_port() == 0) {
			throw new ParameterException();
		}

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);

		String name = vo.getTam_name();
		String ip = vo.getTam_local_ip();
		int tam_port = vo.getTam_local_port();
		String string_tam_port = Integer.toString(tam_port);
		int tam_adm_port = vo.getTam_adm_port();
		String string_admin_port = Integer.toString(tam_adm_port);
		String license = Integrity.generateTAMlicense(ip);
		String hmac = Integrity.generateTAMDbIntegrity(name, ip, tam_port, tam_adm_port,
				license);
		vo.setTam_license(license);
		vo.setHmac(hmac);
		boolean result = enrollService.updateManagerWithID(vo);
		ManagerResultEnum retEnum = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		if (securityVO.getUser_no() == 0)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		ManagerInfoVO findVO = enrollService.findManager(vo);
		ManagerHistoryInfoVO historyVO = modelMapper.map(findVO,
				ManagerHistoryInfoVO.class);
		String actionValue = historyVO.getExist().equalsIgnoreCase("Y") ? "U" : "D";
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		historyVO.setAction(actionValue);
		result = enrollService.saveManagerHistoryInfo(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/manager")
	public CommonResponseDTO<Object> deleteManager(ManagerInfoDTO managerInfoDTO) {

		if (String.valueOf(managerInfoDTO.getTam_no()).isEmpty()) {
			throw new ParameterException();
		}

		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);
		boolean result = enrollService.deleteManagerWithID(vo);
		ManagerResultEnum retEnum = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.DELETEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(retEnum);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/manager/db")
	public CommonResponseDTO<Object> enrollDBInfo(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerDBInfoDTO dto, BindingResult errors)
			throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0)
			throw new TokenValidationException();

		if (!(securityVO.getPerm_no() == 1)) {
			throw new TokenValidationException();
		}
		log.info("Post DB => " + securityVO.getUser_id());
		String pub_ip = dto.getDb_pub_ip();
		Integer pub_port = dto.getDb_pub_port();

		ManagerDBInfoVO vo = new ManagerDBInfoVO();
		vo = modelMapper.map(dto, ManagerDBInfoVO.class);
		String key = vo.getDb_priv_ip().trim();
		String id = vo.getDb_id().trim();
		String pw = vo.getDb_passwd().trim();
		String name = vo.getDb_name().trim();
		String db_id = Integrity.tasEncrypt(key, id);
		String db_passwd = Integrity.tasEncrypt(key, pw);
		String db_name = Integrity.tasEncrypt(key, name);
		vo.setDb_id(db_id);
		vo.setDb_passwd(db_passwd);
		vo.setDb_name(db_name);
		boolean result = enrollService.saveManagerDBInfo(vo);
		ManagerResultEnum resultEnum = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(resultEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		DBInfoHistoryVO historyVO = new DBInfoHistoryVO();

		historyVO.setDb_svc_name(dto.getDb_svc_name());
		historyVO.setDb_type_no(dto.getDb_type_no());
		historyVO.setDb_priv_ip(dto.getDb_priv_ip());
		historyVO.setDb_priv_port(dto.getDb_priv_port());
		historyVO.setDb_pub_ip(dto.getDb_pub_ip());
		historyVO.setDb_pub_port(dto.getDb_pub_port());
		historyVO.setDb_id(db_id);
		historyVO.setDb_passwd(db_passwd);
		historyVO.setDb_name(db_name);
		historyVO.setAction("C");
		historyVO.setIssuer_user_no(securityVO.getUser_no());

		boolean historyResult = enrollService.saveDBInfoHistory(historyVO);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/manager/db")
	public CommonResponseDTO<Object> updateDB(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerDBInfoDTO dto, BindingResult errors)
			throws TNAuthException {

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0)
			throw new TokenValidationException();
		if (!(securityVO.getPerm_no() == 1)) {
			throw new TokenValidationException();
		}
		log.info("Put DB => " + securityVO.getUser_id());
		if (dto.getDb_svc_name() == "" || dto.getDb_priv_ip() == ""
				|| dto.getDb_priv_port() < 0 || dto.getDb_priv_port() > 65535) {
			throw new ParameterException();
		}

		ManagerDBInfoVO vo = modelMapper.map(dto, ManagerDBInfoVO.class);
		String key = vo.getDb_priv_ip().trim();
		String db_id = vo.getDb_id().trim();
		String enc_id = Integrity.tasEncrypt(key, db_id);
		String db_passwd = vo.getDb_passwd().trim();
		String enc_passwd = Integrity.tasEncrypt(key, db_passwd);
		String db_name = vo.getDb_name().trim();
		String enc_name = Integrity.tasEncrypt(key, db_name);
		vo.setDb_id(enc_id);
		vo.setDb_passwd(enc_passwd);
		vo.setDb_name(enc_name);
		boolean result = enrollService.updateDB(vo);
		ManagerResultEnum retEnum = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(retEnum);
		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		DBInfoHistoryVO historyVO = new DBInfoHistoryVO();

		historyVO.setDb_svc_name(dto.getDb_svc_name());
		historyVO.setDb_type_no(dto.getDb_type_no());
		historyVO.setDb_priv_ip(dto.getDb_priv_ip());
		historyVO.setDb_priv_port(dto.getDb_priv_port());
		historyVO.setDb_pub_ip(dto.getDb_pub_ip());
		historyVO.setDb_pub_port(dto.getDb_pub_port());
		historyVO.setDb_id(enc_id);
		historyVO.setDb_passwd(enc_passwd);
		historyVO.setDb_name(enc_name);
		historyVO.setAction("U");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		boolean historyResult = enrollService.saveDBInfoHistory(historyVO);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/manager/db")
	public CommonResponseDTO<Object> deleteZone(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerDBInfoDTO dto, BindingResult errors)
			throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0)
			throw new TokenValidationException();
		if (!(securityVO.getPerm_no() == 1)) {
			throw new TokenValidationException();
		}
		log.info("Put DB => " + securityVO.getUser_id());
		if (String.valueOf(dto.getDb_svc_name()).isEmpty()) {
			throw new ParameterException();
		}

		ManagerDBInfoVO vo = modelMapper.map(dto, ManagerDBInfoVO.class);

		ManagerDBInfoVO voList = enrollService.getDBInfo(vo);
		boolean result = enrollService.deleteDBInfo(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.DELETEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		DBInfoHistoryVO historyVO = new DBInfoHistoryVO();
		historyVO.setDb_no(voList.getDb_no());
		historyVO.setDb_svc_name(vo.getDb_svc_name());
		historyVO.setDb_type_no(voList.getDb_type_no());
		historyVO.setDb_priv_ip(voList.getDb_priv_ip());
		historyVO.setDb_priv_port(voList.getDb_priv_port());
		historyVO.setDb_pub_ip(voList.getDb_pub_ip());
		historyVO.setDb_pub_port(voList.getDb_pub_port());

		historyVO.setDb_id(voList.getDb_id());
		historyVO.setDb_passwd(voList.getDb_passwd());
		historyVO.setDb_name(voList.getDb_name());
		historyVO.setAction("D");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		boolean historyResult = enrollService.saveDBInfoHistoryDel(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/db/history")
	public JSONObject getDBHistory(@Validated DBInfoHistoryDTO dto,
			HttpServletRequest request) throws Exception {
		DBInfoHistoryVO vo = modelMapper.map(dto, DBInfoHistoryVO.class);
		List<DBInfoHistoryVO> volist = enrollService.getDBHistory(vo);
		int totalCount = enrollService.countDBHistory();
		String strcount = Integer.toString(totalCount);

		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);

		return result;
	}

	@GetMapping("/manager/db")
	public CommonResponseDTO<Object> checkServiceName(@Validated ManagerDBCheckDTO dto,
			BindingResult errors) {
		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}
		ManagerDBInfoVO vo = modelMapper.map(dto, ManagerDBInfoVO.class);
		boolean result = enrollService.isFindSvcName(vo);
		ManagerResultEnum err = result ? ManagerResultEnum.EXISTSVCNAME
				: ManagerResultEnum.NOEXISTSVCNAME;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(err);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/agent/private")
	public String[] agentPrivate(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerInfoDTO dto, BindingResult errors) throws TNAuthException {
		ManagerInfoVO vo = modelMapper.map(dto, ManagerInfoVO.class);
		ManagerInfoVO voList = enrollService.getAgentPrivate(vo);
		String priv_ip = voList.getTam_local_ip();
		int pr_port = voList.getTam_local_port();
		String pub_ip = voList.getTam_pub_ip();
		int pub_po = voList.getTam_pub_port();
		String priv_port = Integer.toString(pr_port);
		String pub_port = Integer.toString(pub_po);
		String result[] = { priv_ip, priv_port, pub_ip, pub_port };
		return result;
	}

	@GetMapping("/manager/priv-ip")
	public String getPrivateIp(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerInfoDTO dto, BindingResult errors) throws TNAuthException {
		ManagerInfoVO vo = modelMapper.map(dto, ManagerInfoVO.class);
		ManagerInfoVO getIp = enrollService.getPrivIp(vo);
		ManagerInfoDTO privIp = modelMapper.map(getIp, ManagerInfoDTO.class);

		return privIp.getTam_local_ip();
	}

	@GetMapping("/manager/service-name")
	public String[] getDBInfo(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ManagerDBInfoDTO dto, BindingResult errors)
			throws TNAuthException {
		ManagerDBInfoVO vo = modelMapper.map(dto, ManagerDBInfoVO.class);
		ManagerDBInfoVO info = enrollService.getDBInfo(vo);
		ManagerDBInfoDTO dbinfo = modelMapper.map(info, ManagerDBInfoDTO.class);

		int db_type_no = dbinfo.getDb_type_no();
		String stringdbtypeno = Integer.toString(db_type_no);
		String db_priv_ip = dbinfo.getDb_priv_ip();
		int db_priv_port = dbinfo.getDb_priv_port();
		String stringdbprivport = Integer.toString(db_priv_port);
		String db_id = dbinfo.getDb_id();
		String db_pwd = dbinfo.getDb_passwd();
		String db_name = dbinfo.getDb_name();

		String key = dbinfo.getDb_priv_ip();

		String dec_id = Integrity.tasDecrypt(key, db_id);
		String dec_pwd = Integrity.tasDecrypt(key, db_pwd);
		String dec_name = Integrity.tasDecrypt(key, db_name);

		String result[] = { stringdbtypeno, db_priv_ip, stringdbprivport, dec_id, dec_pwd,
				dec_name };
		return result;

	}

	@GetMapping("/manager/setting") // manager 환경 파일생성
	public String[] createSettingFile(ManagerCreateFileDTO dto) throws TNAuthException {
		if (dto.getTam_ip() == "" || dto.getComm_type() == "" || dto.getDb_type() == ""
				|| dto.getDb_addr() == "" || dto.getDb_port() == ""
				|| dto.getDb_port() == "" || dto.getDb_id() == ""
				|| dto.getDb_name() == "") {
			throw new ParameterException();
		}

		ManagerCreateFileVO vo = modelMapper.map(dto, ManagerCreateFileVO.class);
		String tam_name = vo.getTam_name().trim();
		String tam_ip = vo.getTam_ip().trim();
		String comm_type = vo.getComm_type().trim();
		String db_type = vo.getDb_type().trim();
		String db_ip = vo.getDb_ip().trim();
		String db_port = vo.getDb_port();
		String db_id = vo.getDb_id().trim();
		String db_pwd = vo.getDb_pwd().trim();
		String db_name = vo.getDb_name().trim();
		String enc_ip = Integrity.tasEncrypt(tam_name, db_ip);
		String enc_port = Integrity.tasEncrypt(tam_name, db_port);
		String enc_id = Integrity.tasEncrypt(tam_name, db_id);
		String enc_pwd = Integrity.tasEncrypt(tam_name, db_pwd);
		String enc_name = Integrity.tasEncrypt(tam_name, db_name);
		int dbport = Integer.parseInt(db_port);
		String hmac = Integrity.generateTAMConfIntegrity(tam_name, tam_ip, comm_type,
				db_type, db_ip, dbport, db_id, db_pwd, db_name);

		String db[] = { tam_name, tam_ip, comm_type, db_type, enc_ip, enc_port, enc_id,
				enc_pwd, enc_name, hmac };
		return db;
	}

	@GetMapping("/manager/read-file") // -> //verify-content 로 변경

	public CommonResponseDTO<Object> ManagerVerifyFile(
			@Validated ManagerCreateFileDTO dto, BindingResult errors)
			throws TNAuthException {
		ManagerCreateFileVO vo = modelMapper.map(dto, ManagerCreateFileVO.class);
		boolean result = enrollService.verifySettingFile(vo);
		ManagerResultEnum err = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.JNI_ERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/manager/verify-content") // Manager 환경 파일검증
	public CommonResponseDTO<Object> ManagerVerifyContent(
			@Validated ManagerCreateFileDTO dto, BindingResult errors)
			throws TNAuthException {

		ManagerCreateFileVO vo = modelMapper.map(dto, ManagerCreateFileVO.class);
		String tam_name = vo.getTam_name().trim();
		String tam_ip = vo.getTam_ip().trim();
		String comm_type = vo.getComm_type().trim();
		String db_type = vo.getDb_type().trim();
		String db_addr = vo.getDb_addr().trim();
		String dbport = vo.getDb_port().trim();
		String dec_port = Integrity.tasDecrypt(tam_name, dbport);
		int port = Integer.parseInt(dec_port);

		String db_id = vo.getDb_id().trim();
		String db_pwd = vo.getDb_pwd().trim();
		String db_name = vo.getDb_name().trim();

		String dec_addr = Integrity.tasDecrypt(tam_name, db_addr);
		String dec_id = Integrity.tasDecrypt(tam_name, db_id);
		String dec_pwd = Integrity.tasDecrypt(tam_name, db_pwd);
		String dec_name = Integrity.tasDecrypt(tam_name, db_name);

		String hmac = vo.getHmac();
		String createhmac = Integrity.generateTAMConfIntegrity(tam_name, tam_ip,
				comm_type, db_type, dec_addr, port, dec_id, dec_pwd, dec_name);

		String value[] = { tam_name, tam_ip, comm_type, db_type, dec_addr, dec_port,
				dec_id, dec_pwd, dec_name, hmac, createhmac };

		return CommonResponseDTO.builder().data(value).build();
	}

	@GetMapping("/manager/history")
	public JSONObject findAllManagerHistory(@Validated ManagerHistoryInfoDTO dto,
			HttpServletRequest request) throws Exception {
		ManagerHistoryInfoVO vo = modelMapper.map(dto, ManagerHistoryInfoVO.class);
		List<ManagerHistoryInfoVO> volist = enrollService.findAllManagerHistory(vo);

		String filter = vo.getFilter();

		int totalCount = enrollService.countManagerHistory(filter);

		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);

		return result;
	}

	@GetMapping("/controller/check")
	public CommonResponseDTO<Object> checkController(@Validated ControllerInfoDTO dto,
			BindingResult errors) {

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ControllerInfoVO vo = modelMapper.map(dto, ControllerInfoVO.class);
		boolean result = enrollService.isFindController(vo);
		ManagerResultEnum err = result ? ManagerResultEnum.EXISTCONTROLLER
				: ManagerResultEnum.NOEXISTCONTROLLER;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(err);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/controller")
	public CommonResponseDTO<Object> enrollController(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ControllerInfoDTO dto, BindingResult errors)
			throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0) {
			throw new TokenValidationException();
		}
		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}
		log.info("Post Controller => " + securityVO.getUser_id());
		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ControllerInfoVO vo = modelMapper.map(dto, ControllerInfoVO.class);

		String name = vo.getCtrl_name();
		String ip = vo.getLocal_ip();
		int port = vo.getLocal_port();
		int admin_port = vo.getAdmin_port();

		String license = Integrity.generateTACtrllicense(ip);
		String icv = Integrity.generateTACtrlDbIntegrity(name, ip, port, admin_port,
				license);
		vo.setLicense(license);
		vo.setIcv(icv);
		boolean result = enrollService.saveControllerInfo(vo);

		ManagerResultEnum resultEnum = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(resultEnum);

		if (!result) {
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		// 아직은 log table 존재 X (6/17)
		// savehistory flow

		//		ControllerInfoVO findVO = enrollService.findController(vo);
		//		ControllerHistoryVO historyVO = modelMapper.map(findVO,ControllerHistoryVO.class);
		//		historyVO.setIssuer_user_no(securityVO.getUser_no());
		//		historyVO.setAction("C");
		//		result = enrollService.saveControllerHistoryInfo(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/controller")
	public CommonResponseDTO<Object> updateController(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ControllerInfoDTO dto, BindingResult errors)
			throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0) {
			throw new TokenValidationException();
		}
		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}
		log.info("Put Controller => " + securityVO.getUser_id());
		if (dto.getLocal_port() == 0 || dto.getAdmin_port() == 0) {
			log.warn("Controller Port PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ControllerInfoVO vo = modelMapper.map(dto, ControllerInfoVO.class);

		// name, local_ip, local_port, admin_port, watchdog, enable 

		// if > ip change ? > license function 
		// 어떠한것 하나라도 변경할 시 > icv parameter 뭐 들어가는지는 모르겠는데 icv값 변경해서 setting 해줘야함

		vo.setLicense("updateLicenseValue");
		vo.setIcv("updateicvValue");

		boolean result = enrollService.updateControllerWithID(vo);
		ManagerResultEnum retEnum = result ? ManagerResultEnum.SUCCESS
				: ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(retEnum);
		if (!result) {
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		// history action "U" input history
//		ManagerInfoVO findVO = enrollService.findManager(vo);
//		ManagerHistoryInfoVO historyVO = modelMapper.map(findVO,
//				ManagerHistoryInfoVO.class);
//		String actionValue = historyVO.getExist().equalsIgnoreCase("Y") ? "U" : "D";
//		historyVO.setIssuer_user_no(securityVO.getUser_no());
//		historyVO.setAction(actionValue);
//		result = enrollService.saveManagerHistoryInfo(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/controller/file")
	public CommonResponseDTO<Object> ControllerValueEncrypt(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ControllerSettingFileDTO dto, BindingResult errors)
			throws TNAuthException {

		ControllerSettingFileVO vo = modelMapper.map(dto, ControllerSettingFileVO.class);
		int port = vo.getDb_port();
		String db_port = Integer.toString(port);

		String key = vo.getCtrl_name();
		String enc_db_addr = Integrity.tasEncrypt(key, vo.getDb_addr());
		String enc_db_port = Integrity.tasEncrypt(key, db_port);
		String enc_db_id = Integrity.tasEncrypt(key, vo.getDb_id());
		String enc_db_pwd = Integrity.tasEncrypt(key, vo.getDb_pwd());
		String enc_db_name = Integrity.tasEncrypt(key, vo.getDb_name());
		String db_type = vo.getDb_type();

		String hmac = Integrity.generateTACtrlConfIntegrity(vo.getCtrl_name(),
				vo.getLocal_ip(), vo.getDb_type(), vo.getDb_addr(), vo.getDb_port(),
				vo.getDb_id(), vo.getDb_pwd(), vo.getDb_name());

		String[] array = { enc_db_addr, enc_db_port, enc_db_id, enc_db_pwd, enc_db_name,
				hmac };

		return CommonResponseDTO.builder().data(array).build();
	}
}