/**
 * 
 */


$(function(){

// user permission check flow
	$.ajax({
		url : '/user/permission',
		type : 'get',
		success : function(result){
			if(result.data.errorCode == -1){
				document.getElementById("perm_box").disabled = true;
				document.getElementById("existBox").disabled = true;
			}
		},
		error : function(result){
		}
	})



/*
	$('#main_back').click(function() {
		window.location.href="/";
	});
	
	isExistUSERID = true;
	allowUSERID = '';
	
	$("#enrollBtn").click(function() {
		if(allowUSERID != $("input[name='user_id']").val() || allowUSERID.length < 1 ){
			//alert('ID 중복검사 후에 등록절차를 진행해주세요')
			Swal.fire('경고', 'ID 중복검사 후에 등록절차를 진행해주세요','error');
			isExistUSERID = true;
			return 0;
		}
		
		if(isExistUSERID != false){
			//alert('해당 ID를 사용하는 유저가 존재합니다')
			Swal.fire('경고', '해당 ID를 사용하는 유저가 존재합니다','error');
			isExistUSERID = true; 
			return 0;
		}
		
		//0428
		
		var user_id = $("input[name='user_id']").val();
		var passwd = $("input[name='passwd']").val();
		var repasswd = $("input[name='repasswd']").val();
		var user_company_name = $("input[name='user_company_name']").val();
		var user_dept_name = $("input[name='user_dept_name']").val();
		var user_name = $('input[name="user_name"]').val();
		var user_email = $('input[name="user_email"]').val();
		var user_phone_num1 = $("input[name='user_phone_num1']").val();
		var user_phone_num2 = $("input[name='user_phone_num2']").val();
		
		var pwdResult = CheckPWD(passwd);
		console.log("PWD : "+pwdResult);
		
		var emailResult = CheckEMAIL(user_email);
		console.log("EMAIL : "+emailResult);
				
		if(user_name == "" || user_company_name == "" || user_dept_name == "" || user_email == "" || user_phone_num1 == ""){
			Swal.fire('','필수항목을 모두 입력바랍니다','error')
		}else if(pwdResult == false){
			Swal.fire('','영문, 숫자, 특수문자를 조합하여 8자 이상 16자 이하로 입력해주세요','error')
		}else if(emailResult == false){
			Swal.fire('','올바른 형식의 이메일 주소를 입력해주세요','error')
		}else {
		enrollObj = new userDTO(user_id,passwd,repasswd,user_company_name,user_dept_name,user_name,user_email,user_phone_num1,user_phone_num2);
		
		console.log(enrollObj)
		
		$.ajax({
            url:'/user',
            type:'post',
            data: enrollObj,
            success:function(result){
            	Swal.fire('실패', result.data.errorMessage,'error');
            	if(result.data.errorCode == 0){
	            	Swal.fire('알림','계정 등록은 관리자 확인 승인 후 완료 됩니다','info').then((value) => {
		 	           	Swal.fire('','성공하였습니다', 'success')
							.then((value) => {
			            		window.location.href="/"
						});
	            	})
            	}
            },
            error:function() {
            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
            }
        })
        
        }
	});
	
	*/
	$("#updateBtn").click(function() {
	
	var user_no = $('input[name="user_no"]').val();
	var user_id = $('input[name="user_id"]').val();
	var perm_no = $('select[name="permissions"] option:selected').val();
	var user_name = $('input[name="user_name"]').val();
	
	var comp_no= $('select[name="comp_name"] option:selected').val();
	var zone_name = $('select[name="proj_name"] option:selected').val();
	
	var user_company_name= $('input[name="user_company_name"]').val();
	var user_dept_name = $('input[name="user_dept_name"]').val();
	var user_email = $('input[name="user_email"]').val();
	var user_phone_num1 = $('input[name="user_phone_num1"]').val();
	var user_phone_num2 = $('input[name="user_phone_num2"]').val();
	var exist = $('select[name="exist"] option:selected').val();
	
	var emailResult = CheckEMAIL(user_email);
	console.log("email Result : "+emailResult);
	var phone1Result = CheckPhone(user_phone_num1);
	console.log("phone 1 Result : "+phone1Result);
		
		if(user_name == "" || user_company_name == "" || user_dept_name==""|| user_email == "" || user_phone_num1 == ""){
			Swal.fire('','필수항목을 입력바랍니다','error');
		}else if(emailResult == false){
			Swal.fire('','올바른 형식의 이메일을 입력해주세요','error');
		}else if(phone1Result == false){
			Swal.fire('','올바른 형식의 연락처를 입력해주세요','error');
		}else{
		
		if(user_phone_num2 != ""){
			var phone2Result = CheckPhone(user_phone_num2);
			console.log("phone2Result > > "+phone2Result);
			
			if(phone2Result == false){
				Swal.fire('','올바른 형식의 연락처를 입력해주세요','error');
				return;
			}
		}		
		
		if(comp_no == "default"){
			comp_no = $('#asis_comp_no').val();
		}
		if(zone_name == "default"){
			zone_name = $('#u_proj_name').val();
		}
		
		updateObj = new UpdateDTO(user_no, user_id,perm_no,user_name,comp_no,zone_name,user_company_name,user_dept_name,user_email,user_phone_num1,user_phone_num2,exist);
		console.log(updateObj)
		
			$.ajax({
	            url:'/user',
	            type:'put',
	            data: updateObj,
	            success:function(result){
		            Swal.fire('실패', result.data.errorMessage,'error');
		            	if(result.data.errorCode == 0){
		            		UserChangeSwal();
		            	}
	            },
	            error:function() {
	            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
	            }
	        }) // ajax
		} //else
	});
	
	
	
	
	$('#comp_box').change(function(){
		var comp_no = $('#comp_box option:selected').val();
		projObj = new ProjectDTO(comp_no);
		
		$.ajax({
			url:'/project/matching',
			type:'get',
			data: projObj,
			success:function(result){
				console.log(result);
				$('#proj_box').children().remove();
					$('#proj_box').append('<option value="default">-- 선택 --</option>');
				for(var i =0;i<result.length;i++){
					$('#proj_box').append('<option>'+result[i]+'</option>');
				}
			}
		}) // ajax
		
		
	})	// change function 
	
});

//전역
function goToUserInfoDetails(user_no) {
	document.writeln('<form name=userForm action=/user/updatePage METHOD=GET>');
	document.writeln('    <input name=user_no type=hidden>');
	document.writeln('</form>');
	document.userForm.user_no.value=user_no;
	document.userForm.submit();
}

function CheckPWD(pwd) {
	if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/.test(pwd)) {
		return true;
	}else{
		return false;
	}
}

function CheckEMAIL(email) {
	if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(email)) {
		return true;
	}else{
		return false;
	}
}

function CheckPhone(num) {
	if (/^\d{3}\d{3,4}\d{4}$/.test(num)) {
		return true;
	}else{
		return false;
	}
}

