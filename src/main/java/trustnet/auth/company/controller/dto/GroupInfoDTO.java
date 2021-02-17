package trustnet.auth.company.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoDTO {
	int group_no;
	String group_name;
	String exist;
	String reg_date;
}
