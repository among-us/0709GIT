/**
 * 
 */
 

$(function(){
	isExistZONENAME = true; 
	allowZONEName = '';
	
	$("#enrollBtn").click(function() {
		if(allowZONEName != $("input[name='zone_name']").val() || allowZONEName.length < 1 ){
			//alert('서버명 중복검사 후에 등록절차를 진행해주세요')
			swal('경고','서버명 중복검사 후에 등록절차를 진행해주세요','error')
			isExistZONENAME = true;
			return 0;
		}
		
		if(isExistZONENAME != false){
			//alert('해당 서버명을 사용하는 서버가 존재합니다')
			swal('경고','해당 서버명을 사용하는 서버가 존재합니다','error')
			isExistZONENAME = true; 
			return 0;
		}
		
		enrollObj = new EnrollDTO($("input[name='zone_name']").val(),
				$("input[name='zone_info']").val(),
				$("input[name='pl_license_cnt']").val(),
				$("input[name='tpl_license_cnt']").val(),
				//$("select[name='pl_license_cnt'] option:selected").val(),
				//$("select[name='tpl_license_cnt'] option:selected").val(),
				$("select[name='session_time'] option:selected").val(),
				$("select[name='comp_no'] option:selected").val(),
				$("input[name='integrity_value']").val());
		
		$.ajax({
            url:'/zone',
            type:'post',
            data: enrollObj,
            
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            	swal('성공','등록 성공', 'success')
				.then((value) => {
            		window.location.href = "/zone/listPage";
					});
            	}
            },
            faile:function() {
            	//alert("서버와의 통신에 실패하였습니다")
            	swal('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
	$('#updateBtn').click(function() {
		updateObj = new UpdateDTO($("input[name='zone_no']").val(),
				$("input[name='zone_name']").val(),
				$("input[name='zone_info']").val(),
				$("select[name='comp_name'] option:selected").val(),
				$("input[name='pl_license_cnt']").val(),
				$("input[name='tpl_license_cnt']").val(),
				//$("select[name='pl_license_cnt'] option:selected").val(),
				//$("select[name='tpl_license_cnt'] option:selected").val(),
				$("select[name='session_time'] option:selected").val(),
				$("input[name='integrity_value']").val(),
				$("select[name='exist'] option:selected").val());
		
		$.ajax({
		
            url:'/zone',
            type:'put',
            data: updateObj,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            		swal('성공','변경 완료', 'success')
					.then((value) => {
            		window.location.href = "/zone/listPage";
					});
            	}
            },
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	swal('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
	
	// checkBTN Click Event ( For Jquery ) 바꿔라잉
	$('#checkBtn').click(function() {
		checkObj = new CheckDTO($("input[name='zone_name']").val());
		
		$.ajax({
            url:'/zone',
            type:'get',
            data: checkObj,
            success:function(result){
            	//alert(result.data.errorMessage);
            	swal('경고',result.data.errorMessage,'error');
            	if(result.data.errorCode == 0) {
            	swal('','사용할 수 있는 TAM명 입니다.','success');
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
            fail:function() {
            	//alert("서버와의 통신에 실패하였습니다");
            	swal('','서버와의 통신에 실패하였습니다','error');
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
            fail:function() {
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



$("#verifyBtn").click(function() {
		verifyObj = new VerifyDTO(
				$("input[name='zone_name']").val(),
				$("input[name='revision_no']").val(),
				$("input[name='pl_license_cnt']").val(),
				$("input[name='tpl_license_cnt']").val(),
				$("input[name='limited_url']").val(),
				$("input[name='integrity_value']").val());
				
				/* function 에는 integrity 라고 선언되있고
				 input name은 integrity_value 라고 되있음.
				*/
      		  	console.log(verifyObj);
        $.ajax({
          url:'/zone/verify',
          type:'GET',
          data: verifyObj,
          success:function(bRet){
            if(bRet.data.errorCode == 0) {
 	           	swal('','검증 성공','success');
            }else{
 	           	swal('','검증 실패','error');
            }
          },
          fail:function() {
            swal('','서버와의 통신에 실패하였습니다','error');
          }
      })
    });
    
    //
$("#verifyBtn").click(function() {
		verifyObj = new VerifyDTO(
				$("input[name='zone_name']").val(),
				$("input[name='revision_no']").val(),
				$("input[name='pl_license_cnt']").val(),
				$("input[name='tpl_license_cnt']").val(),
				$("input[name='limited_url']").val(),
				$("input[name='integrity_value']").val());
				
				/* function 에는 integrity 라고 선언되있고
				 input name은 integrity_value 라고 되있음.
				*/
      		  	console.log(verifyObj);
        $.ajax({
          url:'/zone/verify',
          type:'GET',
          data: verifyObj,
          success:function(bRet){
            if(bRet.data.errorCode == 0) {
 	           	swal('','검증 성공','success');
            }else{
 	           	swal('','검증 실패','error');
            }
          },
          fail:function() {
            swal('','서버와의 통신에 실패하였습니다','error');
          }
      })
    });
	//
	
	// 기능 table / sort / search 
	
	table = $('#tables').DataTable( {
		info:false,
		responsive: false,
	    order: [ 0, 'asc' ]
    });
	
	
$('#myTable tbody tr').click(function() {
    $(this).addClass('bg-primary').siblings().removeClass('bg-primary');
    $(this).addClass('text-white').siblings().removeClass('text-white');
});
	
// 라이선스 요청 정보 list 클릭시 modal창에 들어갈 값 
$("#myTable tbody").on("click", "tr", function(){
  var name = $(this).find("td:eq(1)").text();
  var re_no = $(this).find("td:eq(4)").text();
  var pl_cnt= $(this).find("td:eq(5)").text();
  var tpl_cnt= $(this).find("td:eq(6)").text();
  var z_limited_url= $(this).find("td:eq(9)").text();
  
   $('#integrityInfo').on('show.bs.modal', function(){
      $('#zone_name')[0].innerText = name;
      $('#revision_no')[0].innerText = re_no;
      $('#zone_pl_count')[0].innerText = pl_cnt;
      $('#zone_tpl_count')[0].innerText = tpl_cnt;
      $('#zone_limited_url')[0].innerText = z_limited_url;
  });
      $('#integrityInfo').modal('show');
});
  

});
	//end

//search
function myFunction() {
		var input, filter, table, tr, td, i, txtValue;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("myTable");
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
	}






//전역
function goToZoneUpdatePage(zone_no) {
	document.writeln('<form name=zoneForm action=/zone/updatePage METHOD=GET>');
	document.writeln('    <input name=zone_no type=hidden>');
	document.writeln('</form>');
	document.zoneForm.zone_no.value=zone_no;
	document.zoneForm.submit();
}
