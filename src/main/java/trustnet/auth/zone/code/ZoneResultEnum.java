package trustnet.auth.zone.code;

public enum ZoneResultEnum {
	SUCCESS(0, "성공하였습니다"),
	FAIL(1000, "실패하였습니다"),
	SAVEERROR(1001, "등록에 실패하였습니다"),
	UPDATEERROR(1002, "변경에 실패하였습니다"),
	DELETEERROR(1003, "삭제에 실패하였습니다"),
	PARAMETEREXCEPTION(2001, "반드시 필요한 데이터가 전달되지 않았습니다"),
	BINDEXCEPTION(2002, "처리할 수 없는 데이터가 전달되었습니다"),
	SQL_INTEGRITY_CONSTRAINT_VIOLATION(3001, "중복된 정보를 가진 정보가 존재합니다"), // ZONE 라이선스 관리 -> ZONE 라이선스 등록 -> ALERT 창 내용 변경 // 이름을 -> 정보를
	EXISTSUCCESS(0, "사용할 수 있는 이름입니다"),
	EXISTFAIL(5002, "중복되는 PROJECT명이 존재합니다"),
	JNI_ERROR(-1,"JNI EXCEPTION ERROR");

	private int errorCode;
	private String errorMessage;
	
	ZoneResultEnum(int errorCode, String errorMessage) {
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
