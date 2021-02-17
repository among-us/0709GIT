package trustnet.auth.user.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import trustnet.auth.user.code.UserResultEnum;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserInfoResponseDTO {
	int errorCode;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorMessage;
	
	public UserInfoResponseDTO(UserResultEnum retEnum) {
		this.errorCode = retEnum.getErrorCode();
		this.errorMessage = retEnum.getErrorMessage();
	}
	
	public UserInfoResponseDTO(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
