package trustnet.auth.manager.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
public class ControllerInfoDTO {
	int ctrl_no;
	@Pattern(regexp = "^[0-9a-zA-Z_]*$", message = "Controller명은 영숫자로만 구성할 수 있습니다")
	@NotEmpty(message = "TA-Controller명을 입력해주세요")
	String ctrl_name;
	String local_ip;
	@Min(value = 0, message = "올바른 내부 포트 범위를 입력해주세요")
	@Max(value = 65535, message = "올바른 내부 포트 범위를 입력해주세요")
	int local_port;
	@Min(value = 0, message = "올바른 관리자 포트 범위를 입력해주세요")
	@Max(value = 65535, message = "올바른 관리자 포트 범위를 입력해주세요")
	int admin_port;
	String license;
	String watchdog;
	String proc_state;
	String enable;
	String icv;
}
