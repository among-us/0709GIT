package trustnet.auth.manager.code;

public enum ManagerResultEnum {
	SUCCESS(0, "성공하였습니다"),
	DELETEERROR(1000, "삭제에 실패하였습니다"),
	SAVEERROR(1001, "등록에 실패하였습니다"),
	EXISTMANAGER(1002, "해당 이름의 서버가 이미 존재합니다"),
	NOEXISTMANAGER(0, "사용하실 수 있는 서버명 입니다"),
	PARAMETEREMPTY(2001, "반드시 필요한 데이터가 전달되지 않았습니다"),
	BINDEXCEPTION(2002, "서버에서 처리할 수 있는 데이터 형태가 아닙니다"),
	TOKENVALIDATION(2004, "토큰이 만료되어 로그인 페이지로 이동합니다"),
	SQL_INTEGRITY_CONSTRAINT_VIOLATION(3001, "데이터베이스를 사용하는데 문제가 발생하였습니다 \n관리자에게 문의바랍니다");
	
	private int errorCode;
	private String errorMessage;
	
	ManagerResultEnum(int errorCode, String errorMessage) {
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
