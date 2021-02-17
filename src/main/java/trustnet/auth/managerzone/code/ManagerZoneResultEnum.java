package trustnet.auth.managerzone.code;

public enum ManagerZoneResultEnum {
//SUCCESS -> 확인 버튼 누르면 .. CONTROLLER ZONE 등록 IP관리 매칭 목록으로 가고싶어
	SUCCESS(0, "등록에 성공하였습니다"),
	FAIL(1001, "등록에 실패하였습니다"),
	PARAMETEREXCEPTION(2001, "반드시 필요한 데이터가 전달되지 않았습니다"),
	BINDEXCEPTION(2002, "처리할 수 없는 데이터가 전달되었습니다");
	
	
	private int errorCode;
	private String errorMessage;
	
	ManagerZoneResultEnum(int errorCode, String errorMessage) {
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
