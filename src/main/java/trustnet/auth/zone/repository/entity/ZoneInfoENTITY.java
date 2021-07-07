package trustnet.auth.zone.repository.entity;

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
public class ZoneInfoENTITY {

//	String zoneName; 
//	String zoneInfo;
//	int revisionNumber;
//	String regDate;
//	int plLicenseCnt;
//	int tplLicenseCnt;
//	int sessionTime;
//	String limitedUrl;
//	String hMac;

	int zone_no;
	String zone_name;
	String zone_info;
	int comp_no;
	@JsonInclude(JsonInclude.Include.NON_NULL)
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
	boolean isEnrolled;
	
	int pub_static;
	int pub_dynamic;
	int pub_timelimit;
}
