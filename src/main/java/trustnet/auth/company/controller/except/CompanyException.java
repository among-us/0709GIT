package trustnet.auth.company.controller.except;

import java.net.BindException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.company.code.CompanyResultEnum;
import trustnet.auth.company.controller.dto.CompanyResponseInfoDTO;
import trustnet.auth.manager.controller.except.ParameterException;

@RestControllerAdvice("trustnet.auth.company.controller")
@Slf4j
public class CompanyException {

	@ExceptionHandler(ParameterException.class)
	CommonResponseDTO<Object> parameterException(ParameterException e) {
		log.warn("{}", e.getClass());
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO();
		CompanyResultEnum err = CompanyResultEnum.PARAMETEREMPTY;
		resDTO.setErrorCode(err.getErrorCode());
		resDTO.setErrorMessage(err.getErrorMessage());
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(BindException.class)
	public CommonResponseDTO<Object> bindException(BindException e) {
		CompanyResultEnum retEnum = CompanyResultEnum.BINDEXCEPTION;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
}
