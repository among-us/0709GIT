package trustnet.auth.manager.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

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
public class ManagerDBInfoDTO {
	int db_no;
	String db_svc_name;
	String tam_name;
	int db_type_no;
//	@Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", message = "올바른 로컬 IPv4 주소를 입력해주세요")
	String db_priv_ip;
	@Min(1)
	@Max(65535)
	int db_priv_port;
//	@Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", message = "올바른 로컬 IPv4 주소를 입력해주세요")
	String db_pub_ip;
	@Min(1)
	@Max(65535)
	int db_pub_port;
	String db_id;
	String db_passwd;
	String db_name;
}

