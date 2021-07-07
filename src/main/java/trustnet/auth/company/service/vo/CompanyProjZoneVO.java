package trustnet.auth.company.service.vo;

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
public class CompanyProjZoneVO {
	int proj_no;
	String proj_name;
	int zone_no;
	String zone_name;
	
	int pageNum;
	int show_cnt;
	
}
