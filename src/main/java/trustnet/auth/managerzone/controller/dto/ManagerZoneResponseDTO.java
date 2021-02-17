package trustnet.auth.managerzone.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import trustnet.auth.managerzone.code.ManagerZoneResultEnum;


@NoArgsConstructor
@Getter
@Setter
public class ManagerZoneResponseDTO {
	int errorCode;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorMessage;
	

	public ManagerZoneResponseDTO(ManagerZoneResultEnum err) {
		this.errorCode = err.getErrorCode();
		this.errorMessage = err.getErrorMessage();
		// TODO Auto-generated constructor stub
	}
}
