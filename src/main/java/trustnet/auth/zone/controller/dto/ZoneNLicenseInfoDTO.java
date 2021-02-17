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
public class ZoneNLicenseInfoDTO {
	int lit_zone_no;
	String lit_taa_ip;
	int lit_license_type;
	String zct_zone_name;
	String zct_zone_info;
	String zct_exist;
	String license_value;
}
