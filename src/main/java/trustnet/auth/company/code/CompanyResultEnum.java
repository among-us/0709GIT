package trustnet.auth.company.code;

public enum CompanyResultEnum {
	SUCCESS(0, "성공하였습니다"),
	FAIL(1001 , "실패하였습니다"),
	PARAMETEREMPTY(1002, "반드시 필요한 데이터가 전달되지 않았습니다"),
	BINDEXCEPTION(2001, "처리할 수 없는 데이터가 전달되었습니다"),
	EXISTSUCCESS(0, "사용할 수 있는 기업명 입니다"),
	EXISTFAIL(5002, "중복되는 이름이 존재합니다");
	
	
	
	private int errorCode;
	private String errorMessage;
	
	CompanyResultEnum(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage; 
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
