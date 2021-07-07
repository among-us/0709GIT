package trustnet.auth.user.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class UserInfoDTO {
	//	int uid;
	//	int authNumber;
	//	String id;
	//	String pw;
	//	String name;
	//	String phoneNumber;
	//	String company;

	int user_no;

	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "계정명에 특수문자를 사용할 수 없습니다")
	@Size(min = 4, max = 16, message = "계정명은 4글자 이상 16글자 이하만 사용 가능합니다")
	@NotBlank(message = "계정명을 입력해주세요")
	String user_id;
//	@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,16}$", message = "영문, 숫자, 특수문자 조합하여 비밀번호를 생성하세요")
	@Size(min = 8, max = 16, message = "패스워드는 8글자 이상 16글자 이하만 사용 가능합니다")
	@NotBlank(message = "패스워드를 입력해주세요")
	String passwd;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String repasswd;
	int perm_no;
	int comp_no;
	String proj_name;
	int proj_no;
	int zone_no;
	String zone_name;
	int auth_err;
	String user_name;
	String user_company_name;
	String user_dept_name;
	String user_email;
//	@Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$" ,message="test")
	String user_phone_num1;
	String user_phone_num2;
	String reg_date;
	String last_login;
	String exist;
	String hmac;

	String comp_name;
	int issuer_user_no;
	String issuer_user_id;
	boolean isLogin;
	String action;
	String history_reg_date;
	
	int user_perm_no;
	String user_perm_info;
	
	String recent_date;
}
