/*
$(function(){
	$("#loginBtn").click(function() {
		loginObj = new LoginDTO(
				$('input[name="user_id"]').val(),
				$('input[name="passwd"]').val());
		
		$.ajax({
            url:'/login',
            type:'post',
            data: loginObj,
            success:function(result){
            	if(result.data.errorCode == 0){
            		window.location.href="/main"
            	}
            	else
            		$('#loginError').text(result.data.errorMessage)
            	
            },
            error:function() {
            	alert("서버와의 통신에 실패하였습니다")
            }
        })
	});
});
//기존소스
 */



$(function(){
	$("#loginBtn").click(clickLogin);
	
	function clickLogin() {
		loginObj = new LoginDTO(
				$('input[name="user_id"]').val(),
				$('input[name="passwd"]').val());
				var user_id = $('input[name="user_id"]').val();
				var passwd = $('input[name="passwd"]').val();
				
				if(user_id == "" || passwd == ""){
					Swal.fire('','아이디 또는 비밀번호를 입력해주세요','error')
					return;
				}
		$.ajax({
            url:'/login',
            type:'post',
            data: loginObj,
            success:function(result){
            console.log(result.data);
            
            	if(result.data.errorCode == 55){
            		Swal.fire('',result.data.errorMessage,'error');
            		return;
            	}
            	if(result.data.errorCode == 9999){
            		Swal.fire('',result.data.errorMessage,'error');
            		return;
            	}
            	if(result.data.errorCode == 0){
           			console.log(result.data);
            		window.location.href="/main"
            		return;
            	}
            	if(result.data.errorCode == 1002){
            		console.log('1002 code flow');
            		//$('#loginError').text(result.data.errorMessage);
            		Swal.fire('',result.data.errorMessage,'error');
            		return;
            	}
            	
            	
            },
            error:function(result) {
            	alert(result.data)
            	//Swal.fire('','서버와의 통신에 실패하였습니다','error');
            }
        })
	}
	
	function keyEnter(){
		 if ( window.event.keyCode == 13 ) {
         	clickLogin();
        }
	}
	
	$('.btn-user').on('click',keyEnter)
    $('#exampleInputPassword').on('keyup',keyEnter)
});
	