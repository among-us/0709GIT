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
public class ControllerSettingFileENTITY {
	String ctrl_name;
	String local_ip;
	String db_svc_name;
	int db_type_no;
	String db_addr;
	int db_port;
	String db_id;
	String db_pwd;
	String db_name;
}
