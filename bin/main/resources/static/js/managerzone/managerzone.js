
$(function(){
var tam_no = $('#Before').attr('name');
numberObj = new numberDTO(tam_no);

/*

	  $("#m_enrollBtn").click(function() {
			  $('#After').children().remove();
				  $('input:checkbox[name="beforeZone"]').each(function() {
					    if(this.checked == true){
					      var obj = $(this).parents('label')
					      var cloneObj = obj.clone()
					      $('#After').append(cloneObj);
					      $('#After').append('<br>');
				    }
			  });
	  });
	  */
/*
		$("#m_deleteBtn").click(function () {
			    $('input:checkbox[name="afterZone"]').each(function() {
				      if(this.checked == true){
				        var checked_data = $(this).val();
					         $('input:checkbox[name="beforeZone"]:checked').each(function() {
							        if(checked_data == $(this).val()){
							          $(this).removeAttr("checked");
							        }
					         });
				      }
			    });
			$("#After input[type='checkbox']:checked").parent().remove();
		});
		*/
		
		
		

/*
$("#saveBtn").click(function() {
		
		var dtoList = [];

		$('#After label[name="checkBoxLabel"]').each(function() {
			  var zone_no = $(this).children().val();
			  managerZoneInfoDTO = new ManagerZoneInfoDTO(Number(tam_no), Number(zone_no))
			  
			  console.log(managerZoneInfoDTO);
			  
			  dtoList.push(managerZoneInfoDTO)
		});


	
	$.ajax({
			        url:'/managerZone',
			        type:'post',
			        contentType: 'application/json',
			        dataType: 'json',
			        data: JSON.stringify(dtoList),
			        success:function(result){
			          Swal.fire('실패', result.data.errorMessage,'error');
			          if(result.data.errorCode == 0) {
			          Swal.fire('성공','등록 성공', 'success')
			    		.then((value) => {
			           		window.location.href = "/managerZone/listPage";
			      });
			          }

			        },
			        error:function() {
			          Swal.fire('실패','서버와의 통신에 실패하였습니다','error');
			        }
			    })

			dtoList = [];


});
*/

//0623
	$("#saveBtn").click(function() {
		
		// 기존 each 소스	//$('#After label[name="aftercheckBoxLabel"]').each(function() {
		//var zone_no = $(this).children().val();
		
		var dtoList = [];
		$($('#After').children().children()).each(function() {
			var zone_no = $(this).val();
			if(zone_no == ""){
				zone_no = $(this).children().val();
			}
			
			managerZoneInfoDTO = new ManagerZoneInfoDTO(Number(tam_no), zone_no)
			dtoList.push(managerZoneInfoDTO);
			console.log("dtoList > "+JSON.stringify(dtoList));
		});

		var length = dtoList.length;
		console.log('length > ' +length);
		
		exceptionObj = new exceptDTO(tam_no);
		console.log("exceptionObj > "+JSON.stringify(exceptionObj));
		
		if(dtoList.length == 0){
			$.ajax({
				url:'/managerZone/exception',
				type:'delete',
				data : exceptionObj,
				success:function(result){
					if(result.data.errorCode == 0){
						Swal.fire('',result.data.errorMessage,'success').then((result)=>{
							window.location.href = "/managerZone/listPage";
						})
					}else{
						Swal.fire('',result.data.errorMessage,'error');
					}
					
				},
				error:function(result){
					console.log(result.data);
				}
			})	// ajax		
		}else{
		// 정상 flow 
			$.ajax({
				url:'/managerZone',
				type:'post',
				contentType:'application/json',
				dataType:'json',
				data : JSON.stringify(dtoList), exceptionObj,
				success:function(result){
				if(result.data.errorCode == 0){
						Swal.fire('',result.data.errorMessage,'success').then((result)=>{
							window.location.href = "/managerZone/listPage";
						})
				}
				if(result.data.errorCode == -1){
						Swal.fire('',result.data.errorMessage,'error');
				}
				},
				error:function(result){
					alert(result.data.errorMessage);
				}
			})	// ajax
			
			/*
		}
		*/
		}
			dtoList = [];
			
});// saveBtn click function

});

//전역
function goToManagerZoneMatch(tam_no) {
	document.writeln('<form name=managerForm action=/managerZone/enrollPage METHOD=GET>');
	document.writeln('    <input name=tam_no type=hidden>');
	document.writeln('</form>');
	document.managerForm.tam_no.value=tam_no;
	document.managerForm.submit();
}

function m_enroll(){
$('#After').children().remove();

		$('input:checkbox[name="beforeZone"]').each(function() {
		    if(this.checked == true){
			      var obj = $(this).parents('label')
			      
			      
			      var cloneObj = obj.clone()
			      cloneObj.class = 'afterZone';
			      $('#After').append(cloneObj);
			      $('#After').append('<br>');
	    	}
		})
}
		
function m_delete(){
	$('input:checkbox[name="afterZone"]').each(function() { 
			if(this.checked == true){ 
				var checked_data = $(this).val();
	
			$('input:checkbox[name="beforeZone"]:checked').each(function() {
				var before = $(this).val();
					if(checked_data == $(this).val()){
						$(this).removeAttr("checked");
					}
			}); 
	}
});	
		$("#After input[type='checkbox']:checked").parent().remove();
		//$("#After input[type='checkbox']:checked").parent().parent().remove();
}	// m_delete function 



