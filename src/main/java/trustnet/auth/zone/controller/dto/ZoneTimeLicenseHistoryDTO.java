package trustnet.auth.zone.controller.dto;

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
public class ZoneTimeLicenseHistoryDTO {

//	String zone_name;
	int history_no;
	int zone_no;
	String zone_name;
	String allowed_ip;
	int allowed_cnt;
	String limited_period;
	int issuer_user_no;
	String action;
	String history_reg_date;
	
	int pageNum;
	int show_cnt;
	String ordering;
	String filter;
	String column;
}	
