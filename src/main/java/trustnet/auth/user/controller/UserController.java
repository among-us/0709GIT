package trustnet.auth.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.log.service.LogService;
import trustnet.auth.session.obj.SessionObject;
import trustnet.auth.user.controller.dto.UserHistoryInfoDTO;
import trustnet.auth.user.controller.dto.UserInfoDTO;
import trustnet.auth.user.service.UserService;
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
	
	@GetMapping("/")
	public ModelAndView indexPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Login");
		return mav;
	}

	@GetMapping("/user/myAccountPage")
	public ModelAndView accountPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Account");
		return mav;
	}
	
	@GetMapping("/user/enrollPage")
	public ModelAndView enrollPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Enroll");
		log.info("hallo...");
		List<CompanyInfoVO> voList = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> dtoList = 
				mapper.map(voList, new TypeToken<List<CompanyInfoDTO>>() {}.getType());
		mav.addObject("companyList", dtoList);
		return mav;
	}
	
	@GetMapping("/user/updatePage")
	public ModelAndView updatePage(UserInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/Update");
		UserInfoVO vo = mapper.map(dto, UserInfoVO.class);
		UserInfoVO retVO = userService.findAllUserWithCompASUserNO(vo);
		UserInfoDTO retDTO = mapper.map(retVO, UserInfoDTO.class);
		
		List<CompanyInfoVO> companyVoList = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> companyDtoList = 
				mapper.map(companyVoList, new TypeToken<List<CompanyInfoDTO>>() {}.getType());
		
		mav.addObject("userInfo", retDTO);
		mav.addObject("companyInfoList", companyDtoList);
		return mav;
	}
	
	@GetMapping("/user/listPage")
	public ModelAndView listPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/user/List");
		List<UserInfoVO> voList = userService.findAllUserWithComp();
		List<UserInfoDTO> dtoList = 
				mapper.map(voList, new TypeToken<List<UserInfoDTO>>() {}.getType());

		mav.addObject("userList", dtoList);
		return mav;    
	}
	
	@GetMapping("/user/historyPage")
	public ModelAndView historyPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/user/History");
		List<UserInfoVO> voList = userService.findAllUserWithComp();
		List<UserInfoDTO> dtoList = 
				mapper.map(voList, new TypeToken<List<UserInfoDTO>>() {}.getType());

		mav.addObject("userList", dtoList);
		return mav;    
	}	
	
	@GetMapping("/user/historyDetailPage")
	public ModelAndView historyDetailPage(UserInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/user/HistoryDetail");
		UserInfoVO vo = mapper.map(dto, UserInfoVO.class);
		List<UserHistoryInfoVO> voList = userService.findAllUserHistoryAsUserNo(vo);
		List<UserHistoryInfoDTO> dtoList = mapper.map(voList, new TypeToken<List<UserHistoryInfoVO>> () {}.getType());
		mav.addObject("userHistoryList", dtoList);
		log.info("userHistoryList => {}", dtoList);
		return mav;    
	}	
	
//	@PostMapping("/main")
//	public ModelAndView loginTryPage(LoginInfoDTO loginInfoDTO, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
//		ModelAndView mav = new ModelAndView("thymeleaf/main/Main");
//		UserInfoVO vo = mapper.map(loginInfoDTO, UserInfoVO.class);
//		UserInfoVO retVO = userService.doLogin(vo);
//		log.info(retVO.toString());
//		boolean result = retVO.isLogin();
//		UserResultEnum resultEnum = (result == true) ? UserResultEnum.SUCCESS : UserResultEnum.LOGINERROR;
//		UserInfoResponseDTO resDTO = new UserInfoResponseDTO(resultEnum);
//		
//		if(!resultEnum.isSuccess()) {
//			mav.setViewName("thymeleaf/user/Login");
//			mav.addObject("errorCode", resDTO);
//			return mav;
//		}
//		
//		SecurityAuthInfoVO authVO = mapper.map(retVO, SecurityAuthInfoVO.class);
//		SecurityAuthInfoVO retAuthVo = securityService.createToken(authVO);
//		SecurityAuthInfoDTO retAuthDTO = mapper.map(retAuthVo, SecurityAuthInfoDTO.class);
//		
//		Cookie cookie = new Cookie("UNETAUTHTOKEN", retAuthDTO.getValue());
//		cookie.setPath(request.getContextPath());
//		response.addCookie(cookie);
//		
//		return mav;
//	}
	
	@GetMapping("/main")
	public ModelAndView mainPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/main/Main");
		return mav;
	}
	
	
	@GetMapping("/logout") 
	public ModelAndView logoutPage(HttpServletResponse response) { 
		ModelAndView mav = new ModelAndView("thymeleaf/user/Login");
		Cookie cookie = new Cookie("UNETAUTHTOKEN", null); 
		cookie.setMaxAge(0);
		response.addCookie(cookie); return mav; 
	}
	 
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/user/session", method=RequestMethod.POST) public
	 * CommonResponseDTO<Object> getSession(){ return
	 * CommonResponseDTO.builder().data(sessionObj).build(); }
	 */
	

}
