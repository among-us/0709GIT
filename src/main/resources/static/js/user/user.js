/**
 * 
 */


$(function(){
	
	isExistUSERID = true;
	allowUSERID = '';
	
	$("#enrollBtn").click(function() {
		if(allowUSERID != $("input[name='user_id']").val() || allowUSERID.length < 1 ){
			//alert('ID 중복검사 후에 등록절차를 진행해주세요')
			swal('경고', 'ID 중복검사 후에 등록절차를 진행해주세요','error');
			isExistUSERID = true;
			return 0;
		}
		
		if(isExistUSERID != false){
			//alert('해당 ID를 사용하는 유저가 존재합니다')
			swal('경고', '해당 ID를 사용하는 유저가 존재합니다','error');
			isExistUSERID = true; 
			return 0;
		}
		
		
		enrollObj = new userDTO($("input[name='user_id']").val(),
				$("input[name='passwd']").val(), 
				$("input[name='repasswd']").val(), 
				$('select[name="comp_no"] option:selected').val());
		
		console.log(enrollObj)
		
		$.ajax({
            url:'/user',
            type:'post',
            data: enrollObj,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('실패', result.data.errorMessage,'error');
            	if(result.data.errorCode == 0){
            	swal('','성공하였습니다', 'success')
				.then((value) => {
            		window.location.href="/"
					});
            	}
            },
            faile:function() {
            	//alert("서버와의 통신에 실패하였습니다")
            	swal('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
	
	$("#updateBtn").click(function() {
		updateObj = new UpdateDTO(
				$('input[name="user_no"]').val(),
				$('select[name="permissions"] option:selected').val(),
				$('select[name="comp_name"] option:selected').val(), 
				$('select[name="exist"] option:selected').val());
		
		console.log(updateObj)
		
		$.ajax({
            url:'/user',
            type:'put',
            data: updateObj,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('실패', result.data.errorMessage,'error');
            	if(result.data.errorCode == 0){
            		swal('성공','변경 완료', 'success')
				.then((value) => {
            		window.location.href="/user/listPage"
					});
            	}
            },
            faile:function() {
            	//alert("서버와의 통신에 실패하였습니다")
            	swal('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
	$('#checkBtn').click(function() {
		checkObj = new CheckDTO($("input[name='user_id']").val());
		
		$.ajax({
            url:'/user',
            type:'get',
            data: checkObj,
            success:function(result){
            //	alert(result.data.errorMessage);
            	swal('',result.data.errorMessage,'error');
            	
            	if(result.data.errorCode == 0) {
	            	swal('','사용할 수 있는 ID 입니다','success');
            		isExistUSERID = false;
            		allowUSERID = checkObj.user_id;
            	}
            	else {
            		isExistUSERID = true;
            		allowUSERID = '';
            	}
            },
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	swal('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	

	table = $('#tables').DataTable({
		info:false,
		responsive: false,
		order: [ 0, 'asc' ]
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
