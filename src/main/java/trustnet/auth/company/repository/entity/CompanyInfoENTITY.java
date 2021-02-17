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
	int group_no;
	String exist;
	String reg_date;
}
