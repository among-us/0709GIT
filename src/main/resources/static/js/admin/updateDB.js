$(function(){
   $('#dbUpdateBox').change(function(){
      selectNameNo = $(this).val();
      selectNameValue = $(this).children("option:selected").text()
      
      $("input[name='u_db_type_no']").val(selectNameValue);
      
 	var test = $("input[name='u_db_type_no']").val();
 
})

  if($("input[name='u_db_type_no']").val() == "1"){
  	$('#d_t').val("mariaDB");
  }else{
  	$('#d_t').val("oracleDB");
  }
  
// enroll btn
	isExistSVCNAME = true; 
	allowSVCName = '';
	
	$('#enrollBtn').click(function(){
	console.log('enrollbtn click function start ')
	if(allowSVCName != $("input[name='db_svc_name']").val() || allowSVCName.length < 1 ){
		Swal.fire('경고','서비스명 중복검사 후에 등록절차를 진행해주세요','error')
		isExistSVCNAME = true;
		return 0;
	}
	

	if(isExistSVCNAME != false){
		Swal.fire('경고','해당 서비스명을 사용하는 유저가 존재합니다','error')
		isExistSVCNAME = true; 
		return 0;
	}
		
 	var db_svc_name = $("input[name='db_svc_name']").val();
    var tam_name = $("input[name='tam_name']").val();
    var db_type_no =$("#dbTypeBox option:selected").val();
    var db_priv_ip = $('input[name="db_priv_ip"]').val();		
    var db_priv_port = $('input[name="db_priv_port"]').val();		
    var db_pub_ip = $('input[name="db_pub_ip"]').val();			
    var db_pub_port = $('input[name="db_pub_port"]').val();			
    var db_id = $('input[name="db_id"]').val();		
    var db_passwd = $('input[name="db_passwd"]').val();		
    var db_name = $('input[name="db_name"]').val().trim();	
  	
    manaDBObj = new ManaDBDTO(db_svc_name, tam_name, db_type_no,db_priv_ip,db_priv_port,db_pub_ip,db_pub_port,db_id,db_passwd,db_name);
	 console.log(manaDBObj);

  	var result = ValidateIPaddress(db_priv_ip);
  	console.log(result);

	var test = db_priv_ip.split('.');
	var ipcheck = test[0];
	console.log("IP CHECK : "+ipcheck);
	
		var priv_result = ValidateIPaddress(db_priv_ip);	
		
		var pri_port_result= ValidateNumber(db_priv_port);
		console.log('validatenumber : '+pri_port_result);
			
		
    if (db_svc_name =="" || db_type_no == "default" || db_priv_ip == "" || db_priv_port == "" || db_id == "" || db_passwd=="" || db_name=="") {
        Swal.fire('', '필수항목을 입력해주세요', 'error');
	}else if(!result){
		Swal.fire('입력 오류','올바른 서비스 IPv4 주소를 입력해주세요','error');
	}else if(pri_port_result == false){
		Swal.fire('입력 오류','서비스 포트 입력값을 확인해주세요','error');
	}else if(db_priv_port > 65535 || db_priv_port < 0){
		Swal.fire('입력 오류','서비스 포트 범위를 확인해주세요','error');
	}else{
	
		if(db_pub_ip != ""){		// 공백이아니고 뭐라도 적었으면 검사들어가	
				var result = ValidateIPaddress(db_pub_ip);	
			if(!result){
				Swal.fire('입력 오류','올바른 외부 IPv4 주소를 입력해주세요','error');
				 return ;
			}
		}		
			
		if(db_pub_port != ""){		// 공백이아니고 뭐라도 적었으면 검사들어가
			console.log('dbPublicPort FLOW');
			
			var pub_port_result= ValidateNumber(db_pub_port);
			if(pub_port_result == false){
				Swal.fire('입력 오류','외부 포트 입력값을 확인해주세요','error');
				return ;
			}
			
			if(db_pub_port > 65535 || db_pub_port < 0){
				console.log('DB PUBLIC PORT IF FLOW');
				Swal.fire('입력 오류','외부 포트 범위를 확인해주세요','error');
				return ;
			}
			
		}
			 
		$.ajax({
		          url:'/manager/db',
		          type:'post',
		          data: manaDBObj,
		          success:function(result){
		          	console.log('success');
		            Swal.fire('성공','등록 성공', 'success').then((value) => {
						window.location.href = "/manager/listDBPage";
		      	  	});
		          },
		          error:function(result) {
		          console.log(result);
		          console.log('error');
		            Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
		          } //error
		      });// ajax      
		} // else
});
		
		
	
	
// ================================================================================================================================================

		
		
	
// ======================================= ======================================= ======================================= ======================================= =======================================
	
	
 $('#updateBtn').click(function() {
	 
  	var db_type_no = $("input[name='u_db_type_no']").val().trim();
  	
  	if(db_type_no == "mariaDB"){
  		db_type_no = 1
  	}else {
  		db_type_no = 2
  	}
  	
  	var db_no = $("input[name='db_no']").val();
  	var db_svc_name =	$("input[name='db_svc_name']").val();
  	
  	var db_priv_ip = $("input[name='db_priv_ip']").val();
  	var db_priv_port = $("input[name='db_priv_port']").val();
  	var db_pub_ip= $("input[name='db_pub_ip']").val();
  	var db_pub_port= $("input[name='db_pub_port']").val();
  	var db_id= $("input[name='db_id']").val();
  	var db_passwd= $("input[name='db_passwd']").val();
  	var db_name = $("input[name='db_name']").val();
  	
		updateObj = new UpdateDTO(db_no, db_svc_name, db_type_no, db_priv_ip, db_priv_port, db_pub_ip, db_pub_port, db_id, db_passwd, db_name);
				
				
		var priv_result = ValidateIPaddress(db_priv_ip);	
		console.log('priv IP RESULT : ' + priv_result);
		
		if(db_priv_ip == "" || db_priv_port == "" || db_id == "" || db_passwd == "" || db_name == ""){
				Swal.fire('입력 오류','필수 항목을 입력바랍니다','error');
			}else if(priv_result == false){
				Swal.fire('입력 오류','올바른 서비스 IPv4 주소를 입력해주세요','error');
			}else if(db_priv_port > 65535 || db_priv_port < 0){
				Swal.fire('입력 오류','서비스 포트 범위를 확인해주세요','error');
			}else {
				if(db_pub_ip != ""){
					var pub_result = ValidateIPaddress(db_pub_ip);		
						console.log('priv IP RESULT : ' + pub_result);
						if(!pub_result){
							Swal.fire('입력 오류','올바른 외부 IPv4 주소를 입력해주세요','error');
							return ;
						}
					}
					
				if(db_pub_port != ""){
					if(db_pub_port > 65535 || db_pub_port < 0){
							Swal.fire('입력 오류','올바른 외부 포트 범위를 입력해주세요','error');
							return ;
					}
				}
					// 0614 0615 start
					$.ajax({
			            url:'/db-info',
			            type:'put',
			            data: updateObj,
			            success:function(result){
			            	Swal.fire('실패', result.data.errorMessage,'error');
			            if(result.data.errorCode == 0) {
			            	Swal.fire({
								  text: '변경 완료',
								  icon : 'success',
								  iconColor : 'cornflowerblue',
								  width: 500,
								  padding: '2em',
								  background: '#272C33',
								  confirmButtonColor : 'cornflowerblue'
							})
								.then((value) => {
				            		 window.location.href = "/manager/listDBPage";
								});
			            	}
			            },
			            error:function(result) {
			            	Swal.fire('',result.data,'error');
			            }
			        })
			}
				
			
	});
	
	
	// ======================================= ======================================= ======================================= ======================================= =======================================
	
	
	
	
  
  /*
  $('#deleteBtn').click(function(){
  		var db_svc_name = $('input[name="db_svc_name"]').val();
  		console.log(db_svc_name);
  		
  		dbDelObj = new DeleteDTO(db_svc_name);
  		$.ajax({
	        url:'/manager/db',
          	type:'delete',
            data: dbDelObj,
            success:function(result){
            	Swal.fire('경고',result.data.errorMessage,'error');
	            	if(result.data.errorCode == 0) {
						Swal.fire('성공','삭제 성공', 'success')
					.then((value) => {
	            		window.location.href = "/manager/listDBPage"
					});
	            }
          	},
            error:function() {
            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
  		
  })
  */
  // Swal.fire delete check test 
  
  $('#deleteBtn').click(function(){
  Swal.fire({
	  title: 'DB 정보를 삭제하시겠습니까 ?',
	  icon: 'warning',
	  showCancelButton: true,
	  confirmButtonColor: '#d33',
	  cancelButtonColor: 'lightgray',
	  cancelButtonText: '<span style="color : black">취소</span>',
	  confirmButtonText: '삭제하겠습니다'
	}).then((result) => {
	  if (result.isConfirmed) {
	  
  		var db_svc_name = $('input[name="db_svc_name"]').val();
  		console.log(db_svc_name);
  
  		dbDelObj = new DeleteDTO(db_svc_name);
  		$.ajax({
	        url:'/manager/db',
          	type:'delete',
            data: dbDelObj,
            success:function(result){
	            	if(result.data.errorCode == 0) {
						Swal.fire('','삭제 성공','success').then((value) => {
	            		window.location.href = "/manager/listDBPage"
					});
	            }
          	},
            error:function() {
            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
  		
	  }
})
  
  })
  
  
}) // function end

// =========================== 외부 FUNCTION ============================================================================================================

function ValidateIPaddress(ip) {  
  if (/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])((\/)([0-9]|[0-2][0-9]|3[0-2])){0,1}$/.test(ip)) {
    return true ;
  }else{
  	return false ; 
  }
} 
 
 // port nubmer regexp ++++ 
 
function PubValidateIPaddress(db_pub_ip) {  
    if (/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])((\/)([0-9]|[0-2][0-9]|3[0-2])){0,1}$/.test(db_pub_ip)) {
    return ("true")  
  }else{
 	Swal.fire('','외부 ip error','error');
  	return ("false")  
  }
}  

function ValidateNumber(no) {
	if (/^[0-9]+$/.test(no)) {
		return true;
	}else{
		return false;
	}
}

