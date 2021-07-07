package trustnet.auth.company.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompProjZoneListDTO {
	int comp_no;
	int proj_no;
	int zone_no;
	String reg_date;
	
	
	String comp_name;
	String proj_name;
	String zone_name;
	
}
