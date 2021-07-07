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
public class CompanyHistoryInfoVO {
	int history_no;
	int comp_no;
	String comp_name;
	int issuer_user_no;
	String action;
	String reg_date;
	
	int pageNum;
	int show_cnt;
	String column;
	String filter;
	String ordering;
	String issuer_user_id;
}
