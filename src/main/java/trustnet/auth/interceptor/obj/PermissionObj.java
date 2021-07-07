package trustnet.auth.interceptor.obj;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class PermissionObj {
	// 관리자 권한 페이지 
	private final List<String> adminAllowPage;
	
	public PermissionObj() {
		adminAllowPage = new ArrayList<String>();
		/*
		adminAllowPage.add("/company/historyPage");
		adminAllowPage.add("/user/listPage");
		adminAllowPage.add("/user/historyPage");
		adminAllowPage.add("/manager/enrollPage");
		adminAllowPage.add("/zone/enrollPage");
		adminAllowPage.add("/zoneLicense/enrollPage");
		adminAllowPage.add("/managerZone/listPage");
		adminAllowPage.add("/company/enrollPage");
		*/
	}
	
	public List<String> getAdminAllowPage() {
		return adminAllowPage;
	}
	
	public boolean allowAdminPage(String perm_number, HttpServletRequest request) {
		String requestServletPath = request.getServletPath();

		boolean result = true;
		for(String page : adminAllowPage) {
			if(page.equalsIgnoreCase(requestServletPath) && !perm_number.equalsIgnoreCase("1")) {
				result = false;
				break;
			}
		}
		return result;
	}
	
//	public boolean allowAdminPage(String permission, HttpServletRequest request) {
//		String requestServletPath = request.getServletPath();
//		
//		boolean result = true;
//		for(String page : adminAllowPage) {
//			if(page.equalsIgnoreCase(requestServletPath) && !permission.equalsIgnoreCase("admin")) {
//				result = false;
//				break;
//			}
//		}
//		
//		return result;
//	}
}
