package trustnet.auth.zone.repository.entity;

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
public class ZoneLicenseHistoryInfoENTITY {
	int zone_no;
	String taa_ip;
	int license_type;
	int issuer_user_no;
	String action;
	String history_reg_date;
	String issuer_user_id;
	
}
