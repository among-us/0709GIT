package trustnet.auth.manager.controller.dto;

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
public class ManagerDBCheckDTO {
	@Pattern(regexp = "^[0-9a-zA-Z_]*$", message = "서비스명에 특수문자를 사용할 수 없습니다")
	@NotEmpty(message = "서비스명을 입력해주세요")
	String db_svc_name;
}
