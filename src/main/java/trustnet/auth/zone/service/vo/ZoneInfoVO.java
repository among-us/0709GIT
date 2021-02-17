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

//	String zoneName; 
//	String zoneInfo;
//	int revisionNumber;
//	String regDate;
//	int plLicenseCnt;
//	int tplLicenseCnt;
//	int sessionTime;
//	String limitedUrl;
//	String hMac;
//	


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
	int session_time;
	String limited_url;
	String exist;
	String integrity_value;
	

	public ZoneInfoVO(int comp_no) {
		this.comp_no = comp_no;
	}
}

