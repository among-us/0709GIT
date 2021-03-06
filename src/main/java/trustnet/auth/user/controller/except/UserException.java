package trustnet.auth.user.controller.except;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.interceptor.GeneralInterceptor;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.user.code.UserResultEnum;
import trustnet.auth.user.controller.dto.UserInfoResponseDTO;

@RestControllerAdvice("trustnet.auth.user.controller")
@Slf4j
public class UserException extends RuntimeException{

	@ExceptionHandler(BindException.class)
	public CommonResponseDTO<Object> BindException(BindException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.BINDEXCEPTION;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(ParameterException.class)
	CommonResponseDTO<Object> parameterException(ParameterException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.PARAMETEREMPTY;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(ParameterLengthException.class)
	CommonResponseDTO<Object> parameterLengthException(ParameterLengthException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.PARAMETERLENGTHEXCEPTION;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(PasswordLengthException.class)
	CommonResponseDTO<Object> passwordLengthException(PasswordLengthException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.PASSWORDLENGTEH;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(RePasswordCheckException.class)
	CommonResponseDTO<Object> rePasswordCheckException(RePasswordCheckException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.REPASSWORDCHECK;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(TokenValidationException.class)
	CommonResponseDTO<Object> tokenValidationException(TokenValidationException e) {
		log.info("{}", e.getClass());
		log.info(">>>>> TOKEN VALID ERROR <<<<<");
		UserResultEnum err = UserResultEnum.TOKENVALIDATION;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		GeneralInterceptor a = new GeneralInterceptor(null, null, null, null);
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(LoginFailException.class)
	CommonResponseDTO<Object> loginFailException(LoginFailException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.LOGINERROR;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(UserExistException.class)
	CommonResponseDTO<Object> userExistException(UserExistException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.EXISTERROR;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(UserNoneException.class)
	CommonResponseDTO<Object> userNoneException(UserNoneException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.NONEERROR;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@ExceptionHandler(NoPermissionException.class)
	CommonResponseDTO<Object> NopermissionException(NoPermissionException e) {
		log.info("{}", e.getClass());
		UserResultEnum err = UserResultEnum.EXISTERROR;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
}
