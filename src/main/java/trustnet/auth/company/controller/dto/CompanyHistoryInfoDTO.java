package trustnet.auth.company.controller.dto;

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
public class CompanyHistoryInfoDTO {
	int history_no;
	int comp_no;
	String comp_name;
	int issuer_user_no;
	String action;
	String reg_date;
	
	int pageNum;
	int show_cnt;
	String column;
	String ordering;
	String filter;
	String issuer_user_id;
}
