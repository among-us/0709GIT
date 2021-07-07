package trustnet.auth.user.controller.dto;

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
public class MainDashboardCountDTO {
	int manager_cnt;
	int agent_cnt;
	int group_cnt;
	int project_cnt;
}
