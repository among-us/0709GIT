package trustnet.auth.company.controller.dto;

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
public class CompanyLicenseDTO {
	String comp_name;
	String zone_name;
	String proj_name;
	String zone_info;
	int pl_license_cnt;
	int tpl_license_cnt;
	int PL_SUM;
	int TPL_SUM;
	int TTL_SUM;
	//	int RC_SUM;
	int NA_SUM;
	int allowed_cnt;
	int tm_pub_cnt;

	int zone_no;
	int revision_no;
	String limited_url;
}
