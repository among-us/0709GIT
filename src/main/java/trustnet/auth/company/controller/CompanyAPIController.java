package trustnet.auth.company.controller;


import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.company.code.CompanyResultEnum;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.controller.dto.CompanyResponseInfoDTO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.user.controller.except.TokenValidationException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CompanyAPIController {

	private final CompanyService companyService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;
	
	@PostMapping("/company")
	public CommonResponseDTO<Object> enrollCompany(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, @Validated CompanyInfoDTO dto, BindingResult errors) {
		log.info("[enrollCompany => CompanyInfoDTO\n] {}", dto.toString());
		log.info("errors ==> {}", errors.hasErrors());
		
		if(errors.hasErrors()) {
			 CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(-7070, errors.getFieldError().getDefaultMessage());
			 return CommonResponseDTO.builder().data(resDTO).build();
		}
		
		if(dto.getComp_name().isEmpty() || 
				String.valueOf(dto.getGroup_no()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) 
			throw new TokenValidationException();
		
		if(!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		
		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		boolean result = companyService.saveCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS : CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

		log.info("[enrollCompany => CompanyResponseInfoDTO\n] {}", resDTO.toString());
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}	
	
	@PutMapping("/company")
	public CommonResponseDTO<Object> updateCompany(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, CompanyInfoDTO dto) {
		log.info("[updateCompany => CompanyInfoDTO\n] {}", dto.toString());
		if(String.valueOf(dto.getComp_no()).isEmpty() ||
				dto.getComp_name().isEmpty() || 
				String.valueOf(dto.getGroup_no()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) 
			throw new TokenValidationException();
		
		if(!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		
		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		boolean result = companyService.updateCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS : CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);
		

		
		log.info("[updateCompany => CompanyResponseInfoDTO\n] {}", resDTO.toString());
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@DeleteMapping("/company")
	public CommonResponseDTO<Object> deleteCompany(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, CompanyInfoDTO dto) {
		log.info("deleteCompany => CompanyInfoDTO : \n {}", dto.toString());
		if(String.valueOf(dto.getComp_no()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) 
			throw new TokenValidationException();
		
		if(!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		
		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		boolean result = companyService.deleteCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS : CompanyResultEnum.FAIL; 
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);
		
		log.info("deleteCompany => CompanyResponseInfoDTO : \n {}", resDTO.toString());
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
}
