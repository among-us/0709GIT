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
public class CompanyInfoVO {
	
	int comp_no;
	String comp_name;
	String asis_comp_name;
	String exist;
	String reg_date;
	String issuer_user_id;
}
