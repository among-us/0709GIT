$(function(){
	
	// get Account Info
	
	$.ajax({
		url : '/user/account-info',
		type : 'get',
		success : function(result){
			var perm_no = result.data;
			
			switch(perm_no){
				case 0 : $('input[name="permissions"]').val("no permission"); break;
				case 1 : $('input[name="permissions"]').val("admin");break;
				case 10 : $('input[name="permissions"]').val("GROUP");break;
				case 20 : $('input[name="permissions"]').val("PROJECT");break;
			}
						
		}, //success 
		error : function(result){
			alert("err >> "+result);
		}//error
	})// ajax end	
	
	
	var id = $('#u_id').val();
	var perm = $('#u_permission').val();
	
	if(perm == 0){
		$('input[name="permissions"]').val("no permission");
	}
	if(perm == 1){
		$('input[name="permissions"]').val("admin");
	}
	
	if(perm == 10){
		$('input[name="permissions"]').val("group");
	}
	
	if(perm == 20){
		$('input[name="permissions"]').val("unit");
	}
	
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
            	Swal.fire('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 2004) {
            		window.location.href = "/"
            			return ;
            	}
            	else if(result.data.errorCode == 0){
            		Swal.fire('성공','변경 완료', 'success')
					.then((value) => {
            		window.location.href = "/main";
					});
            	}
				$('input[name="passwd"]').val('')
				$('input[name="repasswd"]').val('')
            },
            error:function() {
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
