package trustnet.auth.user.service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class UserInfoVO {
//	int uid;
//	int authNumber;
//	String id;
//	String pw;
//	String name;
//	String phoneNumber;
//	String company;
//	boolean isLogin;

	String user_id;
	String passwd;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String repasswd;
//	String permissions;
	
	int comp_no;
	String comp_name;
	int user_no;
	int perm_no;
	int zone_no;
	String zone_name;
	
	
	String proj_name;
	int proj_no;
	int auth_err;
	String reg_date;
	String user_company_name;
	String user_name;
	String user_dept_name;
	String user_email;
	String user_phone_num1;
	String user_phone_num2;
	String last_login;
	String exist;
	String hmac;
	
	int issuer_user_no;
	String issuer_user_id;
	String action;
	boolean isLogin;	
	String history_reg_date;
	int user_perm_no;
	String user_perm_info;
	
	String recent_date;
}
