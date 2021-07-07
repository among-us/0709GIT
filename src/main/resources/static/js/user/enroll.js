$(function(){

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
					
			var phone1Result = CheckPhone(user_phone_num1);
			console.log("Phone : " + phone1Result);
			
			if(user_name == "" || user_company_name == "" || user_dept_name == "" || user_email == "" || user_phone_num1 == ""){
				Swal.fire('','필수항목을 모두 입력바랍니다','error')
			}else if(pwdResult == false){
				Swal.fire('','비밀번호는 영문, 숫자, 특수문자를 조합하여 8자 이상 16자 이하로 입력해주세요','error')
			}else if(emailResult == false){
				Swal.fire('','올바른 형식의 이메일 주소를 입력해주세요','error')
			}else if(phone1Result == false){
				Swal.fire('','올바른 형식의 연락처를 입력해주세요','error');
			}
			else {
			if(user_phone_num2 != ""){
				var phone2Result = CheckPhone(user_phone_num2);
				if(phone2Result == false){
					Swal.fire('','올바른 형식의 연락처를 입력해주세요','error');
					return;
				}
			}
			
			enrollObj = new userDTO(user_id,passwd,repasswd,user_company_name,user_dept_name,user_name,user_email,user_phone_num1,user_phone_num2);
			
			$.ajax({
	            url:'/user',
	            type:'post',
	            data: enrollObj,
	            success:function(result){
			console.log('>>>success result ');			
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
	            error:function(result) {
	            console.log('result error');
	            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
	            }
	        })
	        
	        }
		});
		
		
		$('#checkBtn').click(function() {
		checkObj = new CheckDTO($("input[name='user_id']").val());
		var id = $('#u_id').val();
		
		$.ajax({
            url:'/user',
            type:'get',
            data: checkObj,
            success:function(result){
            	Swal.fire('',result.data.errorMessage,'error');
            	
            	if(result.data.errorCode == 0) {
	            	Swal.fire('','사용할 수 있는 ID 입니다','success');
            		isExistUSERID = false;
            		allowUSERID = checkObj.user_id;
            	}
            	else {
            		isExistUSERID = true;
            		allowUSERID = '';
            	}
            },
            error:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
})


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

function CheckPhone(phone) {
	if (/^\d{3}\d{3,4}\d{4}$/.test(phone)) {
		return true;
	}else{
		return false;
	}
}