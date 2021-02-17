/*
$('#verifyBtn').click(function() {
		verifyObj = new checkDTO(
				$("input[name='zone_name']").val(),
				$("input[name='revision_no']").val(),
				$("input[name='pl_license_cnt']").val(),
				$("input[name='tpl_license_cnt']").val(),
				$("input[name='limited_url']").val(),
				$("input[name='integrity_value']").val());
				console.log(verifyObj);
				//console test
	//임의로 update 기능 삽입	
		$.ajax({
            url:'/zone',
            type:'put',
            data: verifyObj,
            success:function(result){
            	swal('경고',result.data.errorMessage,'error');
            	
            	if(result.data.errorCode == 0) {
            		swal('성공','변경 완료', 'success')
					.then((value) => {
            		window.location.href = "/zone/listPage";
					});
            	}
            },
            fail:function() {
            	swal('','서버와의 통신에 실패하였습니다','error');
            }
        })
	});
//test

*/

/*	
//verify function()

$(function(){
	// verifyBtn Click Event ( For Jquery )
	$('#verifyBtn').click(function() {
		// verify check;
		// id 모두 변경해야함
		var zone_name = $('#z_name').val();
		var revision_no = $('#z_reno').val();
		var pl_count = $('#z_pl').val();
		var tpl_count = $('#z_tpl').val();
		var limited_url = $('#z_limited_url').val();
		var integrity = $('#z_integrity').val();

		/*
		var verifyobj = {};
		verifyobj.zone_name = $('#z_name').val();
		verifyobj.revision_no = $('#z_reno').val();	
		verifyobj.pl_count = $('#z_pl').val();	
		verifyobj.tpl_count= $('#z_tpl').val();	
		verifyobj.limited_url= $('#z_limited_url').val();	
		verifyobj.integrity= $('#z_integrity').val();	
		*/
//test		
		/*
		$.ajax({
            url:'/zone/verify',
            type:'get',
            data: ,
            success:function(result){
            	alert(result.data.errorMessage);
            	if(result.data.errorCode == 0) {
            		window.location.href = ""
            	}
            },
            fail:function() {
            	alert("서버와의 통신에 실패하였습니다");
            }
        });
  });
});
*/