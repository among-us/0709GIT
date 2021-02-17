/**
 * 
 */
$(function(){
	
	
	
	$("#updateBtn").click(function() {
		updateObj = new UpdateDTO(
				$('input[name="user_id"]').val(),
				$('input[name="passwd"]').val(),
				$('input[name="repasswd"]').val()
		);
		$.ajax({
            url:'/userAccount',
            type:'put',
            data: updateObj,
            success:function(result){
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 2004) {
            		window.location.href = "/"
            			return ;
            	}
            	else if(result.data.errorCode == 0){
            		swal('성공','변경 완료', 'success')
					.then((value) => {
            		window.location.href = "/main";
					});
            	}
				$('input[name="passwd"]').val('')
				$('input[name="repasswd"]').val('')
            },
            faile:function() {
            	alert("서버와의 통신에 실패하였습니다")
            }
        })
	});
	
});



//전역
function goToUserInfoDetails(user_no) {
	document.writeln('<form name=userForm action=/user/updatePage METHOD=GET>');
	document.writeln('    <input name=user_no type=hidden>');
	document.writeln('</form>');
	document.userForm.user_no.value=user_no;
	document.userForm.submit();
}
