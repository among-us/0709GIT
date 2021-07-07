package trustnet.auth.zone.controller.dto;

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
	int asis_revision_no;
	int asis_pl_count;
	int asis_tpl_count;
	String asis_limited_url;
	
	int tobe_revision_no;
	int tobe_pl_count;
	int tobe_tpl_count;
	String tobe_limited_url;
	
	String integrity;
	String value;
	
}
