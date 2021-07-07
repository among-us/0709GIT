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
public class ManagerCreateFileDTO {
	int db_no;
	String tam_name;
	String tam_ip; // ?
	String comm_type;
	String db_svc_name;
	String db_type;
	@Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$" , message = "올바른 IPv4 주소를 입력해주세요")
	String db_addr; // db addr
	String db_ip;
	@Min(1)
	@Max(65535)
	String db_port;
	String db_id;
	String db_pwd;
	String db_name;
	
	String hmac;
	
	int db_type_no;
	
}
