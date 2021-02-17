package trustnet.auth.company.controller.dto;

import javax.validation.constraints.NotBlank;
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

	@Pattern(regexp = "[a-zA-Z가-횧]", message = "회사명에 특수문자를 사용할 수 없습니다")
	@NotBlank(message = "회사명을 입력해주세요")
	String comp_name;
	int group_no;
	String exist;
	String reg_date;
	
}
 