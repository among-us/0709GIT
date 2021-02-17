/**
 * 
 */

$(function(){
	// enrollBtn Click Event ( For Jquery )
	$('#enrollBtn').click(function() {
		enrollObj = new EnrollDTO($("input[name='comp_name']").val(),
				$('select[name="group_name"] option:selected').val());
		
		console.log(enrollObj)
		$.ajax({
            url:'/company',
            type:'post',
            data: enrollObj,
            success:function(result){
            	alert(result.data.errorMessage);
            	if(result.data.errorCode == 0) {
            		window.location.href = "/company/listPage"
            	}
            },
            fail:function() {
            	alert("서버와의 통신에 실패하였습니다");
            }
        })
	});
	
	$('#cancelBtn').click(function() {
		window.location.href = document.location.href
	});
	
	$('select[name="comp_name"]').change(function(){
		c_comp_no = $(this).val()
		z_zone_no = $('select[name="zone_name"] option:selected').val()
		companyLicenseObj = new CompanyLicenseDTO(c_comp_no, z_zone_no)
		console.log(companyLicenseObj)
		window.location.href = "/company/licenseStatusPage?c_comp_no=" + c_comp_no + "&z_comp_no=" + z_zone_no
	})
	
	$('select[name="zone_name"]').change(function(){
		c_comp_no = $('select[name="comp_name"] option:selected').val()
		z_zone_no = $('select[name="zone_name"] option:selected').val()
		companyLicenseObj = new CompanyLicenseDTO(c_comp_no, z_zone_no)
		console.log(companyLicenseObj)
		window.location.href = "/company/licenseStatusPage?c_comp_no=" + c_comp_no + "&z_zone_no=" + z_zone_no
	})
	
	
	$('#updateBtn').click(function() {
		updateObj = new UpdateDTO(
				$("input[name='comp_no']").val(),
				$("input[name='comp_name']").val(),
				$("select[name='group_name'] option:selected").val());
		
		console.log(updateObj)
		$.ajax({
            url:'/company',
            type:'put',
            data: updateObj,
            success:function(result){
            	alert(result.data.errorMessage);
            	if(result.data.errorCode == 0) {

            	}
            },
            fail:function() {
            	alert("서버와의 통신에 실패하였습니다");
            }
        })
	});
	
	$('#deleteBtn').click(function() {
		updateObj = new UpdateDTO(
				$("input[name='comp_no']").val(),
				$("input[name='comp_name']").val(),
				$("select[name='group_name'] option:selected").val());
		
		console.log(updateObj)
		$.ajax({
            url:'/company',
            type:'delete',
            data: updateObj,
            success:function(result){
            	alert(result.data.errorMessage);
            	if(result.data.errorCode == 0) {
            		window.location.href = "/company/listPage"
            	}
            },
            fail:function() {
            	alert("서버와의 통신에 실패하였습니다");
            }
        })
	});
	
});

//전역
function goToCompanyInfo(comp_no) {
	console.log(comp_no)
	document.writeln('<form name=companyForm action=/company/updatePage METHOD=GET>');
	document.writeln('    <input name=comp_no type=hidden>');
	document.writeln('</form>');
	document.companyForm.comp_no.value=comp_no;
	document.companyForm.submit();
}
