/**
 * 
 */

$(function(){
	
	selectNameValue = ''
	selectNameNo = ''
	
	$('#zoneListBox').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='zone_name']").val(selectNameValue);
		
	})

	$('#managerListBox').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='tam_name']").val(selectNameValue);
		
	})
	
	$("#enrollBtn").click(function() {
		zoneLicenseDTO = new ZoneLicenseDTO(selectNameNo,$("input[name='taa_ip']").val(), $("select[name='license_type'] option:selected").val());
		
		console.log(zoneLicenseDTO)
		$.ajax({
	        url:'/zone/license',
          	type:'post',
            data: zoneLicenseDTO,
            success:function(result){
            	console.log(result.data.errorMessage)
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) { 
            		swal('성공','등록 성공', 'success')
            		.then((value) => {
	            	window.location.href = "/zoneLicense/listPage"
					});
            		
            	}
            },
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다")
            	swal('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
});
//전역
function goToZoneLicenseInfo(zone_no, taa_ip, license_type) {
	document.writeln('<form name=zoneLicenseForm action=/zoneLicense/updatePage METHOD=GET>');
	document.writeln('    <input name=zone_no type=hidden>');
	document.writeln('    <input name=taa_ip type=hidden>');
	document.writeln('    <input name=license_type type=hidden>');
	document.writeln('</form>');
	document.zoneLicenseForm.zone_no.value=zone_no;
	document.zoneLicenseForm.taa_ip.value=taa_ip;
	document.zoneLicenseForm.license_type.value=license_type;
	document.zoneLicenseForm.submit();
}