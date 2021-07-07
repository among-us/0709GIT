package trustnet.auth.zone.controller.dto;

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
public class ZoneCheckInfoDTO {
	@Pattern(regexp = "^[a-zA-Z0-9_]{1,20}$", message = "PROJECT명은 영숫자로만 구성할 수 있습니다")
//	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "ZONE명은 영숫자로만 구성할 수 있습니다")
	@NotEmpty(message = "PROJECT명을 입력해주세요")
	String zone_name;
}



