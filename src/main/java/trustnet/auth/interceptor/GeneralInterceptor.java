package trustnet.auth.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.SecurityAuthInfoDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.interceptor.obj.PermissionObj;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeneralInterceptor implements HandlerInterceptor {

	private final SecurityService securityService;
	private final PermissionObj permissionObj;
	private final ModelMapper modelMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		SecurityAuthInfoVO vo = securityService.checkTokenValidation(request);
		boolean useble = vo.isUsable();
		boolean allowAccess = true;
		String permission = "";
		
		log.info("usable => {}", useble);
		if(useble) {		
			permission = vo.getPermissions();
			allowAccess = permissionObj.allowAdminPage(permission, request);
			
			SecurityAuthInfoVO retAuthVo = securityService.createToken(vo);
			SecurityAuthInfoDTO retAuthDTO = modelMapper.map(retAuthVo, SecurityAuthInfoDTO.class);
			Cookie cookie = new Cookie("UNETAUTHTOKEN", retAuthDTO.getValue());
			cookie.setPath("/");
			response.addCookie(cookie);
		} 
		else 
			allowAccess = false;
		
		
		log.info("allowAccess => {}", allowAccess);
		if(allowAccess) {
			if(null != modelAndView) {
				modelAndView.addObject("authInfoToken", vo);
				log.info("authInfoToken ==> {}", vo);
			}
			request.setAttribute("permission", permission);
		} 
		else {
			log.info("modelAndView.setViewName(\"redirect:/\");");
			modelAndView.setViewName("redirect:/");
		}
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
	}
	

	
}