$(function(){

	isExistContName = true; 
	allowContName = '';

	// checkBtn click
	$('#checkBtn').click(function() {
		var ctrl_name =  $('#c_name').val();
		checkObj = new CheckDTO(ctrl_name);
		
		$.ajax({
            url:'/controller/check',
            type:'get',
            data: checkObj,
            success:function(result){
            	Swal.fire('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            		Swal.fire('', ctrl_name+'은(는) 사용할 수 있는 이름 입니다.','success');
            		isExistContName = false;
	        		allowContName = checkObj.ctrl_name;
            	}
            	else {
            		isExistContName = true;
            		allowContName = '';
            	}
            },
            error:function(result) {
            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
            	alert(result.data);
            }
        })
	});	//checkBtn End
		
	// enrollBtn click 
	$('#enrollBtn').click(function(){
	
		var ctrl_name =  $('#c_name').val();
		var local_ip = $('#c_local_ip').val();
		var local_port = $('#c_local_port').val();
		var admin_port = $('#c_admin_port').val();
		var watchdog = $('#watchdog').val();
		var enable = $('#enable').val();
	
		
		if(allowContName != ctrl_name || allowContName.length < 1 ){
			Swal.fire('경고','Controller명 중복검사 후 등록절차를 진행해주세요','error')
			isExistContName = true;
			return 0;
		}
		
		if(isExistContName != false){
			Swal.fire('경고','해당 Controller명을 사용하는 서버가 존재합니다','error')
			isExistContName = true; 
			return 0;
		}	
		
		var ip = ValidateIP(local_ip);
		var port = ValidateNumber(local_port);
		var adm_port = ValidateNumber(admin_port);
		
		if(ctrl_name == "" || local_ip == "" || local_port == "" || admin_port == "" || watchdog == "" || enable == ""){
			Swal.fire ('','필수항목을 모두 입력바랍니다.','error');
		}else if(ip == false){
			Swal.fire('','올바른 내부 IP를 입력해주세요','error');
		}else if(local_port == ""){
			Swal.fire('','올바른 내부 포트를 입력해주세요','error');
		}else if(admin_port == ""){
			Swal.fire('','올바른 관리자 포트를 입력해주세요','error');
		}else if(port == false){
			Swal.fire('','내부 포트에 올바른 형식의 값을 입력해주세요','error');
		}else if(adm_port == false){
			Swal.fire('','관리자 포트에 올바른 형식의 값을 입력해주세요','error');
		}else{
		
		enrollObj = new EnrollDTO(ctrl_name, local_ip, local_port, admin_port, watchdog, enable);
		
			$.ajax({
				url : '/controller',
				type : 'post',
				data : enrollObj,
				success :function(result){
					Swal.fire('',result.data.errorMessage,'error');
					
					if(result.data.errorCode == 0){
						Swal.fire('','등록에 성공하였습니다','success').then((value) => {
							window.location.href = '/controller/listPage';
						})
					}
				},
				error : function(result){
					Swal.fire('',result.data.errorMessage,'error');
				}
			})
		}
	}) // enrollBtn End
	
	// updateBtn click
	$('#updateBtn').click(function(){
	
		var ctrl_name =  $('#c_name').val();
		var local_ip = $('#c_local_ip').val();
		var local_port = $('#c_local_port').val();
		var admin_port = $('#c_admin_port').val();
		var watchdog = $('#watchdog').val();
		var enable = $('#enable').val();
		
		var ip = ValidateIP(local_ip);
		if(ctrl_name == "" || local_ip == "" || local_port == ""|| admin_port == "" || watchdog == "" || enable == "" ){
			Swal.fire('','필수 항목을 입력해주세요','error');
		}else if(ip == false){
			Swal.fire('','올바른 내부 IP를 입력해주세요','error');
		}else if(local_port == ""){
			Swal.fire('','올바른 내부 포트를 입력해주세요','error');
		}else if(admin_port == ""){
			Swal.fire('','올바른 관리자 포트를 입력해주세요','error');
		}else{
			updateObj = new UpdateDTO(ctrl_name, local_ip, local_port, admin_port, watchdog, enable);
			$.ajax({
				url : '/controller',
				type : 'put',
				data : updateObj,
				success : function(result){
					Swal.fire('',result.data.errorMessage,'error');
					if(result.data.errorCode == 0){
						Swal.fire('',result.data.errorMessage,'success').then((value)=>{
							window.location.href = "/controller/listPage";
						})
					}
				},
				error : function(){
					Swal.fire('',result.data.errorMessage,'error');
				}
			})			
		}
	})// updateBtn End
	
})// $(function()) End



//Foreign Function 
function goToControllerInfo(ctrl_no) {
	document.writeln('<form name=managerForm action=/controller/updatePage METHOD=GET>');
	document.writeln('    <input name=ctrl_no type=hidden>');
	document.writeln('</form>');
	document.managerForm.ctrl_no.value=ctrl_no;
	document.managerForm.submit();
}
function goToControllerFile(ctrl_no) {
	document.writeln('<form name=managerForm action=/controller/filePage METHOD=GET>');
	document.writeln('    <input name=ctrl_no type=hidden>');
	document.writeln('</form>');
	document.managerForm.ctrl_no.value=ctrl_no;
	document.managerForm.submit();
}

function ValidateIP(ip) {
	/*if (/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])((\/)([1-9]|[1-2][1-9]|3[1-2])){0,1}$/.test(ip)) {*/ // 서브넷 마스크 ?
	if (/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/.test(ip)) {
		return true
	}else{
		return false
	}
}

function ValidateNumber(num){
	var regexp = /^[0-9]*$/
	if(!regexp.test(num) ) {
		return false;
	}else{
		return true;
	}
}

