package trustnet.auth.zone.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import trustnet.auth.zone.code.ZoneResultEnum;

@Data
public class ZoneInfoResponseDTO {
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	int errorCode;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorMessage;
	
	public ZoneInfoResponseDTO(ZoneResultEnum retEnum){
		this.errorCode = retEnum.getErrorCode();
		this.errorMessage = retEnum.getErrorMessage();
	}
	
}
