package trustnet.auth.user.repository.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoENTITY {

	int user_no;
	String user_id;
	String passwd;
	//	String permissions;
	int perm_no;
	int comp_no;
	String proj_name;
	int proj_no;
	int zone_no;
	String zone_name;
	int auth_err;
	String reg_date;
	String user_name;
	String user_company_name;
	String user_dept_name;
	String user_email;
	String user_phone_num1;
	String user_phone_num2;
	String last_login;
	String exist;
	String hmac;

	String comp_name;
	int issuer_user_no;
	String issuer_user_id;
	boolean isLogin;
	String action;
	int user_perm_no;
	String user_perm_info;
	String history_reg_date;
	
	String recent_date;
}
