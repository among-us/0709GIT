package trustnet.auth.zone.repository.entity;

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
public class ZoneVerifyIntegrityENTITY {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String zone_name;
	int revision_no;
	int pl_count;
	int tpl_count;
	String limit_url;
	String integrity;
	
}
