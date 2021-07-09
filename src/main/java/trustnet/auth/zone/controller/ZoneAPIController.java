package trustnet.auth.zone.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.naming.NameClassPair;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.user.controller.except.TokenValidationException;
import trustnet.auth.zone.code.ZoneResultEnum;
import trustnet.auth.zone.controller.dto.ZoneAgentCreateFileDTO;
import trustnet.auth.zone.controller.dto.ZoneInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneInfoResponseDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseAllowInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseHistoryInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseStateHistoryInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseUpdateDBDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseUpdateDTO;
import trustnet.auth.zone.controller.dto.ZoneSettingFileDTO;
import trustnet.auth.zone.controller.dto.ZoneTimeLicenseDTO;
import trustnet.auth.zone.controller.dto.ZoneTimeLicenseHistoryDTO;
import trustnet.auth.zone.controller.dto.ZoneVerifyIntegrityDTO;
import trustnet.auth.zone.controller.dto.ZoneWetherLicenseDTO;
import trustnet.auth.zone.repository.entity.ZoneWetherLicenseENTITY;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneAgentCreateFileVO;
import trustnet.auth.zone.service.vo.ZoneHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseAllowInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseStateHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseUpdateDBVO;
import trustnet.auth.zone.service.vo.ZoneLicenseUpdateVO;
import trustnet.auth.zone.service.vo.ZoneSettingFileVO;
import trustnet.auth.zone.service.vo.ZoneTimeLicenseHistoryVO;
import trustnet.auth.zone.service.vo.ZoneTimeLicenseVO;
import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;
import trustnet.auth.zone.service.vo.ZoneWetherLicenseVO;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;
import trustnet.tas.valid.TNLicenseInfo;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ZoneAPIController {

	private static final String String = null;
	private final ZoneService zoneService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;
	private final QRTest qrcode;

	@PostMapping("/zone")
	public CommonResponseDTO<Object> enroll(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneInfoDTO zoneInfoDTO, BindingResult errors) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) {
			throw new TokenValidationException();
		}
		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}

		log.info("Post Zone => " + securityVO.getUser_id());

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneInfoVO vo = modelMapper.map(zoneInfoDTO, ZoneInfoVO.class);
		vo.setIntegrity_value("0");
		vo.setLimited_url("default");

		boolean result = zoneService.saveZoneInfo(vo);
		ZoneResultEnum resultEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.SAVEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(resultEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		ZoneInfoVO infoVO = zoneService.findZoneInfoAsZoneName(vo);
		ZoneHistoryInfoVO historyVO = modelMapper.map(infoVO, ZoneHistoryInfoVO.class);
		historyVO.setAction("C");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/zone")
	public CommonResponseDTO<Object> updateZone(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneInfoDTO zoneInfoDTO, BindingResult errors) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) {
			throw new TokenValidationException();
		}

		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}

		log.info("Update Zone => " + securityVO.getUser_id());
		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneInfoVO vo = modelMapper.map(zoneInfoDTO, ZoneInfoVO.class);

		vo.setLimited_url("default");
		boolean result = zoneService.updateZoneWithID(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.UPDATEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		ZoneInfoVO infoVO = zoneService.findZoneInfoAsZoneName(vo);
		String action = infoVO.getExist().equalsIgnoreCase("Y") ? "U" : "D";
		ZoneHistoryInfoVO historyVO = modelMapper.map(infoVO, ZoneHistoryInfoVO.class);
		historyVO.setAction(action);
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/zone")
	public CommonResponseDTO<Object> deleteZone(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneInfoDTO zoneInfoDTO, BindingResult errors) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) {
			throw new TokenValidationException();
		}

		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}

		log.info("Delete Zone => " + securityVO.getUser_id());

		if (String.valueOf(zoneInfoDTO.getZone_no()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		ZoneInfoVO vo = modelMapper.map(zoneInfoDTO, ZoneInfoVO.class);
		boolean result = zoneService.deleteZoneWithID(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.DELETEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/zone/license")
	public CommonResponseDTO<Object> enrollLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneLicenseInfoDTO dto) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) {
			throw new TokenValidationException();
		}

		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}
		log.info("Post License => " + securityVO.getUser_id());

		if (String.valueOf(dto.getZone_no()).isEmpty() || dto.getTaa_ip().isEmpty()
				|| String.valueOf(dto.getLicense_type()).isEmpty()) {
			log.warn("/zone/license PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		ZoneLicenseInfoVO vo = modelMapper.map(dto, ZoneLicenseInfoVO.class);
		try {
			boolean result = zoneService.saveZoneLicenseInfo(vo);

			ZoneResultEnum resultEnum = result ? ZoneResultEnum.SUCCESS
					: ZoneResultEnum.SAVEERROR;
			ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(resultEnum);

			if (!result) {
				return CommonResponseDTO.builder().data(resDTO).build();
			}

			ZoneLicenseHistoryInfoVO historyVO = modelMapper.map(vo,
					ZoneLicenseHistoryInfoVO.class);
			historyVO.setAction("C");
			historyVO.setIssuer_user_no(securityVO.getUser_no());
			result = zoneService.saveZoneLicenseHistory(historyVO);

			return CommonResponseDTO.builder().data(resDTO).build();

		} catch (Exception e) {
			JSONObject err = new JSONObject();
			err.put("errorCode", -1);
			err.put("errorMessage", "이미 등록된 IP주소입니다");
			return CommonResponseDTO.builder().data(err).build();
		}

	}

	@GetMapping("/zone/license")
	public JSONObject getLicenseState(@Validated ZoneLicenseAllowInfoDTO dto,
			HttpServletRequest request) throws Exception {
		ZoneLicenseAllowInfoVO vo = modelMapper.map(dto, ZoneLicenseAllowInfoVO.class);
		List<ZoneLicenseAllowInfoVO> volist = zoneService.findAllZoneList(vo);
		int totalCount = zoneService.countZoneInfo();
		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);
		return result;
	}

	@GetMapping("/zone/other")
	CommonResponseDTO<Object> getOtherContent(@Validated ZoneLicenseAllowInfoDTO dto,
			HttpServletRequest request) throws Exception {

		ZoneLicenseAllowInfoVO vo = modelMapper.map(dto, ZoneLicenseAllowInfoVO.class);
		log.info("/zone/other dto toString > " + dto.toString());
		ZoneLicenseAllowInfoVO result = zoneService.getOtherContent(vo);

		ZoneLicenseAllowInfoDTO retDTO = modelMapper.map(result,
				ZoneLicenseAllowInfoDTO.class);

		return CommonResponseDTO.builder().data(retDTO).build();
	}

	@PutMapping("/zone/license")
	public CommonResponseDTO<Object> updateLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneLicenseUpdateDTO dto) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		if (dto.getAsis_taa_ip() == "" || dto.getAsis_license_type() == 0) {
			log.warn("PARAMETER EXCEPTION");
		}
		log.info("Put License => " + securityVO.getUser_id());

		ZoneLicenseUpdateVO vo = modelMapper.map(dto, ZoneLicenseUpdateVO.class);

		boolean result = zoneService.updateZoneLicenseInfo(vo);

		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.FAIL;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result) {
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneLicenseInfoVO infoVO = modelMapper.map(vo, ZoneLicenseInfoVO.class);
		ZoneLicenseInfoVO retinfoVO = zoneService.findZoneLicenseAsZoneNONTaaIP(infoVO);
		ZoneLicenseHistoryInfoVO historyVO = modelMapper.map(retinfoVO,
				ZoneLicenseHistoryInfoVO.class);
		historyVO.setAction("U");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneLicenseHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/zone/license")
	public CommonResponseDTO<Object> deleteLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneLicenseInfoDTO dto) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		if (String.valueOf(dto.getZone_no()).isEmpty() || dto.getTaa_ip().isEmpty()
				|| String.valueOf(dto.getLicense_type()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		log.info("Delete License => " + securityVO.getUser_id());

		ZoneLicenseInfoVO vo = modelMapper.map(dto, ZoneLicenseInfoVO.class);
		ZoneLicenseInfoVO infoVO = modelMapper.map(vo, ZoneLicenseInfoVO.class);
		ZoneLicenseInfoVO retinfoVO = zoneService.findZoneLicenseAsZoneNONTaaIP(infoVO);

		boolean result = zoneService.deleteZoneLicenseInfo(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.FAIL;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result) {
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneLicenseHistoryInfoVO historyVO = modelMapper.map(retinfoVO,
				ZoneLicenseHistoryInfoVO.class);
		historyVO.setAction("D");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneLicenseHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/zone")
	public CommonResponseDTO<Object> checkZoneName(@Validated ZoneInfoDTO dto,
			BindingResult errors) {

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}
		ZoneInfoVO vo = modelMapper.map(dto, ZoneInfoVO.class);
		boolean result = zoneService.isFindZone(vo);

		ZoneResultEnum retEnum = result ? ZoneResultEnum.EXISTFAIL
				: ZoneResultEnum.EXISTSUCCESS;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/zone/verify")
	public CommonResponseDTO<Object> ZoneVerifyIntegrity(
			@Validated ZoneVerifyIntegrityDTO dto, BindingResult errors)
			throws TNAuthException {

		ZoneVerifyIntegrityVO vo = modelMapper.map(dto, ZoneVerifyIntegrityVO.class);

		TNLicenseInfo lc = new TNLicenseInfo();

		String asiszonename = vo.getZone_name();

		int asisreno = vo.getAsis_revision_no();
		int asisplcnt = vo.getAsis_pl_count();
		int asistplcnt = vo.getAsis_tpl_count();
		String asislimit = vo.getAsis_limited_url();

		int tobereno = asisreno + 1;
		int tobepl = vo.getTobe_pl_count();
		int tobetpl = vo.getTobe_tpl_count();
		String limited_url = vo.getTobe_limited_url();

		lc.sZoneName = asiszonename;
		lc.unAsisRevNo = asisreno;
		lc.unAsisStaticCnt = asisplcnt;
		lc.unAsisDynamicCnt = asistplcnt;
		if (asislimit == null) {
			asislimit = "";
		}

		lc.sAsisLimitUrl = asislimit;
		lc.unTobeRevNo = tobereno; // asis + 1
		lc.unTobeStaticCnt = tobepl; // 빨간 input 
		lc.unTobeDynamicCnt = tobetpl; // 빨간 input 
		if (limited_url == null) {
			limited_url = "";
		}

		lc.sTobeLimitUrl = limited_url;

		try {
			String sReqMsg = Integrity.generateLicenseRequest(lc);
			log.info("Request Message  :" + sReqMsg);
			return CommonResponseDTO.builder().data(sReqMsg).build();
		} catch (Exception e) {
			log.info("테스트 catch flow 실패 "); // JNI  -10004 error ? 
			log.info(e.toString());
			return CommonResponseDTO.builder().data("error").build();
		}

	}

	@GetMapping("/zone/verify-enroll")
	public CommonResponseDTO<Object> ZoneVerifyEnroll(
			@Validated ZoneVerifyIntegrityDTO dto, BindingResult errors)
			throws TNAuthException {
		ZoneVerifyIntegrityVO vo = modelMapper.map(dto, ZoneVerifyIntegrityVO.class);
		String sResponseMsg = vo.getValue();
		TNLicenseInfo lc = new TNLicenseInfo();
		lc = Integrity.verifyLicenseResponse(sResponseMsg);

		String name = lc.sZoneName;
		String message = lc.sType;
		String timestamp = lc.sTimeStamp;

		int zone_no = zoneService.getZoneNoWithZoneName(name);

		ZoneInfoVO infoVO = zoneService.getZoneInfoWithNo(zone_no);
		int maxST = infoVO.getPl_license_cnt();
		int maxDY = infoVO.getTpl_license_cnt();

		int stVO = zoneService.getAgentStaticAllow(zone_no);
		int dyVO = zoneService.getAgentDynamicAllow(zone_no);

		int asisreno = lc.unAsisRevNo; // 기존 개정번호
		int asispl = lc.unAsisStaticCnt; // 기존 STATIC CNT
		int asistpl = lc.unAsisDynamicCnt; // 기존 DYNAMIC CNT
		String asislimited = lc.sAsisLimitUrl; // 기존 LIMITED URL

		int tobereno = lc.unTobeRevNo; // 변경 개정번호
		int tobepl = lc.unTobeStaticCnt; // 변경 STATIC CNT
		int tobetpl = lc.unTobeDynamicCnt; // 변경 DYNAMIC CNT
		String tobelimited = lc.sTobeLimitUrl; // 변경 LIMITED URL

		String integrity = lc.sIntegrity; // integrity

		String stringasreno = Integer.toString(asisreno);
		String stringaspl = Integer.toString(asispl);
		String stringastpl = Integer.toString(asistpl);
		String stringbereno = Integer.toString(tobereno);
		String stringbepl = Integer.toString(tobepl);
		String stringbetpl = Integer.toString(tobetpl);
		String stringstaticVO = Integer.toString(stVO);
		String stringdynamicVO = Integer.toString(dyVO);
		String maxSTATIC = Integer.toString(maxST);
		String maxDYNAMIC = Integer.toString(maxDY);
		String result[] = { name, stringbereno, stringbepl, maxSTATIC, stringbetpl,
				maxDYNAMIC, tobelimited, integrity, stringstaticVO, stringdynamicVO };

		return CommonResponseDTO.builder().data(result).build();
	}

	@PutMapping("/zone/license-enroll")
	public CommonResponseDTO<Object> enrollLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneLicenseUpdateDBDTO dto) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) {
			throw new TokenValidationException();
		}

		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}
		log.info("Put License => " + securityVO.getUser_id());

		ZoneLicenseUpdateDBVO vo = modelMapper.map(dto, ZoneLicenseUpdateDBVO.class);
		boolean result = zoneService.updateLicenseInfo(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.UPDATEERROR;

		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);
		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/zone/agent-file")
	public CommonResponseDTO<Object> ZoneAgentCreateFile(
			@Validated ZoneAgentCreateFileDTO dto, BindingResult errors)
			throws TNAuthException {

		try {
			ZoneAgentCreateFileVO vo = modelMapper.map(dto, ZoneAgentCreateFileVO.class);
			String zone_name = vo.getZone_name();
			String tam_local_ip_1 = vo.getTam_local_ip_1();
			String tam_local_port_1 = vo.getTam_local_port_1();
			String tam_local_ip_2 = vo.getTam_local_ip_2();
			String tam_local_port_2 = vo.getTam_local_port_2();
			String taa_ip = vo.getTaa_ip();
			String license = vo.getLicense();
			String enc_tam_local_ip = Integrity.tasEncrypt(zone_name, tam_local_ip_1);
			String enc_tam_local_port = Integrity.tasEncrypt(zone_name, tam_local_port_1);

			String enc_tam_local_ip2 = Integrity.tasEncrypt(zone_name, tam_local_ip_2);
			String enc_tam_local_port2 = Integrity.tasEncrypt(zone_name,
					tam_local_port_2);
			int intport1 = Integer.parseInt(tam_local_port_1);
			int intport2 = Integer.parseInt(tam_local_port_2);

			String hmac = Integrity.generateTAAConfIntegrity(zone_name, tam_local_ip_1,
					intport1, tam_local_ip_2, intport2, taa_ip, license);
			String taaFile[] = { enc_tam_local_ip, enc_tam_local_port, enc_tam_local_ip2,
					enc_tam_local_port2, hmac };
			return CommonResponseDTO.builder().data(taaFile).build();

		} catch (Exception e) {
			ZoneAgentCreateFileVO vo = modelMapper.map(dto, ZoneAgentCreateFileVO.class);
			String zone_name = vo.getZone_name();
			String tam_local_ip_1 = vo.getTam_local_ip_1();
			String tam_local_port_1 = vo.getTam_local_port_1();
			String taa_ip = vo.getTaa_ip();
			String license = vo.getLicense();
			String enc_tam_local_ip = Integrity.tasEncrypt(zone_name, tam_local_ip_1);
			String enc_tam_local_port = Integrity.tasEncrypt(zone_name, tam_local_port_1);
			int intport1 = Integer.parseInt(tam_local_port_1);

			String tam_local_ip_2 = "";
			int intport2 = 0;

			String hmac = Integrity.generateTAAConfIntegrity(zone_name, tam_local_ip_1,
					intport1, tam_local_ip_2, intport2, taa_ip, license);

			String cat[] = { enc_tam_local_ip, enc_tam_local_port, hmac };

			return CommonResponseDTO.builder().data(cat).build();
		}

	}

	@GetMapping("/zone/read-file")
	public CommonResponseDTO<Object> ZoneAgentVerifyFile(
			@Validated ZoneSettingFileDTO dto, BindingResult errors)
			throws TNAuthException {
		ZoneSettingFileVO vo = modelMapper.map(dto, ZoneSettingFileVO.class);
		boolean result = zoneService.verifyFile(vo);
		ZoneResultEnum err = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.JNI_ERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/zone/verify-content") // Agent 환경파일 검증
	public CommonResponseDTO<Object> ZoneAgentVerifyContent(
			@Validated ZoneSettingFileDTO dto, BindingResult errors)
			throws TNAuthException {

		try {
			ZoneSettingFileVO vo = modelMapper.map(dto, ZoneSettingFileVO.class);
			String zone_name = vo.getZone_name().trim();
			String tam_local_ip_1 = vo.getTam_local_ip_1().trim();
			String tam_local_port_1 = vo.getTam_local_port_1();
			String tam_local_ip_2 = vo.getTam_local_ip_2().trim();
			String tam_local_port_2 = vo.getTam_local_port_2();

			String decPORT = Integrity.tasDecrypt(zone_name, tam_local_port_1);
			int dec_port = Integer.parseInt(decPORT);
			String decPORT2 = Integrity.tasDecrypt(zone_name, tam_local_port_2);
			int dec_port2 = Integer.parseInt(decPORT2);

			String taa_ip = vo.getTaa_ip().trim();
			String license = vo.getLicense().trim();

			String decIP = Integrity.tasDecrypt(zone_name, tam_local_ip_1);
			String decIP2 = Integrity.tasDecrypt(zone_name, tam_local_ip_2);

			String readhmac = dto.getHmac();

			String createhmac = Integrity.generateTAAConfIntegrity(zone_name, decIP,
					dec_port, decIP2, dec_port2, taa_ip, license); // Agent 환경파일 검증 성공

			switch (license) {
			case "정적":
				license = "static";
				break;
			case "동적":
				license = "dynamic";
				break;
			case "시간제한":
				license = "timelimit";
				break;
			default:
				break;
			}
			String value[] = { zone_name, decIP, decPORT, decIP2, decPORT2, taa_ip,
					license, readhmac, createhmac };
			return CommonResponseDTO.builder().data(value).build();
		} catch (Exception e) {
			ZoneSettingFileVO vo = modelMapper.map(dto, ZoneSettingFileVO.class);

			String zone_name = vo.getZone_name().trim();
			String tam_local_ip_1 = vo.getTam_local_ip_1().trim();
			String tam_local_port_1 = vo.getTam_local_port_1();

			String decPORT = Integrity.tasDecrypt(zone_name, tam_local_port_1);
			int dec_port = Integer.parseInt(decPORT);

			String taa_ip = vo.getTaa_ip().trim();
			String license = vo.getLicense().trim();

			String decIP = Integrity.tasDecrypt(zone_name, tam_local_ip_1);

			String tamip2 = vo.getTam_local_ip_2();
			String tamport2 = vo.getTam_local_port_2();
			if (tamip2 != "" || tamport2 != "") {
				JSONObject err = new JSONObject();
				err.put("errorCode", -2);
				err.put("errorMessage", "파일을 임의로 수정할 시 검증이 불가합니다");
				return CommonResponseDTO.builder().data(err).build();
			}

			String decIP2 = "";
			int dec_port2 = 0;

			String readhmac = vo.getHmac();
			String createhmac = Integrity.generateTAAConfIntegrity(zone_name, decIP,
					dec_port, decIP2, dec_port2, taa_ip, license); // Agent 환경파일 검증 성공

			switch (license) {
			case "정적":
				license = "static";
				break;
			case "동적":
				license = "dynamic";
				break;
			case "시간제한":
				license = "timelimit";
				break;
			default:
				break;
			}
			String value[] = { zone_name, decIP, decPORT, taa_ip, license, readhmac,
					createhmac };
			return CommonResponseDTO.builder().data(value).build();

		}
	}

	@GetMapping("/zone/agent-wether")
	public String[] getLicenseCnt(@Validated ZoneWetherLicenseDTO dto,
			BindingResult errors) {
		ZoneWetherLicenseVO vo = modelMapper.map(dto, ZoneWetherLicenseVO.class);
		log.info("DTO : " + dto.toString()); // zone_no 

		List<ZoneWetherLicenseVO> cntVO = zoneService.licenseCount(vo);
		List<ZoneWetherLicenseDTO> cntdto = modelMapper.map(cntVO,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());
		String licensevalue = cntdto.get(0).getPl_license_cnt();
		String tpllicensevalue = cntdto.get(0).getTpl_license_cnt();

		ZoneWetherLicenseVO pubvo = modelMapper.map(dto, ZoneWetherLicenseVO.class); // PUBLISH == 발급 개수 

		List<ZoneWetherLicenseVO> pubVO = zoneService.licensePublish(pubvo);
		List<ZoneWetherLicenseDTO> pubDTO = modelMapper.map(pubVO,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());

		List<ZoneWetherLicenseVO> pubPVO = zoneService.tpllicensePublish(pubvo);
		List<ZoneWetherLicenseDTO> pubPDTO = modelMapper.map(pubPVO,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());

		List<ZoneWetherLicenseVO> denyvo = zoneService.denyCount(pubvo);
		List<ZoneWetherLicenseDTO> denydto = modelMapper.map(denyvo,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());

		List<ZoneWetherLicenseVO> denytplvo = zoneService.denyTplCount(pubvo);
		List<ZoneWetherLicenseDTO> denytpldto = modelMapper.map(denytplvo,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());

		ZoneTimeLicenseVO tmvo = modelMapper.map(dto, ZoneTimeLicenseVO.class);

		List<ZoneTimeLicenseVO> tmvolist = zoneService.timeLicenseCount(tmvo);
		List<ZoneTimeLicenseDTO> tmdto = modelMapper.map(tmvolist,
				new TypeToken<List<ZoneTimeLicenseDTO>>() {
				}.getType());

		List<ZoneTimeLicenseVO> tmpubvo = zoneService.timeLicensePublishCount(tmvo);
		List<ZoneTimeLicenseDTO> tmpubdto = modelMapper.map(tmpubvo,
				new TypeToken<List<ZoneTimeLicenseDTO>>() {
				}.getType());

		int zone_no = tmvo.getZone_no();
		List<ZoneTimeLicenseVO> tmdenyvo = zoneService.timeLicenseDenyCount(zone_no);
		List<ZoneTimeLicenseDTO> tmdenydto = modelMapper.map(tmdenyvo,
				new TypeToken<List<ZoneTimeLicenseDTO>>() {
				}.getType());

		String puilsh_pl = pubDTO.get(0).getPub_pl_cnt();
		String publish_tpl = pubPDTO.get(0).getPub_tpl_cnt();
		String denyPlCnt = denydto.get(0).getDeny_cnt();
		String denyTplCnt = denytpldto.get(0).getDeny_tpl_cnt();

		String value[] = { licensevalue, tpllicensevalue, puilsh_pl, publish_tpl,
				denyPlCnt, denyTplCnt };
		return value;
	}

	@GetMapping("/zone/agent-timelicense")
	public CommonResponseDTO<Object> getTimeLicenseCnt(@Validated ZoneTimeLicenseDTO dto,
			BindingResult errors) throws TNAuthException {

		try {
			ZoneTimeLicenseVO vo = modelMapper.map(dto, ZoneTimeLicenseVO.class);

			int zone_no = vo.getZone_no();
			int allowed_cnt = zoneService.tmAllowedCount(zone_no);
			int publish_cnt = zoneService.tmPublishCount(zone_no);
			int deny_cnt = zoneService.tmDenyCount(zone_no);

			int result[] = { allowed_cnt, publish_cnt, deny_cnt };

			return CommonResponseDTO.builder().data(result).build();
		} catch (Exception e) {
			int result[] = { 0, 0, 0 };
			return CommonResponseDTO.builder().data(result).build();
		}
	}

	@PutMapping("/zone/wether-allow")
	public CommonResponseDTO<Object> updateAllowValue(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneWetherLicenseDTO dto, BindingResult errors) {

		ZoneWetherLicenseVO vo = modelMapper.map(dto, ZoneWetherLicenseVO.class);
		boolean result = zoneService.updateAllowValue(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.UPDATEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/zone/wether-reject")
	public CommonResponseDTO<Object> updateRejectValue(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneWetherLicenseDTO dto, BindingResult errors) {

		ZoneWetherLicenseVO vo = modelMapper.map(dto, ZoneWetherLicenseVO.class);
		boolean result = zoneService.updateRejectValue(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.UPDATEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/agent/pl-license")
	public CommonResponseDTO<Object> taaPlType(@Validated ZoneWetherLicenseDTO dto,
			BindingResult errors) {

		ZoneWetherLicenseVO vo = modelMapper.map(dto, ZoneWetherLicenseVO.class);
		List<ZoneWetherLicenseVO> cntVO = zoneService.taaPlType(vo);
		List<ZoneWetherLicenseDTO> cntdto = modelMapper.map(cntVO,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());

		return CommonResponseDTO.builder().data(cntdto).build();

	}

	@GetMapping("/agent/tpl-license")
	public CommonResponseDTO<Object> taaTplType(@Validated ZoneWetherLicenseDTO dto,
			BindingResult errors) {
		ZoneWetherLicenseVO vo = modelMapper.map(dto, ZoneWetherLicenseVO.class);
		List<ZoneWetherLicenseVO> cntVO = zoneService.taaTplType(vo);
		List<ZoneWetherLicenseDTO> cntdto = modelMapper.map(cntVO,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());
		if (cntdto.isEmpty()) {
		}
		return CommonResponseDTO.builder().data(cntdto).build();
	}

	@GetMapping("/agent/time-license")
	public CommonResponseDTO<Object> taaTmType(@Validated ZoneWetherLicenseDTO dto,
			BindingResult errors) {
		ZoneWetherLicenseVO vo = modelMapper.map(dto, ZoneWetherLicenseVO.class);
		List<ZoneWetherLicenseVO> cntVO = zoneService.taaTmType(vo);
		List<ZoneWetherLicenseDTO> cntdto = modelMapper.map(cntVO,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());
		if (cntdto.isEmpty()) {
		}
		return CommonResponseDTO.builder().data(cntdto).build();
	}

	@PostMapping("/license/timelimit")
	public CommonResponseDTO<Object> updateTimelimitLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneTimeLicenseDTO dto, BindingResult errors) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		log.info("License Enroll TIMELIMIT => " + securityVO.getUser_id());

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneTimeLicenseVO vo = modelMapper.map(dto, ZoneTimeLicenseVO.class);
		boolean result = zoneService.updateTimelimitLicense(vo);

		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.UPDATEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/license/timelimit-history")
	public CommonResponseDTO<Object> timelimitHistory(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneTimeLicenseHistoryDTO dto, BindingResult errors) {

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneTimeLicenseHistoryVO vo = modelMapper.map(dto,
				ZoneTimeLicenseHistoryVO.class);

		ZoneTimeLicenseHistoryVO historyVO = zoneService
				.findTimeLicenseInfoAsZoneName(vo);

		int zone_no = historyVO.getZone_no(); 

		vo.setZone_no(zone_no); 
		vo.setAction("C"); 

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		vo.setIssuer_user_no(securityVO.getUser_no());

		boolean result = zoneService.saveTimeLicenseHistory(vo);
		ZoneResultEnum resultEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.SAVEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(resultEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/license/parse")
	public CommonResponseDTO<Object> timeLicenseParse(@Validated ZoneTimeLicenseDTO dto,
			BindingResult errors) throws TNAuthException {
		String value = dto.getValue();
		String full[] = value.split("\\|");

		int length = full.length;
		String len = Integer.toString(length);

		if (length != 5) {
			return CommonResponseDTO.builder().data("length null").build();
		} else {
			String name = full[0].trim();
			String ip = full[1].trim();
			String cnt = full[2].trim();
			String limit = full[3].trim();
			String integrity = full[4].trim();

			try {
				boolean ret = Integrity.verifyTimeLicenseIntegrity(name, ip,
						Integer.valueOf(cnt), limit, integrity);
				String parse[] = { name, ip, cnt, limit, integrity };
				return CommonResponseDTO.builder().data(parse).build();
			} catch (Exception e) {
				JSONObject err = new JSONObject();
				err.put("errorCode", -1);
				err.put("errorMessage", "잘못된 라이선스 정보입니다");

				return CommonResponseDTO.builder().data(err).build();
			}
		}

	}

	@GetMapping("/zone/license-history")
	public JSONObject findAllLicenseHistory(@Validated ZoneLicenseHistoryInfoDTO dto,
			HttpServletRequest request) throws Exception {
		ZoneLicenseHistoryInfoVO vo = modelMapper.map(dto,
				ZoneLicenseHistoryInfoVO.class);
		List<ZoneLicenseHistoryInfoVO> volist = zoneService.findAllLicenseHistory(vo);
		String filter = vo.getFilter();
		int totalCount = zoneService.countZoneLicenseHistory(filter);
		String strcount = Integer.toString(totalCount);
		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);
		return result;
	}

	@GetMapping("/zone/timelicense-history")
	public JSONObject findTimeLicenseHistory(@Validated ZoneTimeLicenseHistoryDTO dto,
			HttpServletRequest request) throws Exception {
		ZoneTimeLicenseHistoryVO vo = modelMapper.map(dto,
				ZoneTimeLicenseHistoryVO.class);
		List<ZoneTimeLicenseHistoryVO> volist = zoneService.findTimeLicenseHistory(vo);
		String filter = vo.getFilter();
		int totalCount = zoneService.countZonetmLicenseHistory(filter);
		String strcount = Integer.toString(totalCount);
		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);

		return result;
	}

	@GetMapping("/zone/history")
	public JSONObject requestLicenseHistory(@Validated ZoneLicenseStateHistoryInfoDTO dto,
			HttpServletRequest request) throws Exception {
		ZoneLicenseStateHistoryInfoVO vo = modelMapper.map(dto,
				ZoneLicenseStateHistoryInfoVO.class);
		String filter = vo.getFilter();
		if (filter != "") { // filter가 존재
			List<ZoneLicenseStateHistoryInfoVO> volist = zoneService
					.requestLicenseHistory(vo);
			int totalCount = zoneService.countLicenseStateHistory(filter); // 총 개수

			JSONObject result = new JSONObject();
			result.put("data", volist);
			result.put("totalCount", totalCount);

			return result;
		} else {
			List<ZoneLicenseStateHistoryInfoVO> volist = zoneService
					.requestLicenseHistoryWithNoFilter(vo);
			int totalCount = zoneService.countLicenseStateHistoryWithNoFilter(); // 총 개수

			JSONObject result = new JSONObject();
			result.put("data", volist);
			result.put("totalCount", totalCount);

			return result;

		}
	}

	@GetMapping("/test")
	public CommonResponseDTO<Object> testpage(HttpServletRequest request)
			throws Exception {
		try {
			Properties prop = System.getProperties();
			Enumeration clsEnum = prop.propertyNames();
			String key;
			String value;

			while (clsEnum.hasMoreElements()) {
				key = (String) clsEnum.nextElement();
				value = prop.getProperty(key);
				String a = key + " : " + value;
				log.info(key + " : " + value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return CommonResponseDTO.builder().data("a").build();
	}

}
