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
