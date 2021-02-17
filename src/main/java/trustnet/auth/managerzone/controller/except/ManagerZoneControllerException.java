package trustnet.auth.managerzone.controller.except;

import java.net.BindException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.managerzone.code.ManagerZoneResultEnum;
import trustnet.auth.managerzone.controller.dto.ManagerZoneResponseDTO;
import trustnet.auth.zone.code.ZoneResultEnum;
import trustnet.auth.zone.controller.dto.ZoneInfoResponseDTO;

@RestControllerAdvice("trustnet.auth.managerzone.controller")
@Slf4j
public class ManagerZoneControllerException {

	@ExceptionHandler(NullPointerException.class)
	public ModelAndView NullPointerException(NullPointerException e) {
		log.warn("{}", e.getClass());
		ModelAndView mav = new ModelAndView("/error/GeneralError");
		return mav;
	}
	
	@ExceptionHandler(ParameterException.class)
	public CommonResponseDTO<Object> parameterException(ParameterException e){
		log.warn("{}", e.getClass());
		ManagerZoneResultEnum retEnum = ManagerZoneResultEnum.PARAMETEREXCEPTION;
		ManagerZoneResponseDTO resDTO = new ManagerZoneResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(BindException.class)
	public CommonResponseDTO<Object> bindException(BindException e) {
		log.warn("{}", e.getClass());
		ManagerZoneResultEnum retEnum = ManagerZoneResultEnum.BINDEXCEPTION;
		ManagerZoneResponseDTO resDTO = new ManagerZoneResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
}
