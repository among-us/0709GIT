package trustnet.auth.company.repository.entity;

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
public class CompanyProjectHistoryENTITY {
	int history_no;
	String comp_name;
	String zone_info;
	String exist;
	String proj_name;
	String reg_date;

	int comp_no;
	int zone_no;
	int issuer_user_no;
	String issuer_user_id;
	String action;

	int pageNum;
	int show_cnt;
	String ordering;
	String column;
	String filter;
}
