package trustnet.auth.company.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyLicenseVO {
	String comp_name;
	String zone_name;
	String zone_info;
	int pl_license_cnt;
	int tpl_license_cnt;
	int PL_SUM;
	int TPL_SUM;
	int TTL_SUM;
	int RC_SUM;
	int NA_SUM;
}
