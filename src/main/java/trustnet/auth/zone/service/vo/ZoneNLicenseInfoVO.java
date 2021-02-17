package trustnet.auth.zone.service.vo;

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
public class ZoneNLicenseInfoVO {
	int lit_zone_no;
	String lit_taa_ip;
	int lit_license_type;
	String zct_zone_name;
	String zct_zone_info;
	int zct_comp_no;
	int zct_revision_no;
	int zct_pl_license_cnt;
	int zct_tpl_license_cnt;
	int zct_session_time;
	String zct_limited_url;
	String zct_exist;
	String zct_hmac;
	String license_value;
}
