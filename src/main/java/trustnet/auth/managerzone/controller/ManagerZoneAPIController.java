package trustnet.auth.managerzone.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.manager.code.ManagerResultEnum;
import trustnet.auth.manager.controller.dto.ManagerDBCheckDTO;
import trustnet.auth.manager.controller.dto.ManagerDBInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.manager.service.vo.ManagerDBInfoVO;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.managerzone.code.ManagerZoneResultEnum;
import trustnet.auth.managerzone.controller.dto.ManagerZoneGetNameDTO;
import trustnet.auth.managerzone.controller.dto.ManagerZoneInfoDTO;
import trustnet.auth.managerzone.controller.dto.ManagerZoneResponseDTO;
import trustnet.auth.managerzone.service.ManagerZoneService;
import trustnet.auth.managerzone.service.vo.ManagerZoneGetNameVO;
import trustnet.auth.managerzone.service.vo.ManagerZoneInfoVO;
import trustnet.auth.user.controller.except.TokenValidationException;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerZoneAPIController {

	private final ModelMapper modelMapper;
	private final ManagerZoneService managerZoneService;
	private final SecurityService securityService;

	//	@PostMapping("/managerZone")
	//	public CommonResponseDTO<Object> enrollMatch(
	//			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
	//			@RequestBody List<ManagerZoneInfoDTO> dtoList) throws TNAuthException {
	//
	//		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
	//		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
	//			throw new TokenValidationException();
	//		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
	//			throw new TokenValidationException();
	//
	//		log.info("[enrollMatch => CompanyInfoDTO\n] {}", dtoList.toString());
	//
	//		List<ManagerZoneInfoVO> voList = modelMapper.map(dtoList,
	//				new TypeToken<List<ManagerZoneInfoVO>>() {
	//				}.getType());
	//
	//		boolean result = managerZoneService.isFindManagerZone(voList.get(0));
	//		log.info("boolean type result is > " + result);
	//
	//		//false면 if문 실행
	//		if (!result) {
	//			log.info("result > " + result);
	//			result = managerZoneService.deleteManagerZoneWithTAMNO(voList.get(0));
	//		}
	//
	//		ManagerZoneResultEnum resultEnum;
	//		ManagerZoneResponseDTO resDTO;
	//
	//		for (ManagerZoneInfoVO vo : voList) {
	//			boolean ret = managerZoneService.saveManagerZone(vo);
	//			if (!ret) {
	//				resultEnum = ManagerZoneResultEnum.FAIL;
	//				resDTO = new ManagerZoneResponseDTO(resultEnum);
	//				log.info("[ FAIL ! enrollMatch => CompanyResponseInfoDTO\n] {}",
	//						resDTO.toString());
	//				return CommonResponseDTO.builder().data(resDTO).build();
	//			}
	//		}
	//
	//		resultEnum = ManagerZoneResultEnum.SUCCESS;
	//		resDTO = new ManagerZoneResponseDTO(resultEnum);
	//		log.info("[SUCCESS ! enrollMatch => CompanyResponseInfoDTO\n] {}",
	//				resDTO.toString());
	//
	//		return CommonResponseDTO.builder().data(resDTO).build();
	//	}

	@PostMapping("/managerZone")
	public CommonResponseDTO<Object> enrollMatch(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@RequestBody List<ManagerZoneInfoDTO> dtoList) throws TNAuthException {

		log.info("managerZone LIST<DTO> " + dtoList.toString());

		List<ManagerZoneInfoVO> voList = modelMapper.map(dtoList,
				new TypeToken<List<ManagerZoneInfoVO>>() {
				}.getType());

		log.info("managerZone LIST<VO> SIZE = " + voList.size() + "<<<<<<");
		int tam_no = voList.get(0).getTam_no();
		log.info("managerZone TAM_NO = " + tam_no + "<<<<<<");
		ManagerZoneInfoVO deleteVO = new ManagerZoneInfoVO();
		deleteVO.setTam_no(tam_no);
		boolean delete = managerZoneService.deleteManagerZoneWithTAMNO(deleteVO);
		log.info("delete ? " + delete);

		for (ManagerZoneInfoVO vo : voList) {
			log.info("vo.toString >>> " + vo.toString());
			boolean test = managerZoneService.saveManagerZone(vo);
			log.info("boolean 결과 ? " + test);
			if(!test) {
				JSONObject err = new JSONObject();
				err.put("errorCode", -1);
				err.put("errorMessage", "예기지못한 에러가 발생했습니다. 재시도 바랍니다.");
				return CommonResponseDTO.builder().data(err).build();		
			}
		}
		
		JSONObject success = new JSONObject();
		success.put("errorCode", 0);
		success.put("errorMessage", "성공하였습니다");
		return CommonResponseDTO.builder().data(success).build();
	}

	@DeleteMapping("/managerZone/exception")
	public CommonResponseDTO<Object> exceptionDelete(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ManagerZoneInfoDTO dto) throws TNAuthException {
		
		log.info("/exception dto.toString > "+dto.toString());
		ManagerZoneInfoVO vo = modelMapper.map(dto, ManagerZoneInfoVO.class);
		boolean result = managerZoneService.deleteManagerZoneWithTAMNO(vo);
		log.info("/exception result > " + result);
		
		JSONObject ret = new JSONObject();
		ret.put("errorCode", 0);
		ret.put("errorMessage", "성공하였습니다");
		return CommonResponseDTO.builder().data(ret).build();
	}

	//	@PostMapping("/managerZone/catch")
	//	public CommonResponseDTO<Object> MatchingAllDelete(
	//			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
	//			@Validated ManagerZoneInfoDTO dto, BindingResult errors)throws TNAuthException {
	//		
	//		int tam_no = dto.getTam_no();
	//		
	//		boolean result = managerZoneService.matchingAllDelete(tam_no);
	//		
	//		ManagerZoneResultEnum resultEnum = result ? ManagerZoneResultEnum.SUCCESS : ManagerZoneResultEnum.FAIL;
	//		ManagerZoneResponseDTO resDTO = new ManagerZoneResponseDTO(resultEnum);
	//		
	//		return CommonResponseDTO.builder().data(resDTO).build();
	//	}
	//		

}// API controller end

//	@PostMapping("/managerZone")
//	public CommonResponseDTO<Object> enrollMatch(
//			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
//			@RequestBody List<ManagerZoneInfoDTO> dtoList) throws TNAuthException {
//		log.info("[enrollMatch => CompanyInfoDTO\n] {}", dtoList.toString());
//		log.info("MANAGERZONE PARAMETER NULL TEST !!");
//		
//		if (dtoList.size() == 0) {
//			//			log.warn("PARAMETER EXCEPTION");
//			log.warn("PARAMETER ALL DELETE");
//			
//		}
//		
//		List<ManagerZoneInfoVO> voList = modelMapper.map(dtoList,
//				new TypeToken<List<ManagerZoneInfoVO>>() {
//		}.getType());
//		boolean result = managerZoneService.isFindManagerZone(voList.get(0));
//		if (result)
//			result = managerZoneService.deleteManagerZoneWithTAMNO(voList.get(0));
//		
//		ManagerZoneResultEnum resultEnum;
//		ManagerZoneResponseDTO resDTO;
//		for (ManagerZoneInfoVO vo : voList) {
//			boolean ret = managerZoneService.saveManagerZone(vo);
//			if (!ret) {
//				resultEnum = ManagerZoneResultEnum.FAIL;
//				resDTO = new ManagerZoneResponseDTO(resultEnum);
//				log.info("[enrollMatch => CompanyResponseInfoDTO\n] {}",
//						resDTO.toString());
//				
//				return CommonResponseDTO.builder().data(resDTO).build();
//			}
//		}
//		
//		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
//		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
//			throw new TokenValidationException();
//		
//		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
//			throw new TokenValidationException();
//		
//		resultEnum = ManagerZoneResultEnum.SUCCESS;
//		resDTO = new ManagerZoneResponseDTO(resultEnum);
//		log.info("[enrollMatch => CompanyResponseInfoDTO\n] {}", resDTO.toString());
//		
//		return CommonResponseDTO.builder().data(resDTO).build();
//	}
