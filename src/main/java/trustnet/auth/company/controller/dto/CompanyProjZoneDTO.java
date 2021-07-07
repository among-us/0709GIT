package trustnet.auth.company.controller.dto;

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
public class CompanyProjZoneDTO {
	int proj_no;
	String proj_name;
	int zone_no;
	String zone_name;
	
	int pageNum;
	int show_cnt;
	
}
