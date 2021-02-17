/**
 * 
 */

$(function(){
var tam_no = $('#Before').attr('name')

$("#enrollBtn").click(function() {
$('#After').children().remove();
$('input:checkbox[name="beforeZone"]').each(function() {
  if(this.checked == true){ 
    var obj = $(this).parents('label')
    var cloneObj = obj.clone()
    $('#After').append(cloneObj)
    $('#After').append('</br>')
    
   // $('#After input').attr('disabled', 'disabled')
  } // if
}); // click function end
}); //function 

/*
$("#deleteBtn").click(function () {
  $('input:checkbox[name="afterZone"]').each(function () {
      if (this.checked == false) {
          var obj = $(this).parents('label')
    var cloneObj = obj.clone()
    $('#After').remove(cloneObj);
    //$('#After').append('</br>')
          /*체크되어있는 checkbox를 포함한 label들이 현재 obj에 대입되어있는 상황
             체크되어있는 label을 삭제. label -> obj 
             remove.obj
           #After에서 remove*/
          /*
      } // if
  }); // click function end
}); //function
*/


$("#deleteBtn").click(function () {
	$("#After input[type='checkbox']:checked").parent().remove();
	$("#Before input[name='beforeZone']").prop("checked", false);
}); //function


 






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
          //alert(result.data.errorMessage);
          swal('실패', result.data.errorMessage,'error');
          if(result.data.errorCode == 0) {
          swal('성공','등록 성공', 'success')
    .then((value) => {
            window.location.href = "/managerZone/listPage";
      });
          }
          
        },
        fail:function() {
          //alert("서버와의 통신에 실패하였습니다");
          swal('실패','서버와의 통신에 실패하였습니다','error');
        }
    })

dtoList = [];
});



/*	
table = $('#tables').DataTable( {
info:false,
responsive: false,
  order: [ 0, 'asc' ]
});
*/
});

//전역
function goToManagerZoneMatch(tam_no) {
document.writeln('<form name=managerForm action=/managerZone/enrollPage METHOD=GET>');
document.writeln('    <input name=tam_no type=hidden>');
document.writeln('</form>');
document.managerForm.tam_no.value=tam_no;
document.managerForm.submit();
}