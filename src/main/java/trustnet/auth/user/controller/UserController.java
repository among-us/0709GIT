package trustnet.auth.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.SecurityAuthInfoDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.log.service.LogService;
import trustnet.auth.session.obj.SessionObject;
import trustnet.auth.user.controller.dto.UserHistoryInfoDTO;
import trustnet.auth.user.controller.dto.UserInfoDTO;
import trustnet.auth.user.service.UserService;
import trustnet.auth.user.service.vo.MainDashboardCountVO;
import trustnet.auth.user.service.vo.UserHistoryInfoVO;
import trustnet.auth.user.service.vo.UserInfoVO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final LogService logService;
	private final UserService userService;
	private final ModelMapper mapper;
	private final ObjectMapper objectMapper;
	private final SecurityService securityService;
	private final CompanyService companyService;

	@Resource
	private SessionObject sessionObj;

	@GetMapping("/permission/error")
	public ModelAndView errorPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {
		ModelAndView mav = new ModelAndView("thymeleaf/include/error");

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);

		int user_no = securityVO.getUser_no();
		String user_id = securityVO.getUser_id();
		int perm_no = securityVO.getPerm_no();

		SecurityAuthInfoVO paramVO = new SecurityAuthInfoVO();
		paramVO.setUsable(true);
		paramVO.setUser_no(user_no);
		paramVO.setUser_id(user_id);
		paramVO.setPerm_no(perm_no);

		SecurityAuthInfoVO retAuthVo = securityService.createToken(paramVO);
		SecurityAuthInfoDTO retAuthDTO = mapper.map(retAuthVo, SecurityAuthInfoDTO.class);

		Cookie cookie = new Cookie("UNETAUTHTOKEN", retAuthDTO.getValue());
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		//		cookie.setPath(request.getContextPath());

		return mav;
	}

	@GetMapping("/")
	public ModelAndView indexPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Login");
		return mav;
	}

	@GetMapping("/user/myAccountPage")
	public ModelAndView accountPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Account");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();
		int user_no = securityVO.getUser_no();
		UserInfoVO vo = userService.getUserInfo(user_no);
		
		mav.addObject("userInfo", vo);
		mav.addObject("permCode", perm_code);

		return mav;
	}

	@GetMapping("/user/enrollPage")
	public ModelAndView enrollPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Enroll");
		List<CompanyInfoVO> voList = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> dtoList = mapper.map(voList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());
		mav.addObject("companyList", dtoList);
		return mav;
	}

	@GetMapping("/user/updatePage")
	public ModelAndView updatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Update");
		UserInfoVO vo = mapper.map(dto, UserInfoVO.class);
		UserInfoVO retVO = userService.findAllUserWithCompASUserNO(vo);
		UserInfoDTO retDTO = mapper.map(retVO, UserInfoDTO.class);

		List<CompanyInfoVO> companyVoList = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> companyDtoList = mapper.map(companyVoList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());

		int user_no = dto.getUser_no();
		ArrayList<String> projectName = new ArrayList<String>();

		UserInfoVO testvo = userService.getUserInfo(user_no);
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("test", testvo);
		mav.addObject("userInfo", retDTO);
		mav.addObject("companyInfoList", companyDtoList);

		return mav;
	}

	@GetMapping("/user/listPage")
	public ModelAndView listPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/List");

		List<UserInfoVO> voList = userService.findAllUserWithComp();
		List<UserInfoDTO> dtoList = mapper.map(voList,
				new TypeToken<List<UserInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("userList", dtoList);
		return mav;
	}

	@GetMapping("/user/historyPage")
	public ModelAndView historyPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/History");
		List<UserInfoVO> voList = userService.findAllUserWithComp();
		List<UserInfoDTO> dtoList = mapper.map(voList,
				new TypeToken<List<UserInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("userList", dtoList);
		return mav;
	}

	@GetMapping("/user/historyDetailPage")
	public ModelAndView historyDetailPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			UserInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/HistoryDetail");
		UserInfoVO vo = mapper.map(dto, UserInfoVO.class);
		List<UserHistoryInfoVO> voList = userService.findAllUserHistoryAsUserNo(vo);
		List<UserHistoryInfoDTO> dtoList = mapper.map(voList,
				new TypeToken<List<UserHistoryInfoVO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("userHistoryList", dtoList);
		return mav;
	}

	@GetMapping("/main")
	public ModelAndView mainPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/main/Main");

		List<CompanyInfoVO> voAllList = companyService.findAllGroup();
		List<CompanyInfoDTO> dtoAllList = mapper.map(voAllList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());

		List<CompanyInfoVO> compVOList = companyService.findAllCompanyInfo();
		List<CompanyInfoDTO> compDTOList = mapper.map(compVOList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());

		MainDashboardCountVO voList = companyService.getDashboardCount();

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();
		String returnDate = format.format(date);
		String value = returnDate + " 기준";

		mav.addObject("now", value);
		mav.addObject("permCode", perm_code);
		mav.addObject("groupList", dtoAllList);
		mav.addObject("compList", compDTOList);
		mav.addObject("mainCount", voList);

		return mav;
	}

	@GetMapping("/logout")
	public ModelAndView logoutPage(HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Login");
		Cookie cookie = new Cookie("UNETAUTHTOKEN", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return mav;
	}

	@GetMapping("/CHART")
	public ModelAndView chartTestPage(HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Chart");

		return mav;
	}

}