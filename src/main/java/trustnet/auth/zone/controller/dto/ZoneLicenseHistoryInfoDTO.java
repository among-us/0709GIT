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
public class ZoneLicenseHistoryInfoDTO {

	int license_history_no;
	int zone_no;
	String zone_name;
	String taa_ip;
	int license_type;
	int issuer_user_no;
	String action;
	String history_reg_date;
	String issuer_user_id;

	int show_cnt;
	int pageNum;
	String column;
	String ordering;
	String filter;

}
