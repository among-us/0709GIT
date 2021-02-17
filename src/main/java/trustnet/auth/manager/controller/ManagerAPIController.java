package trustnet.auth.manager.controller;

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
import trustnet.auth.manager.controller.dto.ManagerCheckInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.manager.service.ManagerService;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.user.controller.except.TokenValidationException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerAPIController{

	private final ManagerService enrollService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;
	
	@PostMapping("/manager")
	public CommonResponseDTO<Object> enrollManager(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, @Validated ManagerInfoDTO managerInfoDTO, BindingResult errors) {
		
		if(	managerInfoDTO.getTam_local_port() == 0 ||
				managerInfoDTO.getTam_admin_port() == 0){
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		
		if(errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070, errors.getFieldError().getDefaultMessage());
			 return CommonResponseDTO.builder().data(resDTO).build();
		}
		
		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);
		boolean result = enrollService.saveManagerInfo(vo);
		ManagerResultEnum resultEnum = result ? ManagerResultEnum.SUCCESS : ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(resultEnum);
		
		if(!result)
			return CommonResponseDTO.builder().data(resDTO).build();
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0)
			throw new TokenValidationException();

		if(!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		
		ManagerInfoVO findVO = enrollService.findManager(vo);
		ManagerHistoryInfoVO historyVO = modelMapper.map(findVO, ManagerHistoryInfoVO.class);
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		historyVO.setAction("C");
		result = enrollService.saveManagerHistoryInfo(historyVO);
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@GetMapping("/manager")
	public CommonResponseDTO<Object> checkManager(@Validated ManagerCheckInfoDTO managerInfoDTO, BindingResult errors) {
		
		if(errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070, errors.getFieldError().getDefaultMessage());
			 return CommonResponseDTO.builder().data(resDTO).build();
		}
		
		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);
		boolean result = enrollService.isFindManager(vo);
		ManagerResultEnum err = result ? ManagerResultEnum.EXISTMANAGER : ManagerResultEnum.NOEXISTMANAGER;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(err);
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/manager")
	public CommonResponseDTO<Object> updateManager(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, @Validated ManagerInfoDTO managerInfoDTO, BindingResult errors) {

		if(	managerInfoDTO.getTam_local_port() == 0 ||
				managerInfoDTO.getTam_admin_port() == 0){
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		
		if(errors.hasErrors()) {
			ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(-7070, errors.getFieldError().getDefaultMessage());
			 return CommonResponseDTO.builder().data(resDTO).build();
		}
		
		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);

		boolean result = enrollService.updateManagerWithID(vo);
		ManagerResultEnum retEnum = result ? ManagerResultEnum.SUCCESS : ManagerResultEnum.SAVEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(retEnum);
		
		if(!result)
			return CommonResponseDTO.builder().data(resDTO).build();
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0)
			throw new TokenValidationException();

		if(!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		
		ManagerInfoVO findVO = enrollService.findManager(vo);
		ManagerHistoryInfoVO historyVO = modelMapper.map(findVO, ManagerHistoryInfoVO.class);
		String actionValue = historyVO.getExist().equalsIgnoreCase("Y") ? "U" : "D";
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		historyVO.setAction(actionValue);
		result = enrollService.saveManagerHistoryInfo(historyVO);
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@DeleteMapping("/manager")
	public CommonResponseDTO<Object> deleteManager(ManagerInfoDTO managerInfoDTO) {
		
		if(String.valueOf(managerInfoDTO.getTam_no()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		
		ManagerInfoVO vo = modelMapper.map(managerInfoDTO, ManagerInfoVO.class);
		boolean result = enrollService.deleteManagerWithID(vo);
		ManagerResultEnum retEnum = result ? ManagerResultEnum.SUCCESS : ManagerResultEnum.DELETEERROR;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(retEnum);
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
}
