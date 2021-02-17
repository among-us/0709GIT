package trustnet.auth.company.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import trustnet.auth.company.code.CompanyResultEnum;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyResponseInfoDTO {
	int errorCode;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorMessage;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<CompanyLicenseDTO> companyLicenseList;
	
	public CompanyResponseInfoDTO(CompanyResultEnum err) {
		this.errorCode = err.getErrorCode();
		this.errorMessage = err.getErrorMessage();
	}
	
	public CompanyResponseInfoDTO(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public CompanyResponseInfoDTO(List<CompanyLicenseDTO> companyLicenseList) {
		this.errorCode = 0;
		this.companyLicenseList = companyLicenseList;
	}
}
