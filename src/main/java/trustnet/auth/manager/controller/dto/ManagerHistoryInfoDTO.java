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
public class ManagerHistoryInfoDTO {
	int tam_history_no;
	String tam_name;
	String site_info;
	String tam_local_ip;
	int tam_local_port;
	int tam_adm_port;
	String tam_pub_ip;
	int tam_pub_port;
	int tam_pub_adm_port;
	String comm_type;
	String watchdog;
	String exist;
	String issuer_user_id;
	String action;
	String history_reg_date;
	
	int tam_no;
	String tam_license;
	String shared_path;
	String tam_state;
	String hmac;
	int issuer_user_no;
	
	
	int pageNum;
	int show_cnt;
	String ordering;
	String filter;
	String column;
	
}
