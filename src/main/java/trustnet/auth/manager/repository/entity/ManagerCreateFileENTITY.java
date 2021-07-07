package trustnet.auth.manager.repository.entity;

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
public class ManagerCreateFileENTITY {
	
	String tam_name;
	String tam_local_ip; // ?
	String comm_typSe;
	String db_svc_name;
	String db_type;
	String db_addr; // db addr
	int db_port;
	String db_id;
	String db_pwd;
	String db_name;
	
	String hmac;
	
	int db_type_no;
	
}
