package trustnet.auth.user.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.dto.SecurityAuthInfoDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.company.controller.dto.CompanyProjectInfoDTO;
import trustnet.auth.company.service.vo.CompanyProjectInfoVO;
import trustnet.auth.user.code.UserResultEnum;
import trustnet.auth.user.controller.dto.ChartDTO;
import trustnet.auth.user.controller.dto.ChartDailyDTO;
import trustnet.auth.user.controller.dto.UserCheckInfoDTO;
import trustnet.auth.user.controller.dto.UserInfoDTO;
import trustnet.auth.user.controller.dto.UserInfoResponseDTO;
import trustnet.auth.user.controller.dto.UserLoginInfoDTO;
import trustnet.auth.user.controller.except.LoginFailException;
import trustnet.auth.user.controller.except.NoPermissionException;
import trustnet.auth.user.controller.except.PasswordLengthException;
import trustnet.auth.user.controller.except.RePasswordCheckException;
import trustnet.auth.user.controller.except.TokenValidationException;
import trustnet.auth.user.controller.except.UserExistException;
import trustnet.auth.user.controller.except.UserNoneException;
import trustnet.auth.user.service.UserService;
import trustnet.auth.user.service.vo.ChartDailyVO;
import trustnet.auth.user.service.vo.ChartVO;
import trustnet.auth.user.service.vo.UserHistoryInfoVO;
import trustnet.auth.user.service.vo.UserInfoVO;
import trustnet.tas.except.TNAuthException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserAPIController {

	private final ModelMapper modelMapper;
	private final UserService userService;
	private final SecurityService securityService;

	@PostMapping("/user")
	public CommonResponseDTO<Object> enroll(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated UserInfoDTO userInfoDTO, BindingResult errors)
			throws TNAuthException {

		if (errors.hasErrors()) {
			UserInfoResponseDTO resDTO = new UserInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		UserInfoVO vo = modelMapper.map(userInfoDTO, UserInfoVO.class);

		boolean result = securityService.passwordLengthValidation(vo); // 길이 체크 로직

		if (!result)
			throw new PasswordLengthException();

		result = securityService.passwordValidation(vo); // passwd repasswd 일치 여부 로직
		if (!result)
			throw new RePasswordCheckException();

		result = userService.saveUser(vo);

		UserResultEnum resultEnum = result ? UserResultEnum.SUCCESS
				: UserResultEnum.SAVEERROR;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(resultEnum);

		if (!result)
			return CommonResponseDTO.builder().data(resDTO).build();

		UserInfoVO userInfoVO = userService.findUserInfoAsUserID(vo);

		UserHistoryInfoVO historyVO = modelMapper.map(userInfoVO,
				UserHistoryInfoVO.class);
		historyVO.setAction("C");
		result = userService.saveUserHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();

	}

	@PutMapping("/user")
	public CommonResponseDTO<Object> update(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO userInfoDTO, HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView)
			throws JsonProcessingException {
		UserInfoVO vo = modelMapper.map(userInfoDTO, UserInfoVO.class);
		boolean result = userService.updateUserInfo(vo);
		UserResultEnum retEnum = result ? UserResultEnum.SUCCESS : UserResultEnum.FAIL;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(retEnum);

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) {
			throw new TokenValidationException();
		}

		if (!(securityVO.getPerm_no() == 1)) {
			throw new TokenValidationException();
		}

		UserInfoVO userInfoVO = userService.findUserInfoAsUserNO(vo);
		UserHistoryInfoVO historyVO = modelMapper.map(userInfoVO,
				UserHistoryInfoVO.class);
		String actionValue = userInfoVO.getExist().equalsIgnoreCase("Y") ? "U" : "D"; // Exist = No 변경 시 Action Delete 가 들어감. 
		historyVO.setAction(actionValue);
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		historyVO.setZone_no(userInfoVO.getZone_no());
		result = userService.saveUserHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/user/permission")
	public CommonResponseDTO<Object> userPermission(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);

		JSONObject result = new JSONObject();
		result.put("errorCode", 0);
		result.put("errorMessage", "default");

		if (securityVO.getPerm_no() != 1 || securityVO.getUser_id() == null) {
			result.put("errorCode", -1);
			result.put("errorMessage", "권한이 없습니다");
		}

		return CommonResponseDTO.builder().data(result).build();

	}

	@PutMapping("/userAccount")
	public CommonResponseDTO<Object> updateAccount(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO userInfoDTO) throws TNAuthException {
		UserResultEnum retEnum;
		UserInfoResponseDTO resDTO;
		boolean result = true;
		UserInfoVO userVO = modelMapper.map(userInfoDTO, UserInfoVO.class);
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		result = securityService.passwordLengthValidation(userVO);
		if (!result)
			throw new PasswordLengthException();

		result = securityService.passwordValidation(userVO);
		if (!result)
			throw new RePasswordCheckException();

		result = securityVO.getUser_id().equalsIgnoreCase(userVO.getUser_id()) ? true
				: false;
		boolean serviceResult = result ? userService.updateUserPW(userVO) : false;

		retEnum = serviceResult ? UserResultEnum.SUCCESS : UserResultEnum.FAIL;
		resDTO = new UserInfoResponseDTO(retEnum);

		UserInfoVO userInfoVO = userService.findUserInfoAsUserID(userVO);
		UserHistoryInfoVO historyVO = modelMapper.map(userInfoVO,
				UserHistoryInfoVO.class);
		historyVO.setAction("U"); //PUT은 U로 고정하나 ?
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = userService.saveUserHistory(historyVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/user")
	public CommonResponseDTO<Object> checkID(@Validated UserCheckInfoDTO userCheckInfoDTO,
			BindingResult errors) {

		if (errors.hasErrors()) {
			UserInfoResponseDTO resDTO = new UserInfoResponseDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		UserInfoVO vo = modelMapper.map(userCheckInfoDTO, UserInfoVO.class);
		boolean result = userService.isFindUser(vo);
		UserResultEnum retEnum = result ? UserResultEnum.EXISTFAIL
				: UserResultEnum.EXISTSUCCESS;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(retEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PostMapping("/login")
	public CommonResponseDTO<Object> login(UserLoginInfoDTO loginInfoDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {
		UserInfoVO vo = modelMapper.map(loginInfoDTO, UserInfoVO.class);
		String id = vo.getUser_id();
		String exist = userService.getExist(id);
		int perm_no = userService.getPermNo(id);
		if (perm_no == 0) {
			log.warn("NO PERMISSION EXCEPTION >> USER ID IS " + vo.getUser_id());
			throw new NoPermissionException();
		}
		if (exist.equals("N")) {
			log.warn("EXIST NO EXCEPTION");
			throw new UserExistException();
		}

		if (exist == "NONE") {
			log.warn("EXIST NONE EXCEPTION");
			throw new UserNoneException();
		}

		UserInfoVO retVO = userService.doLogin(vo);

		boolean result = retVO.isLogin();

		if (!result) {
			log.warn("LOGIN FAIL EXCEPTION");
			throw new LoginFailException();
		}

		SecurityAuthInfoVO authVO = modelMapper.map(retVO, SecurityAuthInfoVO.class);
		SecurityAuthInfoVO retAuthVo = securityService.createToken(authVO);
		SecurityAuthInfoDTO retAuthDTO = modelMapper.map(retAuthVo,SecurityAuthInfoDTO.class);

		Cookie cookie = new Cookie("UNETAUTHTOKEN", retAuthDTO.getValue());
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);

		UserResultEnum resultEnum = UserResultEnum.SUCCESS;
		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(resultEnum);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/project/matching")
	public String[] getProjectByCompName(@Validated CompanyProjectInfoDTO dto,
			BindingResult errors) {
		int comp_no = dto.getComp_no();
		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		String VOLIST[] = userService.getProjectByCompName(comp_no);

		return VOLIST;
	}

	@GetMapping("/user/profile")
	public CommonResponseDTO<Object> checkPwd(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO dto) throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		String user_id = securityVO.getUser_id();
		int user_no = securityVO.getUser_no();

		UserInfoVO vo = modelMapper.map(dto, UserInfoVO.class);
		vo.setUser_id(user_id);
		vo.setUser_no(user_no);

		boolean retVO = userService.checkPwd(vo);
		if (retVO == true) {
			return CommonResponseDTO.builder().data(true).build();
		} else {
			return CommonResponseDTO.builder().data(false).build();
		}
	}

	@GetMapping("/user/perm-code")
	public CommonResponseDTO<Object> checkPermCode(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO dto) throws TNAuthException {

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		return CommonResponseDTO.builder().data(perm_code).build();
	}

	@GetMapping("/chart/hourly/first-access")
	public CommonResponseDTO<Object> firstAccessHourlyChart(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ChartDTO dto) throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int user_no = securityVO.getUser_no();
		String zone_name = userService.getZoneNameWithUserNo(user_no);
		ChartVO vo = modelMapper.map(dto, ChartVO.class);
		vo.setZone_name(zone_name);

		List<ChartVO> voList = userService.getChartInfo(vo);

		if (voList.size() == 0) {
			JSONObject test = new JSONObject();
			test.put("errorCode", -1);
			test.put("errorMessage", "해당 조건의 범위에 일치하는 데이터가 존재하지 않습니다");

			return CommonResponseDTO.builder().data(test).build();
		}

		return CommonResponseDTO.builder().data(voList).build();
	}

	@GetMapping("/chart/hourly")
	public CommonResponseDTO<Object> hourlyChart(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ChartDTO dto) throws TNAuthException {

		ChartVO vo = modelMapper.map(dto, ChartVO.class);

		List<ChartVO> voList = userService.getChartInfo(vo);

		if (voList.size() == 0) {
			JSONObject test = new JSONObject();
			test.put("errorCode", -1);
			test.put("errorMessage", "해당 조건의 범위에 일치하는 데이터가 존재하지 않습니다");

			return CommonResponseDTO.builder().data(test).build();
		}

		return CommonResponseDTO.builder().data(voList).build();
	}

	@GetMapping("/main/total")
	public CommonResponseDTO<Object> totalChart(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ChartDTO dto) throws TNAuthException {
		ChartVO vo = modelMapper.map(dto, ChartVO.class);
		ChartVO voList = userService.getTotalCount(vo);
		return CommonResponseDTO.builder().data(voList).build();
	}

	@GetMapping("/main/permission")
	public CommonResponseDTO<Object> checkPermission(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO dto, HttpServletRequest request, HttpServletResponse response)
			throws TNAuthException, JsonProcessingException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);

		int perm_no = securityVO.getPerm_no();
		int user_no = securityVO.getUser_no();

		UserInfoVO vo = modelMapper.map(dto, UserInfoVO.class);

		vo.setUser_no(user_no);
		UserInfoVO resultVO = userService.getUserInfoWithUserNo(vo);
		int resultUserNo = resultVO.getUser_no();

		if (resultUserNo == 0) {
			JSONObject error = new JSONObject();
			error.put("errorCode", -1);
			error.put("errorMessage", "NO EXIST");
			return CommonResponseDTO.builder().data(error).build();
		}

		JSONObject chartInfo = new JSONObject();
		JSONArray chartArray = new JSONArray();
		chartArray.add(0, resultVO.getComp_no());
		chartArray.add(1, resultVO.getComp_name());
		chartArray.add(2, resultVO.getUser_no());
		chartArray.add(3, resultVO.getPerm_no());
		chartArray.add(4, resultVO.getZone_no());
		chartArray.add(5, resultVO.getZone_name());

		if (perm_no == 1) {
			chartInfo.put("permNo", 1);
			chartInfo.put("permInfo", "admin");
			chartArray.add(chartInfo);

			return CommonResponseDTO.builder().data(chartArray).build();
		}
		if (perm_no == 10) {
			chartInfo.put("permNo", 10);
			chartInfo.put("permInfo", "group");
			chartArray.add(chartInfo);
			return CommonResponseDTO.builder().data(chartArray).build();
		}
		if (perm_no == 20) {
			chartInfo.put("permNo", 20);
			chartInfo.put("permInfo", "project");
			chartArray.add(chartInfo);
			return CommonResponseDTO.builder().data(chartArray).build();
		}

		return CommonResponseDTO.builder().data("else").build();
	}

	@GetMapping("/chart/daily")
	public CommonResponseDTO<Object> dailyChart(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ChartDailyDTO dto) throws TNAuthException {

		ChartDailyVO vo = modelMapper.map(dto, ChartDailyVO.class);
		List<ChartDailyVO> voList = userService.getDailyChartInfo(vo);

		if (voList.size() == 1) {
			List<ChartDailyVO> voSingleList = new ArrayList<ChartDailyVO>();
			voSingleList.add(voList.get(0));
			return CommonResponseDTO.builder().data(voSingleList).build();

		}
		if (voList.size() == 2) {
			List<ChartDailyVO> voDoubleList = new ArrayList<ChartDailyVO>();
			voDoubleList.add(voList.get(0));
			voDoubleList.add(voList.get(1));
			return CommonResponseDTO.builder().data(voDoubleList).build();
		}

		if (voList.isEmpty()) {
			JSONObject emptyObj = new JSONObject();
			emptyObj.put("errorCode", -1);
			emptyObj.put("errorMessage", "해당 조건의 범위에 일치하는 데이터가 존재하지 않습니다");

			return CommonResponseDTO.builder().data(emptyObj).build();
		}

		return CommonResponseDTO.builder().data(voList).build();
	}

	@GetMapping("/daily/total")
	public CommonResponseDTO<Object> dailyChartTotal(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ChartDailyDTO dto) throws TNAuthException {

		ChartDailyVO vo = modelMapper.map(dto, ChartDailyVO.class);
		ChartDailyVO voList = userService.getDailyChartTotalInfo(vo);

		return CommonResponseDTO.builder().data(voList).build();
	}

	@GetMapping("/user/account-info")
	public CommonResponseDTO<Object> getAccountInfo(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO dto) throws TNAuthException {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int user_no = securityVO.getUser_no();
		int perm_no = userService.getPermNoWithUserNo(user_no);

		return CommonResponseDTO.builder().data(perm_no).build();
	}

}
// controller end
