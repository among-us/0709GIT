package trustnet.auth.manager.controller.except;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.manager.code.ManagerResultEnum;
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.user.controller.except.TokenValidationException;

@RestControllerAdvice("trustnet.auth.manager.controller")
@Slf4j
public class ManagerException {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public void IllegalArgumentException(Exception e) {
		log.info("{}", e.getClass());
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public void NumberFormatException(Exception e) {
		log.info("{}", e.getClass());
	}

	@ExceptionHandler(BindException.class)
	public CommonResponseDTO<Object> BindException(BindException e) {
		log.info("{}", e.getClass());
		
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO();
		ManagerResultEnum err = ManagerResultEnum.BINDEXCEPTION;
		resDTO.setErrorCode(err.getErrorCode());
		resDTO.setErrorMessage(err.getErrorMessage());
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(ParameterException.class)
	CommonResponseDTO<Object> parameterException(ParameterException e) {
		log.info("{}", e.getClass());
		
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO();
		ManagerResultEnum err = ManagerResultEnum.PARAMETEREMPTY;
		resDTO.setErrorCode(err.getErrorCode());
		resDTO.setErrorMessage(err.getErrorMessage());
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(TokenValidationException.class)
	CommonResponseDTO<Object> tokenValidationException(TokenValidationException e) {
		log.info("{}", e.getClass());
		ManagerResultEnum err = ManagerResultEnum.TOKENVALIDATION;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
//	SQLIntegrityConstraintViolationException
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	CommonResponseDTO<Object> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
		log.info("{}", e.getClass());
		ManagerResultEnum err = ManagerResultEnum.SQL_INTEGRITY_CONSTRAINT_VIOLATION;
		ManagerInfoResponseDTO resDTO = new ManagerInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
}
