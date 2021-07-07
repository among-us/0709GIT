package trustnet.auth.manager.controller.dto;

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
//	정규식에 _ UNDERBAR 추가 
	@Pattern(regexp = "^[0-9a-zA-Z_]*$", message = "TAM명은 영숫자로만 구성할 수 있습니다")
	@NotEmpty(message = "TAManager명을 입력해주세요")
	String tam_name;
	String site_info;
//	@Pattern(regexp = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$" , message = "올바른 로컬 IPv4 주소를 입력해주세요")
	String tam_local_ip;
	@Min(0) 	// 0번부터 아닌가 ? ? ? ? ? ? ? ? ? ? ? ? ? ? ? ?
	@Max(value = 65535, message = "내부 포트 범위를 확인해주세요")
	int tam_local_port;
	@Min(0)
	@Max(value = 65535, message = "관리자 내부 포트 범위를 확인해주세요")
//	@Pattern(regexp = "^[0-9]*$" , message = "숫자만 허용")
	int tam_adm_port;
	String tam_pub_ip;
	String tam_pub_port;
	String tam_pub_adm_port;
//	int tam_pub_adm_port;
	String tam_license;
//	@NotEmpty(message = "공유 디렉토리 경로를 입력해주세요")
	String shared_path;
//	@NotEmpty(message = "로그 디렉토리 경로를 입력해주세요")
//	String log_path;
//	@NotEmpty(message = "로그 레벨을 입력해주세요")
//	String log_level;
//	@NotEmpty(message = "타입을 입력해주세요")
	String comm_type;
//	@NotEmpty(message = "Watchdog 사용여부를 입력해주세요")
	String watchdog;
	String tam_state;
	String exist;
	String hmac;
	
	
	
	int show_cnt;
	int nPerPage;
}
