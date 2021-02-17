/**
 * 
 */

class SessionInfo {
	constructor(callBack) {
		$.ajax({
			url:'/user/session',
			type:'post',
			success: callBack,
			fail:function() {
				alert("서버와의 통신에 실패하였습니다");
			}
		})
	}
}