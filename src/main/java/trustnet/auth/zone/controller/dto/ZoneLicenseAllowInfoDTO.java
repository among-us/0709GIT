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
public class ZoneLicenseAllowInfoDTO {
	/*
	 * String zone_name; int revision_no; int
	 * pl_license_cnt; int tpl_license_cnt; String
	 * limited_url; String integrity_value;
	 */
	int zone_no;
	String zone_name;
	String zone_info;
	int pl_license_cnt;
	int Pub_static;
	int tpl_license_cnt;
	int Pub_dynamic;
	int allowed_cnt;
	int Pub_timelimit;
	String limited_period;
	int session_time;
	String exist;
	int revision_no;
	String limited_url;
	
	int pageNum;
	int show_cnt;
	String ordering;
	String filter;
	String column;
	
}
