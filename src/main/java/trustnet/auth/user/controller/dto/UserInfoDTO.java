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
	@Size(min = 8, max = 16, message = "패스워드는 8글자 이상 16글자 이하만 사용 가능합니다")
	@NotBlank(message = "패스워드를 입력해주세요")
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
