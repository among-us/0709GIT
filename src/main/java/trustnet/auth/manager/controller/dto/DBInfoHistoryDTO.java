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
public class DBInfoHistoryDTO {
	int history_no;
	int db_no;
	String db_svc_name;
	int db_type_no;
	String db_priv_ip;
	int db_priv_port;
	String db_pub_ip;
	int db_pub_port;
	String db_id;
	String db_passwd;
	String db_name;
	String reg_date;
	String action;
	int issuer_user_no;

	String issuer_user_id;

	int pageNum;
	int show_cnt;
	String ordering;
	String filter;
	String column;

}
