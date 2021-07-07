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
public class ManagerInfoVO {
	
	int tam_no;
	String tam_name;	
	String site_info;
	String tam_local_ip;
	int tam_local_port;
	int tam_adm_port;
	
	String tam_pub_ip;
	int tam_pub_port;
	int tam_pub_adm_port;
	
	String tam_license;
	String shared_path;
	String comm_type;
	String watchdog;
	String tam_state;
	String exist;
	String hmac;
	
	
//	datatables serverside 처리하기위한 객체
	
	int show_cnt;
	int nPerPage;
	

}
