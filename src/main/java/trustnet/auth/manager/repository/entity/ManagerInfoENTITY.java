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
public class ManagerInfoENTITY {

//
//	String serverName;
//	String siteInfo;
//	String serverIp;
//	int serverPort;
//	String serverLicense;
//	String sharedPath;
//	String logPath;
//	String logLevel;
//	String commType;
//	String hMac;
//

	int tam_no;
	String tam_name;
	String site_info;
	String tam_local_ip;
	int tam_local_port;
	String tam_pub_ip;
	int tam_pub_port;
	int tam_admin_port;
	String tam_license;
	String shared_path;
	String log_path;
	String log_level;
	String comm_type;
	String watchdog;
	String tam_state;
	String exist;
	String hmac;
	
	
}
