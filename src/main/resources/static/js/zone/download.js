/*
function download(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}

// Start file download.
document.getElementById("dwn-btn").addEventListener("click", function(){
    // Generate download of hello.txt file with some content
    var text = document.getElementById("text-val").value;
    var filename = "hello.txt";
    
    download(filename, text);
}, false);

*/
//=====================================================================================================================================


var tam_no = $('#Before').attr('name')
// button onclick으로 다 따로 실행 test

function enrollbtn(){

					$('#After').children().remove();
					  $('input:checkbox[name="beforeZone"]').each(function() {
						    if(this.checked == true){ 
						      var obj = $(this).parents('label')
						      var cloneObj = obj.clone()
						      $('#After').append(cloneObj)
						      $('#After').append('<br>')
					    } 
				  }); 
}

function deletebtn(){
	$('input:checkbox[name="afterZone"]').each(function() {
					      if(this.checked == true){
					        var checked_data = $(this).val();		   	
						         $('input:checkbox[name="beforeZone"]:checked').each(function() {
								        if(checked_data == $(this).val()){
								          $(this).removeAttr("checked");
								        }
											 $('#After').children('br').remove();
						         });
					      }
				    });
				 $("#After input[type='checkbox']:checked").parent().remove();
				 
				 //$("#After input[type='checkbox']:checked").parent().parent().remove();
}

function savebtn(){
	var dtoList = [];
		
			$('#After label[name="checkBoxLabel"]').each(function() {
				  var zone_no = $(this).children().val()
				  managerZoneInfoDTO = new ManagerZoneInfoDTO(Number(tam_no), Number(zone_no))
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
				        error::function() {
				          //alert("서버와의 통신에 실패하였습니다");
				          Swal.fire('실패','서버와의 통신에 실패하였습니다','error');
				        }
				    })
				
				dtoList = [];
}

$(function(){

 /*
		  $("#m_enrollBtn").click(function() {
				  $('#After').children().remove();
					  $('input:checkbox[name="beforeZone"]').each(function() {
						    if(this.checked == true){ 
						      var obj = $(this).parents('label')
						      var cloneObj = obj.clone()
						      $('#After').append(cloneObj)
						      $('#After').append('<br>')
					    } 
				  }); 
		  }); 

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
				$("#After input[type='checkbox']:checked").parent().parent().remove();
			}); 

$("#saveBtn").click(function() {
		var dtoList = [];
		
			$('#After label[name="checkBoxLabel"]').each(function() {
				  var zone_no = $(this).children().val()
				  managerZoneInfoDTO = new ManagerZoneInfoDTO(Number(tam_no), Number(zone_no))
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
				        error::function() {
				          //alert("서버와의 통신에 실패하였습니다");
				          Swal.fire('실패','서버와의 통신에 실패하였습니다','error');
				        }
				    })
				
				dtoList = [];
});
		
		
		
});

//전역
function goToManagerZoneMatch(tam_no) {
document.writeln('<form name=managerForm action=/managerZone/enrollPage METHOD=GET>');
document.writeln('    <input name=tam_no type=hidden>');
document.writeln('</form>');
document.managerForm.tam_no.value=tam_no;
document.managerForm.submit();
}
