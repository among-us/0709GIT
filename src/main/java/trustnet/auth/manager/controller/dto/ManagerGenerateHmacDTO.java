package trustnet.auth.manager.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagerGenerateHmacDTO {
	@Pattern(regexp = "^[0-9a-zA-Z_]*$", message = "TAM명은 영숫자로만 구성할 수 있습니다")
	@NotEmpty(message = "TAManager명을 입력해주세요")
	String tam_name;
	@NotEmpty(message = "로컬 IP 주소를 입력해주세요")
	String tam_local_ip;
	@Min(1)
	@Max(65535)
	int tam_local_port;
	@Min(1)
	@Max(65535)
	int tam_adm_port;
	String tam_license;
}
