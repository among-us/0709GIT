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
public class ControllerInfoVO {
	int ctrl_no;
	String ctrl_name;
	String local_ip;
	int local_port;
	int admin_port;
	String license;
	String watchdog;
	String proc_state;
	String enable;
	String icv;
	
}
