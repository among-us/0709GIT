package trustnet.auth.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponseDTO<T>{
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private T data;
}
