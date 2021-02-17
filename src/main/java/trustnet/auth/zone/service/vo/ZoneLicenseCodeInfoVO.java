package trustnet.auth.zone.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ZoneLicenseCodeInfoVO {

	int license_type;
	String license_value;
}
