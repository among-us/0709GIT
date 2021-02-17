package trustnet.auth.zone.controller.except;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.manager.code.ManagerResultEnum;
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.zone.code.ZoneResultEnum;
import trustnet.auth.zone.controller.dto.ZoneInfoResponseDTO;

@RestControllerAdvice("trustnet.auth.zone.controller")
@Slf4j
public class ZoneException {

	@ExceptionHandler(ParameterException.class)
	public CommonResponseDTO<Object> parameterException(ParameterException e) {
		log.warn("{}", e.getClass());
		ZoneResultEnum retEnum = ZoneResultEnum.PARAMETEREXCEPTION;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(BindException.class)
	public CommonResponseDTO<Object> bindException(BindException e) {
		log.warn("{}", e.getClass());
		ZoneResultEnum retEnum = ZoneResultEnum.BINDEXCEPTION;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	CommonResponseDTO<Object> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
		log.info("{}", e.getClass());
		ZoneResultEnum err = ZoneResultEnum.SQL_INTEGRITY_CONSTRAINT_VIOLATION;
		ZoneInfoResponseDTO resDTO = new ZoneInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
}
