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
		log.info("[enroll => ZoneInfoDTO\n] {}", zoneInfoDTO.toString());

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

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			//		if (!securityVO.getPermissions().equalsIgnoreCase("administrator"))
			throw new TokenValidationException();

		ZoneInfoVO infoVO = zoneService.findZoneInfoAsZoneName(vo);
		log.info(infoVO.toString());
		ZoneHistoryInfoVO historyVO = modelMapper.map(infoVO, ZoneHistoryInfoVO.class);
		log.info("zone 등록 " + historyVO.toString());
		historyVO.setAction("C");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/zone")
	public CommonResponseDTO<Object> updateZone(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneInfoDTO zoneInfoDTO, BindingResult errors) {
		log.info("[updateZone => ZoneInfoDTO\n] {}", zoneInfoDTO.toString());

		//		if (String.valueOf(zoneInfoDTO.getPl_license_cnt()).isEmpty()
		//				|| String.valueOf(zoneInfoDTO.getTpl_license_cnt()).isEmpty()
		//				|| String.valueOf(zoneInfoDTO.getSession_time()).isEmpty()) {
		//			log.warn("PARAMETER EXCEPTION");
		//			throw new ParameterException();
		//		}

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

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		ZoneInfoVO infoVO = zoneService.findZoneInfoAsZoneName(vo);
		String action = infoVO.getExist().equalsIgnoreCase("Y") ? "U" : "D";
		ZoneHistoryInfoVO historyVO = modelMapper.map(infoVO, ZoneHistoryInfoVO.class);
		historyVO.setAction(action);
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/zone")
	public CommonResponseDTO<Object> deleteZone(ZoneInfoDTO zoneInfoDTO) {
		log.info("[deleteZone => ZoneInfoDTO\n] {}", zoneInfoDTO.toString());

		if (String.valueOf(zoneInfoDTO.getZone_no()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		ZoneInfoVO vo = modelMapper.map(zoneInfoDTO, ZoneInfoVO.class);
		boolean result = zoneService.deleteZoneWithID(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.DELETEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		log.info("[enroll => CompanyResponseInfoDTO\n] {}", resDTO.toString());

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/zone/license")
	public CommonResponseDTO<Object> enrollLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneLicenseInfoDTO dto) {
		log.info("[enrollLicense => ZoneLicenseInfoDTO] {}", dto.toString());

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) {
			throw new TokenValidationException();
		}

		if (!securityVO.getUser_id().equalsIgnoreCase("admin")) {
			throw new TokenValidationException();
		}

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
		log.info("ZONELICENSEALLOWINFOPAGE COUNT : " + totalCount);

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
		log.info("[updateLicense => ZoneLicenseUpdateDTO] {}", dto.toString());

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		if (dto.getAsis_taa_ip() == "" || dto.getAsis_license_type() == 0) {
			log.warn("PARAMETER EXCEPTION");
		}

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
		log.info("[deleteLicense => ZoneLicenseInfoDTO\n] {}", dto.toString());

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

		ZoneLicenseInfoVO vo = modelMapper.map(dto, ZoneLicenseInfoVO.class);
		ZoneLicenseInfoVO infoVO = modelMapper.map(vo, ZoneLicenseInfoVO.class);
		ZoneLicenseInfoVO retinfoVO = zoneService.findZoneLicenseAsZoneNONTaaIP(infoVO);

		boolean result = zoneService.deleteZoneLicenseInfo(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.FAIL;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		ZoneLicenseHistoryInfoVO historyVO = modelMapper.map(retinfoVO,
				ZoneLicenseHistoryInfoVO.class);
		historyVO.setAction("D");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneLicenseHistory(historyVO);

		log.info("[enroll => CompanyResponseInfoDTO\n] {}", resDTO.toString());

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

		//		log.info("vo ZONE_NAME"+asiszonename);
		//		log.info("vo ASIS RENO"+asisreno);
		//		log.info("vo ASIS STATIC"+asisplcnt);
		//		log.info("vo ASIS DYNAMIC"+asistplcnt);
		//		log.info("vo ASIS LIMIT"+asislimit);
		//		log.info("vo TOBE RENO"+tobereno);
		//		log.info("vo TOBE STATIC"+tobepl);
		//		log.info("vo TOBE DYNAMIC"+tobetpl);
		//		log.info("vo TOBE LIMIT"+limited_url);
		log.info("======================================");

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

		log.info("ZONE_NAME = " + lc.sZoneName);
		log.info("ASIS RENO = " + lc.unAsisRevNo);
		log.info("ASIS STATIC = " + lc.unAsisStaticCnt);
		log.info("ASIS DYNAMIC = " + lc.unAsisDynamicCnt);
		log.info("ASIS LIMIT = " + lc.sAsisLimitUrl);
		log.info("TOBE RENO = " + lc.unTobeRevNo);
		log.info("TOBE STATIC = " + lc.unTobeStaticCnt);
		log.info("TOBE DYNAMIC = " + lc.unTobeDynamicCnt);
		log.info("TOBE LIMIT = " + lc.sTobeLimitUrl);

		// server = zone=white&info=estl2UBlJIACKmUhQ2dxZpq+z6gyUsldZOkwLD6xYI3jcOVHpBi291uUAGPvJzJuHTecpnNCxRrtZc5JPDyUQQ==
		// local =  zone=white&info=8ZClZut2uc5WhaRVAnepXgQvIc96KeO5VpZIQqSKA4nd1jaKNrEDEpxPcswm18WUvTvRVYagW1d89zci9jn4/g==

		try {
			String sReqMsg = Integrity.generateLicenseRequest(lc);
			log.info("Request Message  :" + sReqMsg);
			return CommonResponseDTO.builder().data(sReqMsg).build();
		} catch (Exception e) {
			log.info("테스트 catch flow 실패 "); // JNI  -10004 error ? 
			log.info(e.toString());
			return CommonResponseDTO.builder().data("error").build();
		}

		//CREATE QR CODE 
		//		return sReqMsg;
	}

	@GetMapping("/zone/verify-enroll")
	public CommonResponseDTO<Object> ZoneVerifyEnroll(
			@Validated ZoneVerifyIntegrityDTO dto, BindingResult errors)
			throws TNAuthException {
		ZoneVerifyIntegrityVO vo = modelMapper.map(dto, ZoneVerifyIntegrityVO.class);
		String sResponseMsg = vo.getValue();
		log.info("RESPONSE MSG :" + sResponseMsg);
		TNLicenseInfo lc = new TNLicenseInfo();
		lc = Integrity.verifyLicenseResponse(sResponseMsg);

		String name = lc.sZoneName;
		String message = lc.sType;
		String timestamp = lc.sTimeStamp;
		log.info(lc.sZoneName);

		int zone_no = zoneService.getZoneNoWithZoneName(name);
		log.info("zone number > " + zone_no);

		ZoneInfoVO infoVO = zoneService.getZoneInfoWithNo(zone_no);
		log.info("INFOVO TOSTRING >> "+infoVO.toString());
		int maxST = infoVO.getPl_license_cnt();
		int maxDY= infoVO.getTpl_license_cnt();
		
		
		
		int stVO = zoneService.getAgentStaticAllow(zone_no);
		int dyVO = zoneService.getAgentDynamicAllow(zone_no);
		log.info("static > " + stVO);
		log.info("dynamic > " + dyVO);

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
		//		log.info("=============STRING==================");
		//		log.info("| 개정 번호 : " + stringasreno);
		//		log.info("| 기존 정적 : " + stringaspl);
		//		log.info("| 기존 동적 : " + stringastpl);
		//		log.info("| 개정될 개정 번호 : " + stringbereno);
		//		log.info("| 입력 정적 : " + stringbepl);
		//		log.info("| 입력 동적 : " + stringbetpl);
		log.info("============== INT ====================");
		log.info("| | 개정 번호 : " + asisreno);
		log.info("| | 최대 정적 : " + asispl);
		log.info("| | 최대 동적 : " + asistpl);
		log.info("| | 개정될 개정 번호 : " + tobereno);
		log.info("| | 요청 정적 : " + tobepl);
		log.info("| | 요청 동적 : " + tobetpl);
		log.info("| | icv : " + integrity);
		log.info("============== INT ====================");

		String result[] = { name, stringbereno, stringbepl, maxSTATIC, stringbetpl,
				maxDYNAMIC, tobelimited, integrity, stringstaticVO, stringdynamicVO };

		return CommonResponseDTO.builder().data(result).build();
	}

	@PutMapping("/zone/license-enroll")
	public CommonResponseDTO<Object> updateLicenseInfo(
			@Validated ZoneLicenseUpdateDBDTO dto, BindingResult errors)
			throws TNAuthException {
		//error 처리 해주고 dto.gettobepl < dto.getasispl   -> 오류임
		log.info("============== LICENSE ENROLL DTO VALUE : " + dto.toString());
		ZoneLicenseUpdateDBVO vo = modelMapper.map(dto, ZoneLicenseUpdateDBVO.class);
		boolean result = zoneService.updateLicenseInfo(vo); // PUT zone info tbl static/dynamic count 
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.UPDATEERROR;

		//		log.info(ZoneResultEnum.SUCCESS.getErrorMessage());
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
			log.info(vo.toString());
			String zone_name = vo.getZone_name();
			log.info(vo.getZone_name());
			String tam_local_ip_1 = vo.getTam_local_ip_1();
			log.info(vo.getTam_local_ip_1());
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
			log.info("encrypt ip2 port 2" + enc_tam_local_port2);
			log.info(enc_tam_local_port2);
			int intport1 = Integer.parseInt(tam_local_port_1);
			int intport2 = Integer.parseInt(tam_local_port_2);

			String hmac = Integrity.generateTAAConfIntegrity(zone_name, tam_local_ip_1,
					intport1, tam_local_ip_2, intport2, taa_ip, license);
			log.info("HMAC VALUE :" + hmac);
			String taaFile[] = { enc_tam_local_ip, enc_tam_local_port, enc_tam_local_ip2,
					enc_tam_local_port2, hmac };
			return CommonResponseDTO.builder().data(taaFile).build();

		} catch (Exception e) {
			ZoneAgentCreateFileVO vo = modelMapper.map(dto, ZoneAgentCreateFileVO.class);
			log.info("CATCH START");
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
			log.info("CATCH HMAC VALUE :" + hmac);

			// red / 999999 / 10.10.10.1 / 1 / 10.10.10.10
			//iXNin5poB81SqSDf5BdR2JA2QlQ5orGFA3jQ8ALnFoU=

			// iXNin5poB81SqSDf5BdR2JA2QlQ5orGFA3jQ8ALnFoU=

			String cat[] = { enc_tam_local_ip, enc_tam_local_port, hmac };

			return CommonResponseDTO.builder().data(cat).build();
		}

	}

	//fail
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
			// dto에 담긴값들은 모두 . 파일에서 긁어온 값들임 

			log.info("try flow" + vo.toString());
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

			log.info("READ FILE HMAC VALUE " + dto.getHmac());
			String readhmac = dto.getHmac();

			log.info("VO LICENSE :" + vo.getLicense());
			log.info("LICENSE :" + license);

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
			log.info("CATCH flow");
			ZoneSettingFileVO vo = modelMapper.map(dto, ZoneSettingFileVO.class);

			log.info(vo.toString());
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
			log.info("CATCH READ HMAC VALUE :" + readhmac);
			log.info("LICENSE VALUE :" + license);
			String createhmac = Integrity.generateTAAConfIntegrity(zone_name, decIP,
					dec_port, decIP2, dec_port2, taa_ip, license); // Agent 환경파일 검증 성공

			log.info("CATCH CREATE HMAC VALUE :" + createhmac);

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
		log.info("==========COUNT DTO =============" + cntdto.toString()); // zone_no로 get PL TPL 
		String licensevalue = cntdto.get(0).getPl_license_cnt();
		//		log.info("pl license Value ========" + licensevalue);
		String tpllicensevalue = cntdto.get(0).getTpl_license_cnt();
		//		log.info("tpl license Value ========" + tpllicensevalue);

		ZoneWetherLicenseVO pubvo = modelMapper.map(dto, ZoneWetherLicenseVO.class); // PUBLISH == 발급 개수 

		List<ZoneWetherLicenseVO> pubVO = zoneService.licensePublish(pubvo);
		List<ZoneWetherLicenseDTO> pubDTO = modelMapper.map(pubVO,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());
		log.info("PUBLISH PL DTO" + pubDTO); // publish_pl_cnt

		List<ZoneWetherLicenseVO> pubPVO = zoneService.tpllicensePublish(pubvo);
		List<ZoneWetherLicenseDTO> pubPDTO = modelMapper.map(pubPVO,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());
		log.info("PUBLISH TPL DTO" + pubPDTO); // publish_tpl_cnt

		List<ZoneWetherLicenseVO> denyvo = zoneService.denyCount(pubvo);
		List<ZoneWetherLicenseDTO> denydto = modelMapper.map(denyvo,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());
		log.info("PUBLISH deny PL DTO" + denydto);

		List<ZoneWetherLicenseVO> denytplvo = zoneService.denyTplCount(pubvo);
		List<ZoneWetherLicenseDTO> denytpldto = modelMapper.map(denytplvo,
				new TypeToken<List<ZoneWetherLicenseDTO>>() {
				}.getType());
		log.info("PUBLISH deny TPL DTO" + denytpldto);

		ZoneTimeLicenseVO tmvo = modelMapper.map(dto, ZoneTimeLicenseVO.class);

		List<ZoneTimeLicenseVO> tmvolist = zoneService.timeLicenseCount(tmvo);
		List<ZoneTimeLicenseDTO> tmdto = modelMapper.map(tmvolist,
				new TypeToken<List<ZoneTimeLicenseDTO>>() {
				}.getType());

		log.info("TIMELICENSE VO : " + tmvo.toString());

		List<ZoneTimeLicenseVO> tmpubvo = zoneService.timeLicensePublishCount(tmvo);
		List<ZoneTimeLicenseDTO> tmpubdto = modelMapper.map(tmpubvo,
				new TypeToken<List<ZoneTimeLicenseDTO>>() {
				}.getType());
		log.info("TIMELICENSE PUBLISH DTO : " + tmpubdto.toString());

		int zone_no = tmvo.getZone_no();
		List<ZoneTimeLicenseVO> tmdenyvo = zoneService.timeLicenseDenyCount(zone_no);
		List<ZoneTimeLicenseDTO> tmdenydto = modelMapper.map(tmdenyvo,
				new TypeToken<List<ZoneTimeLicenseDTO>>() {
				}.getType());
		log.info("TIMELICENSE DENY DTO : " + tmdenydto.toString());

		log.info("--------------TIMELICENSE DTO TEST ----------------------"
				+ tmdto.toString());
		log.info("--------------TIMELICENSE DTO SIZE ----------------------"
				+ tmdto.size());

		// 이다음을 실행을 못함 가져오는값이 없으니까 0번째 배열에서 값추출을 못함

		String puilsh_pl = pubDTO.get(0).getPub_pl_cnt();
		log.info("PUBLISH PL : " + puilsh_pl);
		String publish_tpl = pubPDTO.get(0).getPub_tpl_cnt();
		//		log.info("PUBLISH TPL : "+publish_tpl);
		String denyPlCnt = denydto.get(0).getDeny_cnt();
		//		log.info("DENY COUNT : "+ denyPlCnt);
		String denyTplCnt = denytpldto.get(0).getDeny_tpl_cnt();
		//		log.info("DENY TPL COUNT : "+ denyTplCnt);

		String value[] = { licensevalue, tpllicensevalue, puilsh_pl, publish_tpl,
				denyPlCnt, denyTplCnt };
		log.info("VALUE : " + Arrays.toString(value));
		return value;
	}

	@GetMapping("/zone/agent-timelicense")
	public CommonResponseDTO<Object> getTimeLicenseCnt(@Validated ZoneTimeLicenseDTO dto,
			BindingResult errors) throws TNAuthException {

		try {

			ZoneTimeLicenseVO vo = modelMapper.map(dto, ZoneTimeLicenseVO.class);
			log.info("AGENT TIMELICENSE COUNT = " + vo.toString());

			//		시간제한 라이선스 
			//		최대 허용 개수 = LICENSE_TMLIMIT_INFO_TBL > allowed_cnt
			//		현재 발급 개수 = TAA_INFO_TBL > license_type = 3 && allow_state =1;
			//		현재 거부 개수 = TAA_INFO_TBL > license_type = 3 && allow_state =2;
			int zone_no = vo.getZone_no();

			int allowed_cnt = zoneService.tmAllowedCount(zone_no);
			int publish_cnt = zoneService.tmPublishCount(zone_no);
			int deny_cnt = zoneService.tmDenyCount(zone_no);

			log.info("Allowed cnt" + allowed_cnt);
			log.info("Publish cnt" + publish_cnt);
			log.info("Deny cnt" + deny_cnt);
			int result[] = { allowed_cnt, publish_cnt, deny_cnt };

			return CommonResponseDTO.builder().data(result).build();
		} catch (Exception e) {
			int result[] = { 0, 0, 0 };
			return CommonResponseDTO.builder().data(result).build();
		}
	}
	//	@GetMapping("/zone/agent-timelicense")
	//	public CommonResponseDTO<Object> getTimeLicenseCnt(
	//			@Validated ZoneTimeLicenseDTO dto, BindingResult errors) throws TNAuthException {
	//		
	//		ZoneTimeLicenseVO vo = modelMapper.map(dto, ZoneTimeLicenseVO.class);
	//		log.info("AGENT TIMELICENSE COUNT = "+vo.toString());
	//		
	////		시간제한 라이선스 
	////		최대 허용 개수 = LICENSE_TMLIMIT_INFO_TBL > allowed_cnt
	////		현재 발급 개수 = TAA_INFO_TBL > license_type = 3 && allow_state =1;
	////		현재 거부 개수 = TAA_INFO_TBL > license_type = 3 && allow_state =2;
	//		int zone_no = vo.getZone_no();
	//		
	//		int allowed_cnt = zoneService.tmAllowedCount(zone_no);
	//		int publish_cnt = zoneService.tmPublishCount(zone_no);
	//		int deny_cnt = zoneService.tmDenyCount(zone_no);
	//		
	//		log.info("Allowed cnt"+allowed_cnt);
	//		log.info("Publish cnt"+publish_cnt);
	//		log.info("Deny cnt"+deny_cnt);
	//		int result [] = {allowed_cnt, publish_cnt, deny_cnt};
	//		
	//		return CommonResponseDTO.builder().data(result).build();
	//	}

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
		log.info(
				"=========================================================================================");
		log.info("PL LICENSE controller 탔음" + dto.toString());

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
		//log.info("TPL LICENSE controller 탔음" + dto.toString());
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
		log.info("[updateZone => ZoneTimeLicenseDTO\n] {}", dto.toString());
		log.info("[icv => ZONETIMELICENSE ICV VALUE : ] {}", dto.getIcv());

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneTimeLicenseVO vo = modelMapper.map(dto, ZoneTimeLicenseVO.class);
		log.info("zonetimelicensevo : " + vo);
		boolean result = zoneService.updateTimelimitLicense(vo);

		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS
				: ZoneResultEnum.UPDATEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/license/timelimit-history")
	public CommonResponseDTO<Object> timelimitHistory(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneTimeLicenseHistoryDTO dto, BindingResult errors) {
		log.info("[history => ZoneTimeLicenseHistoryDTO\n] {}", dto.toString());

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneTimeLicenseHistoryVO vo = modelMapper.map(dto,
				ZoneTimeLicenseHistoryVO.class);

		ZoneTimeLicenseHistoryVO historyVO = zoneService
				.findTimeLicenseInfoAsZoneName(vo);

		int zone_no = historyVO.getZone_no(); // white기준 71번 나옴

		vo.setZone_no(zone_no); // ZONE_NAME으로 구해온 ZONE_NO 
		vo.setAction("C"); //postmapping에 한해서 C 고정값 가능

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		vo.setIssuer_user_no(securityVO.getUser_no());

		//		log.info("XML에 드가는값 : " + vo.toString());

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
		log.info("arrays length :" + len);

		if (length != 5) {
			return CommonResponseDTO.builder().data("length null").build();
		} else {
			String name = full[0].trim();
			String ip = full[1].trim();
			String cnt = full[2].trim();
			//			int allow_cnt = Integer.parseInt(cnt);
			String limit = full[3].trim();
			String integrity = full[4].trim();

			try {
				boolean ret = Integrity.verifyTimeLicenseIntegrity(name, ip,
						Integer.valueOf(cnt), limit, integrity);
				//				boolean ret = Integrity.verifyTimeLicenseIntegrity(name, ip, allow_cnt,limit, integrity);
				log.info("boolean > " + ret);
				String parse[] = { name, ip, cnt, limit, integrity };
				return CommonResponseDTO.builder().data(parse).build();
			} catch (Exception e) {
				JSONObject err = new JSONObject();
				err.put("errorCode", -1);
				err.put("errorMessage", "icv 값 검증 오류");

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
		log.info("dto list : " + dto.toString());
		ZoneTimeLicenseHistoryVO vo = modelMapper.map(dto,
				ZoneTimeLicenseHistoryVO.class);

		log.info("=======timelicense HISTORY VO =============" + vo.toString());
		List<ZoneTimeLicenseHistoryVO> volist = zoneService.findTimeLicenseHistory(vo);
		log.info("========t SERVICE 태우고 난 후 LICENSEHISTORYVOLIST========="
				+ volist.toString());

		String filter = vo.getFilter();

		int totalCount = zoneService.countZonetmLicenseHistory(filter);
		String strcount = Integer.toString(totalCount);
		log.info("timelicense HISTORY COUNT : " + strcount);

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
		log.info("filter > > >  " + filter);

		if (filter != "") { // filter가 존재
			List<ZoneLicenseStateHistoryInfoVO> volist = zoneService
					.requestLicenseHistory(vo);

			int totalCount = zoneService.countLicenseStateHistory(filter); // 총 개수

			JSONObject result = new JSONObject();
			result.put("data", volist);
			result.put("totalCount", totalCount);

			return result;
		} else {// filter가 없는거 
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

	//		@RequestMapping("/zone/history")
	//		public JSONObject requestLicenseHistory(@Validated ZoneLicenseStateHistoryInfoDTO dto,
	//				HttpServletRequest request) throws Exception {
	//			//
	//			//		log.info("==============ZONE HISTORY DTO ========================"+ dto.toString());0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	//			//		int nPerPage = dto.getNPerPage();
	//			//		int currentPage = dto.getCurrentPageNumber();
	//			//		int startValue = dto.getStartvalue();
	//			//
	//			//		String stringpage = Integer.toString(nPerPage);
	//			//		String stringnumber = Integer.toString(currentPage);
	//			//		String stringvalue = Integer.toString(startValue);
	//			//
	//			//		log.info("nPerPage : " + stringpage);
	//			//		log.info("page number : " + stringnumber);
	//			//		log.info("start value : " + stringvalue);
	//
	//			ZoneLicenseStateHistoryInfoVO vo = modelMapper.map(dto, ZoneLicenseStateHistoryInfoVO.class);
	//
	//			log.info(
	//					"==============ZONE HISTORY VP ========================" + vo.toString());
	//			List<ZoneLicenseStateHistoryInfoVO> licenseHistoryVOList = zoneService
	//					.requestLicenseHistory(vo);
	//			log.info(
	//					"==============ZONE SERVICE 태우고 난 후 LICENSEHISTORYVOLIST========================"
	//							+ licenseHistoryVOList.toString());
	//			JSONObject result = new JSONObject();
	//			result.put("data", licenseHistoryVOList);
	//
	//			return result;
	//		}

}
