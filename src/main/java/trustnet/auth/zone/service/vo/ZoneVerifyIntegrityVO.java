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

public class ZoneVerifyIntegrityVO {
 
	private String zone_name;
	private int revision_no;
	private int pl_count;
	private int tpl_count;
	private String limit_url;
	private String integrity;
	
}

