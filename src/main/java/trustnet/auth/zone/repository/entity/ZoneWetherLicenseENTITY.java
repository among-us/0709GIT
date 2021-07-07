package trustnet.auth.zone.repository.entity;

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
public class ZoneWetherLicenseENTITY {
	int zone_no;
	String pl_license_cnt;
	String tpl_license_cnt;

	String pub_pl_cnt;
	String pub_tpl_cnt;

	String deny_cnt;
	String deny_tpl_cnt;
	
	String taa_ip;
	String taa_hostname;
	String registed_date;
	String allow_state;
	
	//	String zone_name;
	//	String zone_info;
	//	String pl_cnt;
	//	String allow_pl;
	//	String deny_pl;
	//	String tpl_cnt;
	//	String allow_tpl;
	//	String deny_tpl;
	//	String allow_na;
	//	String deny_na;
}
