/**
 * 
 */


$(function(){

	asisTaa_ip = $('input[name="taa_ip"]').val()
	asisLicense_type = $("select[name='license_type'] option:selected").val()
	
	$("#updateBtn").click(function() {
		zoneLicenseDTO = new ZoneLicenseUpdateDTO(
				$("input[name='zone_no']").val(),
				asisTaa_ip, asisLicense_type,
				$("input[name='taa_ip']").val(),
				$("select[name='license_type'] option:selected").val());
		
		$.ajax({
	        url:'/zone/license',
          	type:'put',
            data: zoneLicenseDTO,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            		swal('성공','변경 완료', 'success')
					.then((value) => {
            		window.location.href = "/zoneLicense/listPage"
					});
            		asisTaa_ip = $('input[name="taa_ip"]').val()
            		asisLicense_type = $("select[name='license_type'] option:selected").val()
            		
            	}
            },
            faile:function() {
            	//alert("서버와의 통신에 실패하였습니다")
            	swal('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
	$('#deleteBtn').click(function(){
		zoneLicenseDTO = new ZoneLicenseDTO(
				$("input[name='zone_no']").val(),
				asisTaa_ip,
				asisLicense_type);
		$.ajax({
	        url:'/zone/license',
          	type:'delete',
            data: zoneLicenseDTO,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
				swal('성공','삭제 성공', 'success')
				.then((value) => {
            		window.location.href = "/zoneLicense/listPage"
					});
            	}
            },
            faile:function() {
            	//alert("서버와의 통신에 실패하였습니다")
            	swal('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
		
	});
	
	
});