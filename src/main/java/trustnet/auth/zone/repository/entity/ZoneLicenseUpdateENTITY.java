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
public class ZoneLicenseUpdateENTITY {
	int zone_no;
	String asis_taa_ip;
	int asis_license_type;
	String taa_ip;
	int license_type;
}
