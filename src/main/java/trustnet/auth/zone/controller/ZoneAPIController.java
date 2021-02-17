package trustnet.auth.zone.controller;

import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
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
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.user.controller.except.TokenValidationException;
import trustnet.auth.zone.code.ZoneResultEnum;
import trustnet.auth.zone.controller.dto.ZoneInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneInfoResponseDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseUpdateDTO;
import trustnet.auth.zone.controller.dto.ZoneVerifyIntegrityDTO;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseUpdateVO;
import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;
import trustnet.tas.except.TNAuthException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ZoneAPIController {

	private final ZoneService zoneService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;

	@PostMapping("/zone")
	public CommonResponseDTO<Object> enroll(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneInfoDTO zoneInfoDTO, BindingResult errors) {
		log.info("[enroll => ZoneInfoDTO\n] {}", zoneInfoDTO.toString());

		if (String.valueOf(zoneInfoDTO.getComp_no()).isEmpty()
				|| String.valueOf(zoneInfoDTO.getPl_license_cnt()).isEmpty()
				|| String.valueOf(zoneInfoDTO.getTpl_license_cnt()).isEmpty()
				|| String.valueOf(zoneInfoDTO.getSession_time()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneInfoVO vo = modelMapper.map(zoneInfoDTO, ZoneInfoVO.class);
		boolean result = zoneService.saveZoneInfo(vo);
		ZoneResultEnum resultEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.SAVEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(resultEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		ZoneInfoVO infoVO = zoneService.findZoneInfoAsZoneName(vo);
		log.info(infoVO.toString());
		ZoneHistoryInfoVO historyVO = modelMapper.map(infoVO, ZoneHistoryInfoVO.class);
		historyVO.setAction("C");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/zone")
	public CommonResponseDTO<Object> updateZone(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated ZoneInfoDTO zoneInfoDTO, BindingResult errors) {
		log.info("[updateZone => ZoneInfoDTO\n] {}", zoneInfoDTO.toString());

		if (String.valueOf(zoneInfoDTO.getComp_no()).isEmpty()
				|| String.valueOf(zoneInfoDTO.getPl_license_cnt()).isEmpty()
				|| String.valueOf(zoneInfoDTO.getTpl_license_cnt()).isEmpty()
				|| String.valueOf(zoneInfoDTO.getSession_time()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneInfoVO vo = modelMapper.map(zoneInfoDTO, ZoneInfoVO.class);
		boolean result = zoneService.updateZoneWithID(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.UPDATEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getPermissions().equalsIgnoreCase("admin"))
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
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.DELETEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		log.info("[enroll => CompanyResponseInfoDTO\n] {}", resDTO.toString());

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/zone/license")
	public CommonResponseDTO<Object> enrollLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, ZoneLicenseInfoDTO dto) {
		log.info("[enrollLicense => ZoneLicenseInfoDTO\n] {}", dto.toString());

		if (String.valueOf(dto.getZone_no()).isEmpty() || dto.getTaa_ip().isEmpty()
				|| String.valueOf(dto.getLicense_type()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		ZoneLicenseInfoVO vo = modelMapper.map(dto, ZoneLicenseInfoVO.class);
		boolean result = zoneService.saveZoneLicenseInfo(vo);
		ZoneResultEnum resultEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.SAVEERROR;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(resultEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		ZoneLicenseHistoryInfoVO historyVO = modelMapper.map(vo, ZoneLicenseHistoryInfoVO.class);
		historyVO.setAction("C");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneLicenseHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/zone/license")
	public CommonResponseDTO<Object> updateLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, ZoneLicenseUpdateDTO dto) {
		log.info("[updateLicense => ZoneLicenseUpdateDTO\n] {}", dto.toString());

		if (String.valueOf(dto.getZone_no()).isEmpty() || dto.getAsis_taa_ip().isEmpty()
				|| String.valueOf(dto.getAsis_license_type()).isEmpty() || dto.getTaa_ip().isEmpty()
				|| String.valueOf(dto.getLicense_type()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		ZoneLicenseUpdateVO vo = modelMapper.map(dto, ZoneLicenseUpdateVO.class);
		boolean result = zoneService.updateZoneLicenseInfo(vo);
		ZoneResultEnum retEnum = result ? ZoneResultEnum.SUCCESS : ZoneResultEnum.FAIL;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		ZoneLicenseInfoVO infoVO = modelMapper.map(vo, ZoneLicenseInfoVO.class);
		ZoneLicenseInfoVO retinfoVO = zoneService.findZoneLicenseAsZoneNONTaaIP(infoVO);
		ZoneLicenseHistoryInfoVO historyVO = modelMapper.map(retinfoVO, ZoneLicenseHistoryInfoVO.class);
		historyVO.setAction("U");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneLicenseHistory(historyVO);

		log.info("[enroll => CompanyResponseInfoDTO\n] {}", resDTO.toString());

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/zone/license")
	public CommonResponseDTO<Object> deleteLicense(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, ZoneLicenseInfoDTO dto) {
		log.info("[deleteLicense => ZoneLicenseInfoDTO\n] {}", dto.toString());

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

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		ZoneLicenseHistoryInfoVO historyVO = modelMapper.map(retinfoVO, ZoneLicenseHistoryInfoVO.class);
		historyVO.setAction("D");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = zoneService.saveZoneLicenseHistory(historyVO);

		log.info("[enroll => CompanyResponseInfoDTO\n] {}", resDTO.toString());

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/zone")
	public CommonResponseDTO<Object> checkZoneName(@Validated ZoneInfoDTO dto, BindingResult errors) {

		if (errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		ZoneInfoVO vo = modelMapper.map(dto, ZoneInfoVO.class);
		boolean result = zoneService.isFindZone(vo);

		ZoneResultEnum retEnum = result ? ZoneResultEnum.EXISTFAIL : ZoneResultEnum.EXISTSUCCESS;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

//	@GetMapping("/zone/verify")
//	public CommonResponseDTO<Object> ZoneVerifyIntegrity(@Validated ZoneVerifyIntegrityDTO dto, BindingResult errors)
//			throws TNAuthException {
//		if (errors.hasErrors()) {
//			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
//			errors.getFieldError().getDefaultMessage());
//			return CommonResponseDTO.builder().data(resDTO).build();
//		}
//		
//		ZoneVerifyIntegrityVO ZVIvo = modelMapper.map(dto, ZoneVerifyIntegrityVO.class);
//		boolean bRet = zoneService.verifyZoneIntegrity(ZVIvo);
//		ZoneResultEnum retEnum = bRet ? ZoneResultEnum.EXISTSUCCESS : ZoneResultEnum.EXISTFAIL;
//		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);
//		log.info(resDTO.toString());
//		return CommonResponseDTO.builder().data(resDTO).build();
//	}

}
