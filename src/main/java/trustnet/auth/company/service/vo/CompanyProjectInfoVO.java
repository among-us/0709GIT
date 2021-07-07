package trustnet.auth.company.service.vo;

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
public class CompanyProjectInfoVO {
	int comp_no;
	String comp_name;
	int zone_no;
	String zone_name;
	String exist;
	String reg_date;
	
	
	int proj_no;
	String proj_name;
	String TOBE_proj_name;
	int pageNum;
	int show_cnt;
	String ordering;
	String filter;
}
