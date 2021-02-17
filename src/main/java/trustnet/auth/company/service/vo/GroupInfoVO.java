package trustnet.auth.company.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoVO {
	int group_no;
	String group_name;
	String exist;
	String reg_date;
}
