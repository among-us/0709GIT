package trustnet.auth.user.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class UserCheckInfoDTO {
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "계정명에 한글 또는 특수문자를 사용할 수 없습니다")
	@Size(min = 4, max = 16, message = "계정명은 4글자 이상 16글자 이하만 입력 가능합니다")
	@NotBlank(message = "계정명을 입력해주세요")
	String user_id;
}
