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

	int user_no;
	String user_id;
	String passwd;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String repasswd;
	String permissions;
	int comp_no;
	int auth_err;
	String last_login;
	String reg_date;
	String exist;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String comp_name;
	String hmac;
	boolean isLogin;
	
}
