package trustnet.auth.user.code;



public enum UserResultEnum {
	SUCCESS(0, "성공하였습니다"),
	FAIL(1001, "실패하였습니다"),
	SAVEERROR(1001, "등록에 실패하였습니다"),
	LOGINERROR(1002, "로그인에 실패하였습니다"),
	PARAMETEREMPTY(2001, "반드시 필요한 데이터가 전달되지 않았습니다"),
	BINDEXCEPTION(2002, "서버에서 처리할 수 있는 데이터 형태가 아닙니다"),
	PARAMETERLENGTHEXCEPTION(2003, "아이디는 4글자이상, 패스워드는 8글자 이상이여야 합니다"),
	TOKENVALIDATION(2004, "토큰이 만료되어 로그인 페이지로 이동합니다"),
	PASSWORDLENGTEH(2005, "패스워드는 8글자 이상이여야 합니다"),
	REPASSWORDCHECK(2006, "입력한 두 패스워드가 다릅니다"),
	EXISTSUCCESS(0, "사용할 수 있는 ID 입니다"),
	EXISTFAIL(5001, "사용할 수 없는 ID 입니다");
	
	private int errorCode;
	private String errorMessage;
	
	UserResultEnum(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage; 
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public boolean isSuccess() {
		return this == SUCCESS;
	}
	
}
