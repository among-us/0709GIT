package trustnet.auth.zone.controller.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
public class ZoneInfoDTO {

//	String zoneName; 
//	String zoneInfo;
//	int revisionNumber;
//	String regDate;
//	int plLicenseCnt;
//	int tplLicenseCnt;
//	int sessionTime;
//	String limitedUrl;
//	String hMac;

//	String zone_name;
//	String zone_info;
//	int pl_license_cnt;
//	int tpl_license_cnt;
//	int session_time;
//	String limited_url;

	int zone_no;
	@Pattern(regexp = "^[a-zA-Z0-9_]{1,20}$", message = "PROJECT명은 영숫자로만 구성할 수 있습니다")
	//TEST	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "ZONE명은 영숫자로만 구성할 수 있습니다")
	@NotEmpty(message = "PROJECT명을 입력해주세요")
	String zone_name;
	String zone_info;
	
	int comp_no;		// 삭제됐음 
	String comp_name;
	
	int revision_no;
	Timestamp registed_date;
	int pl_license_cnt;
	int tpl_license_cnt;
	String session_time;
	String limited_url;
	String exist;
	String integrity_value;
	String history_reg_date;
	
	String limited_period;
	int allowed_cnt;
	boolean isEnrolled;
	
	int pub_static;
	int pub_dynamic;
	int pub_timelimit;

}
