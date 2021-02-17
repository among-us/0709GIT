package trustnet.auth.log.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogInfoDTO {
	int log_no;
	int user_no;
	String reg_date;
	String description;
}
