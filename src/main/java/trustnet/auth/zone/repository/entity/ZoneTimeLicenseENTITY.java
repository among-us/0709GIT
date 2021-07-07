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
public class ZoneTimeLicenseENTITY {
	int zone_no;
	String zone_name;
	String allowed_ip;
	int allowed_cnt;
	String limited_period;
	String icv;
	
	
	
	int publish_cnt;
	int deny_cnt;
	
}
