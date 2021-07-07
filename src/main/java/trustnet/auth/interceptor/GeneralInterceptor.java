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
import trustnet.auth.user.service.UserService;
import trustnet.auth.user.service.vo.UserInfoVO;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeneralInterceptor implements HandlerInterceptor {

	private final SecurityService securityService;
	private final PermissionObj permissionObj;
	private final ModelMapper modelMapper;
	private final UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		SecurityAuthInfoVO vo = securityService.checkTokenValidation(request);

		boolean useble = vo.isUsable();
		boolean allowAccess = true;
		String permission = "";

		try {

			int user_no = vo.getUser_no();
			UserInfoVO queryVO = userService.getUserInfo(user_no);
			int query_perm_no = queryVO.getPerm_no();
			vo.setPerm_no(query_perm_no);

			if (useble) {
				permission = vo.getPermissions();
				int perm_no = vo.getPerm_no();
				String perm_number = Integer.toString(perm_no);
				allowAccess = permissionObj.allowAdminPage(perm_number, request);
				//			allowAccess = permissionObj.allowAdminPage(permission, request);
				SecurityAuthInfoVO retAuthVo = securityService.createToken(vo);
				SecurityAuthInfoDTO retAuthDTO = modelMapper.map(retAuthVo,
						SecurityAuthInfoDTO.class);
				Cookie cookie = new Cookie("UNETAUTHTOKEN", retAuthDTO.getValue());
				cookie.setPath("/");
				response.addCookie(cookie);
			} else {
				allowAccess = false;
			} // first if else

			if (allowAccess) {
				if (null != modelAndView) {
					modelAndView.addObject("authInfoToken", vo);
				}
				request.setAttribute("permission", permission);
			} else {
				log.info("modelAndView.setViewName(\"redirect:/permission/error\");");
				log.info("user number is = " + vo.getUser_no()
						+ " >>>> no permission exception session out !!");

				if (modelAndView == null) {
				} else {
					modelAndView.setViewName("redirect:/permission/error");
				}

			} // second if else 

		} catch (Exception e) {
			log.warn(e.toString());
			modelAndView.setViewName("redirect:/permission/error");
		}

	}// postHandle End

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object object, Exception arg3) throws Exception {
	}

}