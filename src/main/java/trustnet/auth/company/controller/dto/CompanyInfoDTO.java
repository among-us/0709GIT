package trustnet.auth.company.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
public class CompanyInfoDTO {
	
	int comp_no;
	@NotEmpty(message = "GROUP명을 입력해주세요")
	String comp_name;
	String asis_comp_name;
	String exist;
	String reg_date;
//	int group_no;
	String issuer_user_id;
}
 