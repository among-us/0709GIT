package trustnet.auth.user.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.dto.SecurityAuthInfoDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.user.code.UserResultEnum;
import trustnet.auth.user.controller.dto.UserCheckInfoDTO;
import trustnet.auth.user.controller.dto.UserInfoDTO;
import trustnet.auth.user.controller.dto.UserInfoResponseDTO;
import trustnet.auth.user.controller.dto.UserLoginInfoDTO;
import trustnet.auth.user.controller.except.LoginFailException;
import trustnet.auth.user.controller.except.PasswordLengthException;
import trustnet.auth.user.controller.except.RePasswordCheckException;
import trustnet.auth.user.controller.except.TokenValidationException;
import trustnet.auth.user.service.UserService;
import trustnet.auth.user.service.vo.UserHistoryInfoVO;
import trustnet.auth.user.service.vo.UserInfoVO;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserAPIController {

	private final ModelMapper modelMapper;
	private final UserService userService;
	private final SecurityService securityService;	

	
	@PostMapping("/user")
	public CommonResponseDTO<Object> enroll(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, @Validated UserInfoDTO userInfoDTO, BindingResult errors) {
		
		if(errors.hasErrors()) {
			 UserInfoResponseDTO resDTO = new UserInfoResponseDTO(-7070, errors.getFieldError().getDefaultMessage());
			 return CommonResponseDTO.builder().data(resDTO).build();
		}
		
		UserInfoVO vo = modelMapper.map(userInfoDTO, UserInfoVO.class);
		boolean result = securityService.passwordLengthValidation(vo);
		if(!result) 
			throw new PasswordLengthException();
		
		result = securityService.passwordValidation(vo);
		if(!result)
			throw new RePasswordCheckException();
		
		result = userService.saveUser(vo);
		UserResultEnum resultEnum = result ? UserResultEnum.SUCCESS : UserResultEnum.SAVEERROR;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(resultEnum);
		
		if(!result)
			return CommonResponseDTO.builder().data(resDTO).build();
		
		UserInfoVO userInfoVO = userService.findUserInfoAsUserID(vo);
		UserHistoryInfoVO historyVO = modelMapper.map(userInfoVO, UserHistoryInfoVO.class);
		log.info(historyVO.toString());
		historyVO.setAction("C");
		result = userService.saveUserHistory(historyVO);
		return CommonResponseDTO.builder().data(resDTO).build();
		
		
	}
	
	@PutMapping("/user")
	public CommonResponseDTO<Object> update(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, UserInfoDTO userInfoDTO) {
		UserInfoVO vo = modelMapper.map(userInfoDTO, UserInfoVO.class);
		boolean result = userService.updateUserInfo(vo);
		UserResultEnum retEnum = result ? UserResultEnum.SUCCESS : UserResultEnum.FAIL;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(retEnum);
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) 
			throw new TokenValidationException();
		
		if(!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		
		UserInfoVO userInfoVO = userService.findUserInfoAsUserNO(vo);
		log.info(userInfoVO.toString());
		UserHistoryInfoVO historyVO = modelMapper.map(userInfoVO, UserHistoryInfoVO.class);
		String actionValue = userInfoVO.getExist().equalsIgnoreCase("Y") ? "U" : "D";
		historyVO.setAction(actionValue);
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = userService.saveUserHistory(historyVO);
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@PutMapping("/userAccount")
	public CommonResponseDTO<Object> updateAccount(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, UserInfoDTO userInfoDTO) {
		UserResultEnum retEnum;
		UserInfoResponseDTO resDTO;
		boolean result = true;
		UserInfoVO userVO = modelMapper.map(userInfoDTO, UserInfoVO.class);
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) 
			throw new TokenValidationException();
		
		result = securityService.passwordLengthValidation(userVO);
		if(!result) 
			throw new PasswordLengthException();
		
		result = securityService.passwordValidation(userVO);
		if(!result) 
			throw new RePasswordCheckException();
		
		result = securityVO.getUser_id().equalsIgnoreCase(userVO.getUser_id()) ? true : false;
		boolean serviceResult = result ? userService.updateUserPW(userVO) : false;
		
		retEnum = serviceResult ? UserResultEnum.SUCCESS : UserResultEnum.FAIL;
		resDTO = new UserInfoResponseDTO(retEnum);
		
		UserInfoVO userInfoVO = userService.findUserInfoAsUserID(userVO);
		UserHistoryInfoVO historyVO = modelMapper.map(userInfoVO, UserHistoryInfoVO.class);
		historyVO.setAction("U");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = userService.saveUserHistory(historyVO);
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@GetMapping("/user")
	public CommonResponseDTO<Object> checkID(@Validated UserCheckInfoDTO userCheckInfoDTO, BindingResult errors) {
		
		if(errors.hasErrors()) {
			 UserInfoResponseDTO resDTO = new UserInfoResponseDTO(-7070, errors.getFieldError().getDefaultMessage());
			 return CommonResponseDTO.builder().data(resDTO).build();
		}
		
		UserInfoVO vo = modelMapper.map(userCheckInfoDTO, UserInfoVO.class);
		boolean result = userService.isFindUser(vo);
		UserResultEnum retEnum = result ? UserResultEnum.EXISTFAIL : UserResultEnum.EXISTSUCCESS ;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	@PostMapping("/login")
	public CommonResponseDTO<Object> login(UserLoginInfoDTO loginInfoDTO, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		
		UserInfoVO vo = modelMapper.map(loginInfoDTO, UserInfoVO.class);
		UserInfoVO retVO = userService.doLogin(vo);
		boolean result = retVO.isLogin();

		if(!result) {
			log.warn("LOGIN FAIL EXCEPTION");
			throw new LoginFailException();
		}
		
		SecurityAuthInfoVO authVO = modelMapper.map(retVO, SecurityAuthInfoVO.class);
		SecurityAuthInfoVO retAuthVo = securityService.createToken(authVO);
		SecurityAuthInfoDTO retAuthDTO = modelMapper.map(retAuthVo, SecurityAuthInfoDTO.class);
		
		Cookie cookie = new Cookie("UNETAUTHTOKEN", retAuthDTO.getValue());
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);

		UserResultEnum resultEnum = UserResultEnum.SUCCESS;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(resultEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	

}
