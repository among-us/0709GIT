

/**
 * 
 */

$(function(){

$('[data-toggle="tooltip"]').tooltip();

	isExistZONENAME = true; 
	allowZONEName = '';
	
	$("#enrollBtn").click(function() {
		if(allowZONEName != $("input[name='zone_name']").val() || allowZONEName.length < 1 ){
			Swal.fire('경고','서버명 중복검사 후에 등록절차를 진행해주세요','error')
			isExistZONENAME = true;
			return 0;
		}
		
		if(isExistZONENAME != false){
			Swal.fire('경고','해당 서버명을 사용하는 서버가 존재합니다','error')
			isExistZONENAME = true; 
			return 0;
		}
		var zone_name = $("input[name='zone_name']").val();
		var zone_info = $("input[name='zone_info']").val();
		var session_time = $("select[name='session_time'] option:selected").val();
				
		if(zone_name == "" || zone_info == "" || session_time == ""){
			Swal.fire('','필수항목을 입력해주세요','error');
		}else{
			enrollObj = new EnrollDTO(zone_name, zone_info, session_time);
					$.ajax({
			            url:'/zone',
			            type:'post',
			            data: enrollObj,
			            success:function(result){
			            	Swal.fire('경고',result.data.errorMessage,'error');
			            	if(result.data.errorCode == 0) {
			            	Swal.fire('성공','등록 성공', 'success')
							.then((value) => {
			            		window.location.href = "/zone/listPage";
							});
			            	}
			            },
			            error:function() {
			            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			            }
			        })
	 	}
	});
	
	$('#updateBtn').click(function() {
		updateObj = new UpdateDTO(
				$("input[name='zone_no']").val(),
				$("input[name='zone_name']").val(),
				$("input[name='zone_info']").val(),
				$("input[name='pl_license_cnt']").val(),
				$("input[name='tpl_license_cnt']").val(),
				$("select[name='session_time'] option:selected").val(),
				$("select[name='exist'] option:selected").val());
		$.ajax({
		
            url:'/zone',
            type:'put',
            data: updateObj,
            success:function(result){
            	Swal.fire('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            		Swal.fire('성공','변경 완료', 'success')
					.then((value) => {
            		window.location.href = "/zone/listPage";
					});
            	}
            },
            error:function(result) {
            console.log(result);
            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
	$('#checkBtn').click(function() {
		checkObj = new CheckDTO($("input[name='zone_name']").val());
		var z_name = $('input[name="zone_name"]').val();
		
		$.ajax({
            url:'/zone',
            type:'get',
            data: checkObj,
            success:function(result){
            	Swal.fire('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            	
            	Swal.fire('',z_name+'은(는) 사용할 수 있는 PROJECT명 입니다.','success');
            		isExistZONENAME = false;
            		allowZONEName = checkObj.zone_name;
	        		console.log(allowZONEName)
	        		console.log(checkObj.zone_name)
            	}
            	else {
            		isExistZONENAME = true;
            		allowZONEName = '';
            	}
            },
            error:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
/*	$('#deleteBtn').click(function() {
		deleteObj = new DeleteDTO($("input[name='zone_no']").val());
		
		$.ajax({
            url:'/zone',
            type:'delete',
            data: deleteObj,
            success:function(result){
            	alert(result.data.errorMessage);
            	if(result.data.errorCode == 0) {
            		window.location.href = "/zone/listPage";
            	}
            },
            error:function() {
            	alert("서버와의 통신에 실패하였습니다");
            }
        })
	});*/


$('#hintModal').on('click', function(){
  $('#integrityInfo').on('show.bs.modal', function(){
      $('#zone_name')[0].innerText = $('#z_name').val(),
      $('#revision_no')[0].innerText = $('#z_reno').val(),
      $('#zone_pl_count')[0].innerText = $('#z_pl').val(),
      $('#zone_tpl_count')[0].innerText = $('#z_tpl').val(),
      $('#limited_url')[0].innerText = $('#z_limited_url').val();
  });
      $('#integrityInfo').modal('show');
});

    
$('#test').click(function(){

	verifyEnrollObj = 1;
	$.ajax({
          url:'/zone/verify-enroll',
          type:'GET',
          data: verifyEnrollObj,
          success:function(a){
          	console.log('success');
          	console.log(a);
          },
          error:function() {
            Swal.fire('','서버와의 통신에 실패하였습니다','error');
          }
     	})
});


	


});

/*
//search
function myFunction() {
		var input, filter, table, tr, td, i, txtValue;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("tables");
		tr = table.getElementsByTagName("tr");
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[1];
			if (td) {
				txtValue = td.textContent || td.innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}	
*/


//전역
function goToZoneUpdatePage(zone_no) {
	document.writeln('<form name=zoneForm action=/zone/updatePage METHOD=GET>');
	document.writeln('    <input name=zone_no type=hidden>');
	document.writeln('</form>');
	document.zoneForm.zone_no.value=zone_no;
	document.zoneForm.submit();
}

function CheckNum(num){
	var regexp = /^[0-9]*$/
	
	if( !regexp.test(num) ) {
		return false;
	}else{
		return true;
    }
}











