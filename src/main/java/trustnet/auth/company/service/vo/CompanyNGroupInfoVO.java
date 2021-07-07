package trustnet.auth.company.service.vo;

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
public class CompanyNGroupInfoVO {
	int ci_comp_no;
	String ci_comp_name;
	String ci_reg_date;
	int gi_group_no;
	String gi_group_name;
	String gi_reg_date;
	
	String exist;
}
