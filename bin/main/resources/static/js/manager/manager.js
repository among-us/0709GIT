/**
 * 
 */
 

$(function(){

	isExistTAMNAME = true; 
	allowTamName = '';
	/*
	$('#enrollBtn').click(function() {
			//console.log(isExistTAMNAME)
			if(allowTamName != $("input[name='tam_name']").val() || allowTamName.length < 1 ){
				Swal.fire('경고','서버명 중복검사 후에 등록절차를 진행해주세요','error')
				isExistTAMNAME = true;
				return 0;
			}
			
			if(isExistTAMNAME != false){
				Swal.fire('경고','해당 서버명을 사용하는 서버가 존재합니다','error')
				isExistTAMNAME = true; 
				return 0;
			}
			
			var t_l_i = $("input[name='tam_local_ip']").val();
			var t_l_p = $("input[name='tam_local_port']").val();
			var t_a_p = $("input[name='tam_admin_port']").val();
			
			var tam_pub_admin_port = 	$("input[name='tam_pub_admin_port']").val();
			if(tam_pub_admin_port == ""){
				tam_pub_admin_port = 0;
			}
			console.log('tamadmport : '+ tam_pub_admin_port);
			
			enrollObj = new EnrollDTO($("input[name='tam_name']").val(),
					$("input[name='site_info']").val(),
					$("input[name='tam_local_ip']").val(),
					$("input[name='tam_local_port']").val(),
					$("input[name='tam_admin_port']").val(),
					$("input[name='tam_pub_ip']").val(),
					$("input[name='tam_pub_port']").val(),
					tam_pub_admin_port,
					$("input[name='shared_path']").val(),
					$('select[name="comm_type"] option:selected').val(),
					$('select[name="watchdog"] option:selected').val()
					);
			
			
			var priv_ip =  $("input[name='tam_local_ip']").val();
			var priv_port = $("input[name='tam_local_port']").val();
			var tam_priv_port = $("input[name='tam_admin_port']").val();
			// 내부 ip / 내부 포트 / 관리자 내부 포트
			
			//console.log(priv_port);
			//console.log(tam_priv_port);
			
			var pub_ip = $("input[name='tam_pub_ip']").val();
			var pub_port = $("input[name='tam_pub_port']").val();
			// 외부 ip / 외부 포트 / 관리자 외부 포트
			
			
			
			var priv_result = ValidateIPaddress(priv_ip);
			var pub_result = ValidateIPaddress(pub_ip);

			//console.log('private ip : ' + priv_result);
			//console.log('pub ip : ' + pub_result);
			if(priv_ip == "" || priv_port == "" || tam_priv_port == ""){
				Swal.fire('경고','필수항목을 모두 입력바랍니다','error');
			}else if(priv_result == 'false'){
				Swal.fire('입력오류','올바른 내부 IPv4를 입력바랍니다','error');
			}else if(priv_port < 0 || priv_port > 65535){			
				Swal.fire('입력오류','올바른 범위의 내부 포트를 입력바랍니다','error');
			}else if(tam_priv_port <0 || tam_priv_port > 65535){			
				Swal.fire('입력오류','올바른 범위의 관리자 내부 포트를 입력바랍니다','error');
			}else if(tam_pub_admin_port < 0 || tam_pub_admin_port > 65535){
				Swal.fire('입력오류','올바른 범위의 관리자 외부 포트를 입력바랍니다','error');
			}else {
					$.ajax({
			            url:'/manager',
			            type:'post',
			            data: enrollObj,
			            success:function(result){
            	Swal.fire('경고',result.data.errorMessage,'error');
			            	if(result.data.errorCode == 0) {
			            	Swal.fire('성공','등록 성공', 'success')
							.then((value) => {
			            		 window.location.href = "/manager/listPage";
								});
			            	}
			            	if(result.data.errorCode == 2004) {
			            		window.location.href = "/"
			            	}
			            },
			            error:function(result) {
			            	//alert("서버와의 통신에 실패하였습니다");
			            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			            }
			        })
	        	}
		});
		*/
		// ==============================================================================================================================
		
			$('#enrollBtn').click(function() {
			if(allowTamName != $("input[name='tam_name']").val() || allowTamName.length < 1 ){
				Swal.fire('경고','서버명 중복검사 후에 등록절차를 진행해주세요','error')
				isExistTAMNAME = true;
				return 0;
			}
			
			if(isExistTAMNAME != false){
				Swal.fire('경고','해당 서버명을 사용하는 서버가 존재합니다','error')
				isExistTAMNAME = true; 
				return 0;
			}
			
			var t_l_i = $("input[name='tam_local_ip']").val();
			var t_l_p = $("input[name='tam_local_port']").val();
			var t_a_p = $("input[name='tam_admin_port']").val();
			
			var tam_name = $("input[name='tam_name']").val();
			var site_info = $("input[name='site_info']").val();
			var tam_local_ip = $("input[name='tam_local_ip']").val();
			var tam_local_port = $("input[name='tam_local_port']").val();
			var tam_admin_port = $("input[name='tam_admin_port']").val();
			var tam_pub_ip = $("input[name='tam_pub_ip']").val();
			var tam_pub_port = $("input[name='tam_pub_port']").val();
			var tam_pub_admin_port = $("input[name='tam_pub_admin_port']").val();
			var shared_path = $("input[name='shared_path']").val();
			var comm_type = $('select[name="comm_type"] option:selected').val();
			var watchdog = $('select[name="watchdog"] option:selected').val();
			
			var localport = ValidatePort(tam_local_port);
			console.log("localport result = "+localport);
			
			
			/*
			enrollObj = new EnrollDTO(
					$("input[name='tam_name']").val(),
					$("input[name='site_info']").val(),
					$("input[name='tam_local_ip']").val(),
					$("input[name='tam_local_port']").val(),
					$("input[name='tam_admin_port']").val(),
					$("input[name='tam_pub_ip']").val(),
					$("input[name='tam_pub_port']").val(),
					tam_pub_admin_port,
					$("input[name='shared_path']").val(),
					$('select[name="comm_type"] option:selected').val(),
					$('select[name="watchdog"] option:selected').val()
					);
			*/
			
			var priv_port = $("input[name='tam_local_port']").val();
			var tam_priv_port = $("input[name='tam_admin_port']").val();
			// 내부 ip / 내부 포트 / 관리자 내부 포트
			
			var pub_ip = $("input[name='tam_pub_ip']").val();
			var pub_port = $("input[name='tam_pub_port']").val();
			
			var priv_result = ValidateIPaddress(tam_local_ip);
			console.log('priv = '+priv_result);
			var tam_pub_admin_port = 	$("input[name='tam_pub_admin_port']").val();
			
			var priv_port_result = ValidatePort(priv_port);
			var priv_admin_port_result = ValidatePort(tam_priv_port);
			

			if(tam_local_ip == "" || tam_local_port == "" || tam_admin_port == ""){
				Swal.fire('경고','필수항목을 모두 입력바랍니다','error');
			}else if(priv_result == false){
				Swal.fire('입력오류','올바른 내부 IPv4를 입력바랍니다','error');
			}else if(priv_port_result == false){
				Swal.fire('입력오류','올바른 내부 포트를 입력바랍니다','error');
			}else if(priv_admin_port_result == false){
				Swal.fire('입력오류','올바른 내부 관리자 포트를 입력바랍니다','error');
			}else if(tam_local_port > 65535 || tam_local_port < 0){
				Swal.fire('입력오류','올바른 범위의 내부 포트를 입력바랍니다','error');
			}else if(tam_admin_port > 65535 || tam_admin_port<0){
				Swal.fire('입력오류','올바른 범위의 관리자 내부 포트를 입력바랍니다','error');
			}else {
				if(pub_ip != ""){
					var pub_result = ValidateIPaddress(pub_ip);
						if(!pub_result){
								Swal.fire('입력오류','올바른 외부 IPv4를 입력바랍니다','error');
								return;
						}
				}
				
				if(pub_port != ""){
					var pub_port_result = ValidatePort(pub_port);
					if(pub_port_result == false){
						Swal.fire('입력오류','올바른 외부 포트를 입력바랍니다','error');
						return;						
					}
					
					if (pub_port > 65535 || pub_port < 0){
						Swal.fire('입력오류','올바른 범위의 외부 포트를 입력바랍니다','error');
						return;
					}
				}
			
				if(tam_pub_admin_port != ""){
					var pub_admin_result = ValidatePort(tam_pub_admin_port);
					if(pub_admin_result == false){
						Swal.fire('입력오류','올바른 관리자 외부 포트를 입력바랍니다','error');
						return;						
					}
					
					if (tam_pub_admin_port > 65535 || tam_pub_admin_port < 0){
						Swal.fire('입력오류','올바른 범위의 관리자 외부 포트를 입력바랍니다','error');
						return;
					}
				}
				
					enrollObj = new EnrollDTO(tam_name,site_info,tam_local_ip,tam_local_port,tam_admin_port,tam_pub_ip,tam_pub_port,tam_pub_admin_port,shared_path,comm_type,watchdog);
					console.log(enrollObj);
				
					$.ajax({
			            url:'/manager',
			            type:'post',
			            data: enrollObj,
			            success:function(result){
            				Swal.fire('경고',result.data.errorMessage,'error');
			            	if(result.data.errorCode == 0) {
			            	Swal.fire('성공','등록 성공', 'success')
							.then((value) => {
			            		 window.location.href = "/manager/listPage";
								});
			            	}
			            	if(result.data.errorCode == 2004) {
			            		window.location.href = "/"
			            	}
			            },
			            error:function(result) {
			            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			            }	//error
			        })	//ajax
			} // else
		});
		
		// ==============================================================================================================================
		// checkBTN Click Event ( For Jquery )
		$('#checkBtn').click(function() {
				checkObj = new CheckDTO($("input[name='tam_name']").val());
				var t_name = $('#t_name').val();
				$.ajax({
		            url:'/manager',
		            type:'get',
		            data: checkObj,
		            success:function(result){
		            	Swal.fire('경고',result.data.errorMessage,'error');
		            	if(result.data.errorCode == 0) {
		            		Swal.fire('', t_name+'은(는) 사용할 수 있는 이름 입니다.','success');
		            		isExistTAMNAME = false;
			        		allowTamName = checkObj.tam_name;
			        		//console.log(allowTamName)
		            	}
		            	else {
		            		isExistTAMNAME = true;
		            		allowTamName = '';
		            	}
		            },
		            error:function() {
		            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
		            }
		        })
		});
		
		$('#updateBtn').click(function() {
			var tam_name = $("input[name='tam_name']").val();
			var site_info = $("input[name='site_info']").val();
			var tam_local_ip = $("input[name='tam_local_ip']").val();
			var tam_local_port = $("input[name='tam_local_port']").val();
			var tam_admin_port = $("input[name='tam_admin_port']").val();
			var tam_pub_ip = $("input[name='tam_pub_ip']").val();
			var tam_pub_port = $("input[name='tam_pub_port']").val();
			var tam_pub_admin_port = $("input[name='tam_pub_admin_port']").val();
			var shared_path = $("input[name='shared_path']").val();
			var comm_type = $('select[name="comm_type"] option:selected').val();
			var watchdog = $('select[name="watchdog"] option:selected').val();
			
			var localport = ValidatePort(tam_local_port);
			
			var priv_port = $("input[name='tam_local_port']").val();
			var tam_priv_port = $("input[name='tam_admin_port']").val();
			// 내부 ip / 내부 포트 / 관리자 내부 포트
			
			var pub_ip = $("input[name='tam_pub_ip']").val();
			var pub_port = $("input[name='tam_pub_port']").val();
			
			var priv_result = ValidateIPaddress(tam_local_ip);
			console.log('priv = '+priv_result);
			var tam_pub_admin_port = 	$("input[name='tam_pub_admin_port']").val();
			
			var priv_port_result = ValidatePort(priv_port);
			var priv_admin_port_result = ValidatePort(tam_priv_port);
			

			if(tam_local_ip == "" || tam_local_port == "" || tam_admin_port == ""){
				Swal.fire('경고','필수항목을 모두 입력바랍니다','error');
			}else if(priv_result == false){
				Swal.fire('입력오류','올바른 내부 IPv4를 입력바랍니다','error');
			}else if(priv_port_result == false){
				Swal.fire('입력오류','올바른 내부 포트를 입력바랍니다','error');
			}else if(priv_admin_port_result == false){
				Swal.fire('입력오류','올바른 내부 관리자 포트를 입력바랍니다','error');
			}else if(tam_local_port > 65535 || tam_local_port < 0){
				Swal.fire('입력오류','올바른 범위의 내부 포트를 입력바랍니다','error');
			}else if(tam_admin_port > 65535 || tam_admin_port<0){
				Swal.fire('입력오류','올바른 범위의 관리자 내부 포트를 입력바랍니다','error');
			}else {
				
				if(pub_ip != ""){
					var pub_result = ValidateIPaddress(pub_ip);
						if(!pub_result){
								Swal.fire('입력오류','올바른 외부 IPv4를 입력바랍니다','error');
								return;
						}
				}
				
				if(pub_port != ""){
					var pub_port_result = ValidatePort(pub_port);
					if(pub_port_result == false){
						Swal.fire('입력오류','올바른 외부 포트를 입력바랍니다','error');
						return;						
					}
					
					if (pub_port > 65535 || pub_port < 0){
						Swal.fire('입력오류','올바른 범위의 외부 포트를 입력바랍니다','error');
						return;
					}
				}
			
				if(tam_pub_admin_port != ""){
					var pub_admin_result = ValidatePort(tam_pub_admin_port);
					if(pub_admin_result == false){
						Swal.fire('입력오류','올바른 관리자 외부 포트를 입력바랍니다','error');
						return;						
					}
					
					if (tam_pub_admin_port > 65535 || tam_pub_admin_port < 0){
						Swal.fire('입력오류','올바른 범위의 관리자 외부 포트를 입력바랍니다','error');
						return;
					}
				}
				
				updateObj = new UpdateDTO($("input[name='tam_no']").val(),
					$("input[name='tam_name']").val(),
					$("input[name='site_info']").val(),
					$("input[name='tam_local_ip']").val(),
					$("input[name='tam_local_port']").val(),
					$("input[name='tam_admin_port']").val(),
					$("input[name='tam_pub_ip']").val(),
					$("input[name='tam_pub_port']").val(),
					$("input[name='tam_pub_admin_port']").val(),
					$("input[name='tam_license']").val(),
					$("input[name='shared_path']").val(),
					$('select[name="comm_type"] option:selected').val(),
					$('select[name="watchdog"] option:selected').val(),
					/*$('select[name="tam_state"] option:selected').val(),*/
					$('select[name="exist"] option:selected').val());

						$.ajax({
				            url:'/manager',
				            type:'put',
				            data: updateObj,
				            success:function(result){
				            	Swal.fire('실패', result.data.errorMessage,'error');
				            	if(result.data.errorCode == 0) {
				            	Swal.fire('성공','변경 완료', 'success')
								.then((value) => {
				            		 window.location.href = "/manager/listPage";
									});
				            	}
				            },
				            error:function() {
				            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
				            }
				        })
	        }
	});
	
/*	$('#deleteBtn').click(function() {
		deleteObj = new DeleteDTO($("input[name='tam_no']").val());
		$.ajax({
            url:'/manager',
            type:'delete',
            data: deleteObj,
            success:function(result){
          		   Swal.fire('',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            		window.location.href = "/manager/listPage";
            	}
            },
            error:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	Swal.fire('실패','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	*/

	
	$('#createBtn').click(function(){
		var s_t_name = $('select[name=selected_tam_name]').val();
		$('#s_tam_name')[0].innerHTML = s_t_name;
	
		$('#s_tam_name').val(s_t_name);
	});
	
	
	$('#download_btn').click(function(){
		window.location.href = "/manager/settingPage"
	})
	
	
	
	
});

//전역
function goToManagerZoneMatch(tam_no) {
	document.writeln('<form name=managerForm action=/manager/updatePage METHOD=GET>');
	document.writeln('    <input name=tam_no type=hidden>');
	document.writeln('</form>');
	document.managerForm.tam_no.value=tam_no;
	document.managerForm.submit();
}

function goToManagerDB(tam_no) {
	document.writeln('<form name=managerForm action=/manager/settingPage METHOD=GET>');
	document.writeln('    <input name=tam_no type=hidden>');
	document.writeln('</form>');
	document.managerForm.tam_no.value=tam_no;
	document.managerForm.submit();
}


function ValidateIPaddress(ip){  
 	if (/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])((\/)([1-9]|[1-2][1-9]|3[1-2])){0,1}$/.test(ip)) {
		return true;
 	}else{
		return false;  
	}
}  

function ValidatePort(port){
	var regexp = /^[0-9]*$/
	
	if( !regexp.test(port) ) {
		return false;
	}else{
		return true;
    }
}