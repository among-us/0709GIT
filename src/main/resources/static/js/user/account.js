$(function(){
	
	
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
	
	var user_id = $('#u_id').val();
	var passwd = $('#u_pwd').val();
	var repasswd = $('#u_re_pwd').val();
	var user_company_name = $('#u_company_name').val();
	var user_dept_name = $('#u_dept_name').val();
	var user_name  = $('#u_name').val();
	var user_email = $('#u_email').val();
	var user_phone_num1 = $('#u_phone1').val();
	var user_phone_num2 = $('#u_phone2').val();
	
	if(passwd== "" || repasswd=="" || user_company_name==""||user_dept_name==""||user_name==""||user_email==""||user_phone_num1==""|| user_phone_num2==""){
		Swal.fire('','','error');
	}
	updateObj = new UpdateDTO(user_id, passwd, repasswd, user_company_name, user_dept_name,user_name,user_email,user_phone_num1,user_phone_num2);
		
			
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
