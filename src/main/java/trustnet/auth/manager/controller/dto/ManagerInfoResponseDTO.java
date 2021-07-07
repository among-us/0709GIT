package trustnet.auth.manager.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import trustnet.auth.manager.code.ManagerResultEnum;

@NoArgsConstructor
@Getter
@Setter
public class ManagerInfoResponseDTO {

	int errorCode;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorMessage;
	

	public ManagerInfoResponseDTO(ManagerResultEnum err) {
		this.errorCode = err.getErrorCode();
		this.errorMessage = err.getErrorMessage();
		// TODO Auto-generated constructor stub
	}
	
	public ManagerInfoResponseDTO(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		// TODO Auto-generated constructor stub
	}
}
