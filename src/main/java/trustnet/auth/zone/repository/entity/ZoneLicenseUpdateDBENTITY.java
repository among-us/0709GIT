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
public class ZoneLicenseUpdateDBENTITY {
	String zone_name;
	String revision_no;
//	int revision_no;
	String pl_license_cnt;
	String tpl_license_cnt;
	String limited_url;
	String integrity_value;
}
