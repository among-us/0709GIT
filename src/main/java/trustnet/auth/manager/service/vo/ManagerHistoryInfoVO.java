package trustnet.auth.manager.service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class ManagerHistoryInfoVO {
	int tam_history_no;
	int tam_no;
	String tam_name;
	String site_info;
	String tam_local_ip;
	int tam_local_port;
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
	int issuer_user_no;
	String action;
	String history_reg_date;
	String issuer_user_id;
}
