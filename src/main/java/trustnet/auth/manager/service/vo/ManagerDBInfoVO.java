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
public class ManagerDBInfoVO {
	int db_no;
	String db_svc_name;
	String tam_name;
	int db_type_no;
	String db_priv_ip;
	int db_priv_port;
	String db_pub_ip;
	int db_pub_port;
	String db_id;
	String db_passwd;
	String db_name;
}
