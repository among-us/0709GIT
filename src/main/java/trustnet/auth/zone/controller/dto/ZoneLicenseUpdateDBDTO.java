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
public class ZoneLicenseUpdateDBDTO {
	String zone_name;
//	int revision_no;
	String revision_no;
	String pl_license_cnt;
	String tpl_license_cnt;
	String limited_url;
	String integrity_value;
	
}
