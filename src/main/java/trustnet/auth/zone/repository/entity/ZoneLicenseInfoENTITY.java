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
public class ZoneLicenseInfoENTITY {
	int zone_no;
//	String zone_name;
	String taa_ip;
	String license_value;
	int license_type;
}
