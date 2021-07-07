package trustnet.auth.company.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyNGroupInfoENTITY {

	int ci_comp_no;
	String ci_comp_name;
	String ci_reg_date;
	String ci_exist;
	
	int gi_group_no;
	String gi_group_name;
	String gi_reg_date;
	
	String exist;
}
