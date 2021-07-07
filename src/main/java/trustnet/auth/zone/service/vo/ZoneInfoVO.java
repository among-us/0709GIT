package trustnet.auth.zone.service.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ZoneInfoVO {

	int zone_no;
	String zone_name;
	String zone_info;
	int comp_no;
	String comp_name;
	int revision_no;
	Timestamp registed_date;
	int pl_license_cnt;
	int tpl_license_cnt;
	int allowed_cnt;
	String limited_period;
	int session_time;
	String limited_url;
	String exist;
	String integrity_value;
	String history_reg_date;
	
	int pub_static;
	int pub_dynamic;
	int pub_timelimit;

}

