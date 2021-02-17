package trustnet.auth.zone.controller.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import trustnet.auth.common.dto.CommonResponseDTO.CommonResponseDTOBuilder;
import trustnet.auth.zone.code.ZoneResultEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ZoneVerifyIntegrityDTO {
	
	String zone_name;
	int revision_no;
	int pl_count;
	int tpl_count;
	String limit_url;
	String integrity;
	
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static CommonResponseDTOBuilder<Object> builder() {
		return null;
	}
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	int errorCode;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorMessage;
	
	public ZoneVerifyIntegrityDTO(ZoneResultEnum retEnum){
		this.errorCode = retEnum.getErrorCode();
		this.errorMessage = retEnum.getErrorMessage();
	}
	
}
