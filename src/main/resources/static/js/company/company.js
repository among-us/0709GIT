$(function(){
	isExistGroupNAME = true; 
	allowGroupName = '';
	groupstatus = false;
	
	$('#enrollBtn').click(function() {
			if(allowGroupName != $("input[name='comp_name']").val() || allowGroupName.length < 1 ){
				Swal.fire('경고','GROUP명 중복검사 후에 등록절차를 진행해주세요','error')
				isExistTAMNAME = true;
				return 0;
			}
			
			if(isExistGroupNAME != false){
				Swal.fire('경고','해당 GROUP명을 사용하는 GROUP이 존재합니다','error')
				isExistGroupNAME = true; 
				return 0;
			}
			
			enrollObj = new EnrollDTO($("input[name='comp_name']").val(),
					$('select[name="comp_name"] option:selected').val());
				$.ajax({
		            url:'/company',
		            type:'post',
		            data: enrollObj,
		            success:function(result){
		            	Swal.fire('',result.data.errorMessage,'error')
		            	if(result.data.errorCode == 0) {
			            	Swal.fire('성공','등록 성공', 'success')
							.then((value) => {
			            			 window.location.href = "/company/enrollPage"
								});
			            	}
		            },
		            error:function() {
		            	Swal.fire('','서버와의 통신에 실패하였습니다','error')
		            }
		        })
			});
			
		// GROUP 등록 및 정보 checkBtn
		$('#checkBtn').click(function() {
				var comp_name = $("input[name='comp_name']").val();
				checkObj = new CheckDTO(comp_name);
	            var value = ValidateCompName(comp_name);
				
				201221028
				if(value == false){
		            	Swal.fire('경고','올바른 GROUP명을 입력바랍니다','error');
				}else{
					$.ajax({
			            url:'/company/check',
			            type:'get',
			            data: checkObj,
			            success:function(result){
			            	Swal.fire('경고',result.data.errorMessage,'error');
			            	if(result.data.errorCode == 0) {
			            	var a = $("input[name='comp_name']").val();
			            		Swal.fire('', a+'은(는) 사용할 수 있는 GROUP명 입니다.','success');
			            		isExistGroupNAME = false;
				        		allowGroupName = checkObj.comp_name;
			            	}
			            	else {
			            		isExistGroupNAME = true;
			            		allowGroupName = '';
			            	}
			            },
			            error:function() {
			            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
			            }
			        })
				}
		});
		
		// GROUP 정보 수정 checkBtn
		$('#updateCheckBtn').click(function(){
			var comp_no = $("input[name='comp_no']").val();
			var comp_name = $("input[name='comp_name']").val();
			var asis_comp_name = $('#asis_comp_name').val();
			var value = ValidateCompName(comp_name);
			
			updateCheckObj = new updateCheckDTO(comp_no,comp_name,asis_comp_name);
			var value = ValidateCompName(comp_name);
			
			if(value == false){
				Swal.fire('경고','올바른 GROUP명을 입력바랍니다','error');
			}else{
				$.ajax({
					url:'/company/update-check',
					type:'get',
					data: updateCheckObj,
					success:function(result){
						if(result.data.errorCode == 0){
							Swal.fire('',result.data.errorMessage,'success');
							groupstatus = true;
						}
						if(result.data.errorCode == -1){
							Swal.fire('',result.data.errorMessage,'error');
							groupstatus = false;
						}
						if(result.data.errorCode == 1){
							Swal.fire('',result.data.errorMessage,'error');
							groupstatus = false;
						}
					},
					error:function(result){
						console.log("error > "+result.data);
					}
				})
			}
		})
			
		$('#updateBtn').click(function() {
			var asis_comp_name = $("#asis_comp_name").val();
			var comp_name = $("input[name='comp_name']").val();
			
			var value = ValidateCompName(comp_name);
			
			if(comp_name == ""){
				Swal.fire('','GROUP 을 입력하세요','error');
			}else if(value == false){
				Swal.fire('경고','올바른 GROUP명을 입력바랍니다','error');
			}else if(asis_comp_name == comp_name){
				Swal.fire('경고','기존에 사용하던 GROUP명과 동일합니다.','error');
			}else if(groupstatus == false){
				Swal.fire('','올바른 중복 검사 후에 변경 바랍니다','error');
			}else {
				updateObj = new UpdateDTO(
						$("input[name='comp_no']").val(),
						$("input[name='comp_name']").val());
				
				console.log(updateObj)
				$.ajax({
		            url:'/company',
		            type:'put',
		            data: updateObj,
		            success:function(result){
		            	Swal.fire('실패',result.data.errorMessage,'error')
		            	if(result.data.errorCode == 0) {
		            		Swal.fire('성공','변경 완료', 'success')
							.then((value) => {
		            		window.location.href = "/company/enrollPage";
							});
		            	}
		            },
		            error:function() {
						Swal.fire('','서버와의 통신에 실패하였습니다','error')
		            }
		        })
			}
		});
			
			$('#deleteBtn').click(function() {
				var comp_name =  $("input[name='comp_name']").val();
				if(comp_name == ""){
					Swal.fire('','GROUP 을 입력하세요','error');
				}else {
						updateObj = new UpdateDTO( $("input[name='comp_no']").val(), $("input[name='comp_name']").val());
						console.log(updateObj)
						$.ajax({
				            url:'/company',
				            type:'delete',
				            data: updateObj,
				            success:function(result){
				           	 Swal.fire('실패',result.data.errorMessage,'error')
				            	if(result.data.errorCode == 0) {
				            		Swal.fire('성공','삭제 완료', 'success')
									.then((value) => {
				            		window.location.href = "/company/enrollPage";
									});
				            	}
				            },
				            error:function(result) {
				            	Swal.fire('','서버와의 통신에 실패하였습니다','error')
				            }
				        })
				}
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
			
});

function ValidateCompName(comp_name) {
	if (/^[가-힣|a-z|A-Z|0-9|_]+$/.test(comp_name)) {
		return true
	}else{
		return false
	}
}

function goToCompanyInfo(comp_no) {
	console.log(comp_no)
	document.writeln('<form name=companyForm action=/company/updatePage METHOD=GET>');
	document.writeln('    <input name=comp_no type=hidden>');
	document.writeln('</form>');
	document.companyForm.comp_no.value=comp_no;
	document.companyForm.submit();
}