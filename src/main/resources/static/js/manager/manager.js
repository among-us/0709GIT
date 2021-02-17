/**
 * 
 */

$(function(){
	isExistTAMNAME = true; 
	allowTamName = '';
	
	// enrollBtn Click Event ( For Jquery )
	$('#enrollBtn').click(function() {
		console.log(isExistTAMNAME)
		if(allowTamName != $("input[name='tam_name']").val() || allowTamName.length < 1 ){
			//alert('서버명 중복검사 후에 등록절차를 진행해주세요')
			swal('경고','서버명 중복검사 후에 등록절차를 진행해주세요','error')
			isExistTAMNAME = true;
			return 0;
		}
		
		if(isExistTAMNAME != false){
			//alert('해당 서버명을 사용하는 서버가 존재합니다')
			swal('경고','해당 서버명을 사용하는 서버가 존재합니다','error')
			isExistTAMNAME = true; 
			return 0;
		}
		
		enrollObj = new EnrollDTO($("input[name='tam_name']").val(),
				$("input[name='site_info']").val(),
				$("input[name='tam_local_ip']").val(),
				$("input[name='tam_local_port']").val(),
				$("input[name='tam_admin_port']").val(),
/*				$("input[name='tam_license']").val(),*/
				$("input[name='sharedPath']").val(),
				$("input[name='log_path']").val(),
				$('select[name="log_level"] option:selected').val(),
				$("input[name='shared_path']").val(),
				/*$("input[name='comm_type']").val());*/
				$('select[name="comm_type"] option:selected').val(),
				$('select[name="watchdog"] option:selected').val()
				/*$('select[name="tam_state"] option:selected').val()*/);
		$.ajax({
            url:'/manager',
            type:'post',
            data: enrollObj,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            	swal('성공','등록 성공', 'success')
				.then((value) => {
            		 window.location.href = "/manager/listPage";
					});
            	}
            	if(result.data.errorCode == 2004) {
            		window.location.href = "/"
            	}
            },
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	swal('경고','서버와의 통신에 실패하였습니다','error');
            	
            }
        })
	});
	
	
	// checkBTN Click Event ( For Jquery )
	$('#checkBtn').click(function() {
		checkObj = new CheckDTO($("input[name='tam_name']").val());
		
		$.ajax({
            url:'/manager',
            type:'get',
            data: checkObj,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            		swal('','사용할 수 있는 TAM명 입니다.','success');
            		isExistTAMNAME = false;
	        		allowTamName = checkObj.tam_name;
	        		console.log(allowTamName)
            	}
            	else {
            		isExistTAMNAME = true;
            		allowTamName = '';
            	}
            },
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	swal('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
	$('#updateBtn').click(function() {
		updateObj = new UpdateDTO($("input[name='tam_no']").val(),
				$("input[name='tam_name']").val(),
				$("input[name='site_info']").val(),
				$("input[name='tam_local_ip']").val(),
				$("input[name='tam_local_port']").val(),
				$("input[name='tam_admin_port']").val(),
				$("input[name='tam_license']").val(),
				$("input[name='shared_path']").val(),
				$("input[name='log_path']").val(),
				$('select[name="log_level"] option:selected').val(),
				$('select[name="comm_type"] option:selected').val(),
				$('select[name="watchdog"] option:selected').val(),
				/*$('select[name="tam_state"] option:selected').val(),*/
				$('select[name="exist"] option:selected').val());
		
		$.ajax({
            url:'/manager',
            type:'put',
            data: updateObj,
            success:function(result){
            	swal('실패', result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            	// 확인 버튼 누르면 나갈 수 있게 수정
            	swal('성공','변경 완료', 'success')
				.then((value) => {
            		 window.location.href = "/manager/listPage";
					});
            	}
            },
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	swal('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
/*	$('#deleteBtn').click(function() {
		deleteObj = new DeleteDTO($("input[name='tam_no']").val());
		$.ajax({
            url:'/manager',
            type:'delete',
            data: deleteObj,
            success:function(result){
            	// alert(result.data.errorMessage);
          		   swal('',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            		window.location.href = "/manager/listPage";
            	}
            },
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	swal('실패','서버와의 통신에 실패하였습니다','error');
            }
        })
	});*/

	table = $('#tables').DataTable( {
		info:false,
		responsive: false,
	    order: [ 0, 'asc' ]
	});


	$('#createBtn').click
});

//전역
function goToManagerZoneMatch(tam_no) {
	document.writeln('<form name=managerForm action=/manager/updatePage METHOD=GET>');
	document.writeln('    <input name=tam_no type=hidden>');
	document.writeln('</form>');
	document.managerForm.tam_no.value=tam_no;
	document.managerForm.submit();
}