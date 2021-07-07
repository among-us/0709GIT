package trustnet.auth.manager.service.vo;

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
public class ManagerCreateFileVO {

	String tam_name;
	String tam_ip; // ?
	String comm_type;
	String db_svc_name;
	String db_type;
	String db_addr; // db addr
	String db_ip;
	String db_port;
	String db_id;
	String db_pwd;
	String db_name;
	String hmac;
	
	int db_type_no;
}
