package trustnet.auth.user.service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserHistoryInfoVO {
	int user_history_no;
	int user_no;
	String user_id;
	String passwd;
	int perm_no;
	int comp_no;
	int proj_no;
	int zone_no;
	String zone_name;
	int auth_err;
	String user_name;
	String user_company_name;
	String user_dept_name;
	String user_email;
	String user_phone_num1;
	String user_phone_num2;
	String reg_date;
	String last_login;
	String exist;
	String hmac;
	int issuer_user_no;
	String action;
	String history_reg_date;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String comp_name;
	String issuer_user_id;
	
	
	String user_perm_info;
	
}
