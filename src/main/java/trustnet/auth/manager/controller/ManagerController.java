package trustnet.auth.manager.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.manager.controller.dto.ControllerInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerCreateFileDTO;
import trustnet.auth.manager.controller.dto.ManagerDBInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerHistoryInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoDTO;
import trustnet.auth.manager.service.ManagerService;
import trustnet.auth.manager.service.vo.ControllerInfoVO;
import trustnet.auth.manager.service.vo.ManagerCreateFileVO;
import trustnet.auth.manager.service.vo.ManagerDBInfoVO;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerController {

	private final ManagerService managerService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;

	@GetMapping("/manager/enrollPage")
	public ModelAndView enrollPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/Enroll");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/manager/listPage")
	public ModelAndView listPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/List");
		List<ManagerInfoVO> voList = managerService.findAllManagerList();
		List<ManagerInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ManagerInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("managerList", dtoList);

		return mav;
	}

	@GetMapping("/manager/updatePage")
	public ModelAndView updatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/Update");
		ManagerInfoVO vo = modelMapper.map(dto, ManagerInfoVO.class);
		ManagerInfoVO retVO = managerService.findManagerWithNO(vo);

		ManagerInfoDTO retDTO = modelMapper.map(retVO, ManagerInfoDTO.class);
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("managerInfo", retDTO);
		return mav;
	}

	@GetMapping("/manager/historyPage")
	public ModelAndView historyPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/History");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/manager/historyDetailPage")
	public ModelAndView historyDetailPage(ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/HistoryDetail");
		ManagerInfoVO vo = modelMapper.map(dto, ManagerInfoVO.class);
		List<ManagerHistoryInfoVO> voList = managerService
				.findAllManagerHistoryAsTamNo(vo);
		List<ManagerHistoryInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ManagerHistoryInfoDTO>>() {
				}.getType());
		mav.addObject("historyList", dtoList);
		return mav;
	}

	@GetMapping("/manager/verifyFilePage")
	public ModelAndView verifyFilePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ManagerInfoDTO dto) {

		ModelAndView mav = new ModelAndView("thymeleaf/manager/VerifyFile");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/manager/enrollDBPage")
	public ModelAndView dbEnrollPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/EnrollDB");
		List<ManagerCreateFileVO> tamNameVOList = managerService.findAllManagerName();
		List<ManagerCreateFileDTO> tamNameDTOList = modelMapper.map(tamNameVOList,
				new TypeToken<List<ManagerHistoryInfoDTO>>() {
				}.getType()); // tam name list 
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("tamList", tamNameDTOList);
		return mav;
	}

	@GetMapping("/manager/listDBPage")
	public ModelAndView listDBPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/ListDB");
		List<ManagerDBInfoVO> voList = managerService.findAllDBList();
		List<ManagerDBInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ManagerDBInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("dbList", dtoList);
		return mav;
	}

	@GetMapping("/manager/dbupdatePage")
	public ModelAndView updatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ManagerDBInfoDTO dto) throws TNAuthException {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/UpdateDB");
		ManagerDBInfoVO vo = modelMapper.map(dto, ManagerDBInfoVO.class);
		ManagerDBInfoVO retVO = managerService.findDBWithNO(vo);

		String key = retVO.getDb_priv_ip().trim();
		String db_id = retVO.getDb_id().trim();
		String db_passwd = retVO.getDb_passwd().trim();
		String db_name = retVO.getDb_name().trim();

		String dec_id = Integrity.tasDecrypt(key, db_id);
		String dec_pwd = Integrity.tasDecrypt(key, db_passwd);
		String dec_name = Integrity.tasDecrypt(key, db_name);
		ManagerDBInfoDTO retDTO = modelMapper.map(retVO, ManagerDBInfoDTO.class);
		retDTO.setDb_id(dec_id);
		retDTO.setDb_passwd(dec_pwd);
		retDTO.setDb_name(dec_name);

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("dbInfo", retDTO);
		return mav;
	}
	//

	@GetMapping("/manager/settingPage")
	public ModelAndView settingPage(ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/Setting");
		List<ManagerCreateFileVO> tamNameVOList = managerService.findAllManagerName();
		List<ManagerCreateFileDTO> tamNameDTOList = modelMapper.map(tamNameVOList,
				new TypeToken<List<ManagerHistoryInfoDTO>>() {
				}.getType()); // tam name list 
		List<ManagerCreateFileVO> svcNameVOList = managerService.findAllServiceName();
		List<ManagerCreateFileDTO> svcNameDTOList = modelMapper.map(svcNameVOList,
				new TypeToken<List<ManagerCreateFileDTO>>() {
				}.getType());

		ManagerInfoVO vo = modelMapper.map(dto, ManagerInfoVO.class);
		log.info("VO MAP LIST TEST : " + vo.toString());
		int tam_no = vo.getTam_no();

		ManagerInfoVO settingupdateVOList = managerService.getTamInfowithNo(tam_no);
		ManagerInfoDTO settingupdateDTOList = modelMapper.map(settingupdateVOList,
				ManagerInfoDTO.class);
		log.info("SETTINGUPDATE DTO TEST : " + settingupdateDTOList.toString());

		mav.addObject("tamInfoList", settingupdateDTOList);
		mav.addObject("tamList", tamNameDTOList);
		mav.addObject("svcList", svcNameDTOList);

		return mav;
	}

	@GetMapping("/manager/createFilePage")
	public ModelAndView createFilePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/CreateFile");
		List<ManagerCreateFileVO> tamNameVOList = managerService.findAllManagerName();
		List<ManagerCreateFileDTO> tamNameDTOList = modelMapper.map(tamNameVOList,
				new TypeToken<List<ManagerHistoryInfoDTO>>() {
				}.getType()); // tam name list 
		List<ManagerCreateFileVO> svcNameVOList = managerService.findAllServiceName();
		List<ManagerCreateFileDTO> svcNameDTOList = modelMapper.map(svcNameVOList,
				new TypeToken<List<ManagerCreateFileDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("tamList", tamNameDTOList);
		mav.addObject("svcList", svcNameDTOList);

		return mav;

	}

	@GetMapping("/manager/dbHistoryPage")
	public ModelAndView dbHistoryPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/HistoryDB");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;

	}

	// ===================================================================== Controller enroll / list / .conf file download page ================================================================================= 

	@GetMapping("/controller/enrollPage")
	public ModelAndView controllerenrollPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/control/ControllerEnroll");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();
		mav.addObject("permCode", perm_code);
		return mav;

	}

	@GetMapping("/controller/listPage")
	public ModelAndView controllerlistPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/control/ControllerList");
		List<ControllerInfoVO> voList = managerService.findAllControllerList();
		List<ControllerInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ControllerInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("ctrlList", dtoList);
		return mav;

	}

	@GetMapping("/controller/updatePage")
	public ModelAndView controllerupdatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ControllerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/control/ControllerUpdate");

		ControllerInfoVO vo = modelMapper.map(dto, ControllerInfoVO.class);
		ControllerInfoVO retVO = managerService.findControllerWithNO(vo);
		ControllerInfoDTO retDTO = modelMapper.map(retVO, ControllerInfoDTO.class);

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("ctrlInfo", retDTO);
		return mav;
	}

	@GetMapping("/controller/filePage")
	public ModelAndView controllerfilePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ControllerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/control/ControllerFile");
		List<ManagerCreateFileVO> tamNameVOList = managerService.findAllManagerName();
		List<ManagerCreateFileDTO> tamNameDTOList = modelMapper.map(tamNameVOList,
				new TypeToken<List<ManagerHistoryInfoDTO>>() {
				}.getType()); // tam name list 
		List<ManagerCreateFileVO> svcNameVOList = managerService.findAllServiceName();
		List<ManagerCreateFileDTO> svcNameDTOList = modelMapper.map(svcNameVOList,
				new TypeToken<List<ManagerCreateFileDTO>>() {
				}.getType());

		ControllerInfoVO vo = modelMapper.map(dto, ControllerInfoVO.class);
		ControllerInfoVO afterVO = managerService.getCtrlInfowithNo(vo);
		ControllerInfoDTO returnDTO = modelMapper.map(afterVO, ControllerInfoDTO.class);

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("ctrlInfo", returnDTO);
		mav.addObject("svcList", svcNameDTOList);

		return mav;
	}

	@GetMapping("/controller/createFilePage")
	public ModelAndView controllerCreateFilePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/control/ControllerCreateFile");

		List<ManagerCreateFileVO> svcNameVOList = managerService.findAllServiceName();
		List<ManagerCreateFileDTO> svcNameDTOList = modelMapper.map(svcNameVOList,
				new TypeToken<List<ManagerCreateFileDTO>>() {
				}.getType());

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("svcList", svcNameDTOList);
		return mav;
	}

}
