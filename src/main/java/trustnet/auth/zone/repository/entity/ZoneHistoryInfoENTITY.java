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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ZoneHistoryInfoENTITY {
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
	int issuer_user_no;
	String action;
	String history_reg_date;
	String issuer_user_id;
}
