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
public class ZoneLicenseInfoDTO {

	int zone_no;
//	String zone_name;
	String taa_ip;
	int license_type;
//	String license_value;
	
}
