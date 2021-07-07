package trustnet.auth.company.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInfoENTITY {
	int comp_no;
	String comp_name;
	String asis_comp_name;
	String exist;
	String reg_date;
	String issuer_user_id;
//	int group_no;
}
