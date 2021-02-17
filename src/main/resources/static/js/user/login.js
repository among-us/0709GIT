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
            faile:function() {
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
		
		$.ajax({
            url:'/login',
            type:'post',
            data: loginObj,
            success:function(result){
            	if(result.data.errorCode == 0){
            		window.location.href="/main"
            	}
            	else
            		//$('#loginError').text(result.data.errorMessage)
            		swal('',result.data.errorMessage,'error');
            		
            	
            },
            faile:function() {
            	alert("서버와의 통신에 실패하였습니다")
            	swal('','서버와의 통신에 실패하였습니다','error');
            	
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
	