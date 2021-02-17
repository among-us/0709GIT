package trustnet.auth.manager.controller.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
public class ManagerInfoDTO {

//	int tam_no;
//	String tam_name;
//	String site_info;
//	String tam_local_ip;
//	int tam_local_port;
////	String tam_pub_ip;
////	int tam_pub_port;
//	String tam_license;
//	String shared_path;
//	String log_path;
//	String log_level;
//	String comm_type;
////	String hmac;
	
	int tam_no;
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "TAM명은 영숫자로만 구성할 수 있습니다")
	@NotEmpty(message = "TAM명을 입력해주세요")
	String tam_name;
	String site_info;
	@NotEmpty(message = "로컬 IP 주소를 입력해주세요")
	String tam_local_ip;
	@Min(1)
	@Max(65535)
	int tam_local_port;
	@Min(1)
	@Max(65535)
	int tam_admin_port;
	String tam_license;
//	@NotEmpty(message = "공유 디렉토리 경로를 입력해주세요")
	String shared_path;
	@NotEmpty(message = "로그 디렉토리 경로를 입력해주세요")
	String log_path;
	@NotEmpty(message = "로그 레벨을 입력해주세요")
	String log_level;
	@NotEmpty(message = "타입을 입력해주세요")
	String comm_type;
	@NotEmpty(message = "Watchdog 사용여부를 입력해주세요")
	String watchdog;
	String tam_state;
	String exist;
	String hmac;
	
}
