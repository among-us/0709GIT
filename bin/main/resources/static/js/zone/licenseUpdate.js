/**
 * 
 */
function ValidateIPaddress(taa_ip) {
	if  (/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])((\/)([0-9]|[0-2][0-9]|3[0-2])){0,1}$/.test(taa_ip)){
		return ("true")																							      //[1-9]|[1-2]
	}else{
		Swal.fire('','올바른 IPv4 주소를 입력해주세요','error');
		return ("false")
	}
}

$(function(){


var lic_type = $('input[name="now_lic_type"]').val();
/*
if(lic_type == "static"){
	$('input[name="now_lic_type"]').val('정적 라이선스');
}
if(lic_type == "dynamic"){
	$('input[name="now_lic_type"]').val('동적 라이선스');
}
if(lic_type == "timelimit"){
	$('input[name="now_lic_type"]').val('시간제한 라이선스');
}

*/
/*
var lic_type = $('#now_stat_lic_type').text();
	
if(lic_type == "static"){
	$('#now_stat_lic_type').text("Static");
	$('select[name="license_type"]').val("1");
}else if(lic_type == "dynamic"){
	$('#now_stat_lic_type').text("Dynamic");
	$('select[name="license_type"]').val("2");
}else if(lic_type == "timelimit"){
	$('#now_stat_lic_type').text("Timelimit");
	$('select[name="license_type"]').val("3");
}*/


$("#updateBtn").click(function() {
	Swal.fire({
	  title: "",
	  text:'허용 정보를 변경하시겠습니까?',
	  icon: "warning",
	  confirmButtonColor: '#4E73DF',
	  cancelButtonColor: '#d33',
	  confirmButtonText: '네',
	  cancelButtonText : '아니오',
	  showCancelButton : true
	})
	.then((result) => {
	  if (result.isConfirmed) {
	 	var zone_no = $('input[name="zone_no"]').val();
		var taa_ip = $('input[name="taa_ip"]').val();
	 	var asis_taa_ip = $('#now_stat').text();
		var string_license = $('#license').val();
		
		var license_type = "";
		 
		if(string_license == "정적 라이선스"){
			license_type = 1	
		}else if(string_license == "동적 라이선스"){
			license_type = 2	
		}else if(string_license == "시간제한 라이선스"){
			license_type = 3	
		}else if(string_license == "N/A"){
			license_type = 99	
		}
		
		zoneLicenseDTO = new ZoneLicenseUpdateDTO(zone_no, taa_ip,asis_taa_ip, license_type);
		console.log(zoneLicenseDTO);
				
		var result = ValidateIPaddress(taa_ip);
		
		if(taa_ip == asis_taa_ip){
			Swal.fire('','이미 해당 IP를 사용하고있습니다','error');
		}else if(result =="false"){
			Swal.fire('입력 오류','올바른 로컬 IPv4 주소를 입력해주세요','error');
		}else{
				$.ajax({
			        url:'/zone/license',
		          	type:'put',
		            data: zoneLicenseDTO,
		            	success:function(result){
			            	Swal.fire('',result.data.errorMessage,'error');
			            	if(result.data.errorCode == 0) {
			            		Swal.fire('성공','변경 완료', 'success')
								.then((value) => {
			            			window.location.href = "/zoneLicense/listPage"
								});
			            	}
			            },
			            error:function(result) {
			            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			            }
			        })
				  } 
  	}
});
	});
	
	$('#deleteBtn').click(function(){
	
	Swal.fire({
			  title: '',
			  text: "삭제 시 해당 정보 라이선스는 사용할 수 없습니다. 그래도 삭제하시겠습니까?",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#E74A3B',
			  cancelButtonColor: 'gray',
			  confirmButtonText: '예',
			  cancelButtonText : '아니오'
			}).then((result) => {
				if (result.isConfirmed) {
					var zone_no = $('input[name="zone_no"]').val();
					//var taa_ip = $('input[name="taa_ip"]').val();
					var taa_ip = $('#now_stat').text();
					var string_license = $('#license').val();
					var license_type = "";
					 
					if(string_license == "정적 라이선스"){
						license_type = 1	
					}else if(string_license == "동적 라이선스"){
						license_type = 2	
					}else if(string_license == "시간제한 라이선스"){
						license_type = 3	
					}else if(string_license == "N/A"){
						license_type = 99	
					}
					
					
						deleteObj = new ZoneLicenseDeleteDTO(zone_no, taa_ip, license_type);
						$.ajax({
					        url:'/zone/license',
				          	type:'delete',
				            data: deleteObj,
				            success:function(result){
				            	Swal.fire('경고',result.data.errorMessage,'error');
				            	if(result.data.errorCode == 0) {
								Swal.fire('성공','삭제 성공', 'success')
								.then((value) => {
				            		window.location.href = "/zoneLicense/listPage"
									});
				            	}
				            },
				            error:function() {
				            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
				            }
				        })// ajax
						
			  }// if
			})	//then
	});
	
	
	
	/*
	$('#tamListBox').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='tam_name']").val(selectNameValue);
		
	})
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});