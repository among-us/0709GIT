//package trustnet.auth.common.dto;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.CookieValue;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import trustnet.auth.common.vo.HmacVO;
//import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
//import trustnet.auth.manager.controller.except.ParameterException;
//import trustnet.tas.except.TNAuthException;
//import trustnet.tas.valid.Integrity;
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//public class HmacAPIController {
//	private final ModelMapper modelMapper;
//	
//	@PutMapping("/manager/genHmac")
//	public CommonResponseDTO<Object> generateHmac(
//			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
//			@Validated HmacDTO hmacDTO, BindingResult errors)
//			throws TNAuthException {
//		if (hmacDTO.getTam_local_port() == 0
//				|| hmacDTO.getTam_adm_port() == 0) {
//			log.warn("PARAMETER EXCEPTION");
//			throw new ParameterException();
//		}
//		if (errors.hasErrors()) {
//			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070,
//					errors.getFieldError().getDefaultMessage());
//			return CommonResponseDTO.builder().data(resDTO).build();
//		}
//
//		//		Tamanager enroll 정보로 integrity 값 생성
//		HmacVO hmacvo = modelMapper.map(hmacDTO, HmacVO.class);
//		log.info("===============VOLIST===============" + hmacvo.toString());
//
//		String tam_name = hmacvo.getTam_name();
//		String tam_local_ip = hmacvo.getTam_local_ip();
//		int tam_local_port = hmacvo.getTam_local_port();
//		int tam_adm_port = hmacvo.getTam_adm_port();
//		// function genTAMLICENSE -> tam_license 
//
//		String ip = hmacvo.getTam_name();
//		String tam_license = Integrity.generateTAMlicense(ip);
//		log.info("===============TAM_LICENSE===============" + tam_license.toString());
//
//		String integrity = Integrity.generateTAMDbIntegrity(tam_name, tam_local_ip,
//				tam_local_port, tam_adm_port, tam_license);
//		log.info("===============INTEGRITY(HMAC)===============" + integrity.toString());
//
//		return CommonResponseDTO.builder().data(integrity).build();
//
//		//		
//
//	}
//	
//	
//	
//}
