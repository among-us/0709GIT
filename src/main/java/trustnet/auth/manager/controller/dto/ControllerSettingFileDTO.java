package trustnet.auth.manager.controller.dto;

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
public class ControllerSettingFileDTO {
	String ctrl_name;
	String local_ip;
	String db_svc_name;
	String db_type;
	int db_type_no;
	String db_addr;
	int db_port;
	String db_id;
	String db_pwd;
	String db_name;
}
