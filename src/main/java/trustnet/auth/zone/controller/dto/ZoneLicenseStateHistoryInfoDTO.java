package trustnet.auth.zone.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class ZoneLicenseStateHistoryInfoDTO {
	int state_no;
	int tam_no;
	String tam_local_ip;
	int taa_no;
	String taa_ip;
	String taa_hostname;
	int zone_no;
	String zone_name;
	int license_type;
	int req_type;
	String req_date;
	String req_result;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String result_reason;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String tam_name;
}
