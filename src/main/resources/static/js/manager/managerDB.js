$(function(){

  $('#tamListBox').change(function(){
      selectNameNo = $(this).val();
      selectNameValue = $(this).children("option:selected").text()
      
      $("input[name='tam_name']").val(selectNameValue);
      
  })
  $('#dbTypeBox').change(function(){
      selectNameNo = $(this).val();
      selectNameValue = $(this).children("option:selected").text()
      
      $("input[name='db_type_no']").val(selectNameValue);
  })
  
  // checkBtn script
  $('#checkBtn').click(function() {
			checkObj = new DBCheckDTO($("input[name='db_svc_name']").val());
			var db_name = $("input[name='db_svc_name']").val();
			
			$.ajax({
		        url:'/manager/db',
		        type:'get',
		        data: checkObj,
		        success:function(result){
		        	Swal.fire('경고',result.data.errorMessage,'error');
		        	if(result.data.errorCode == 0) {
		        		Swal.fire('',db_name+ '은(는) 사용할 수 있는 서비스명 입니다.','success');
		        		isExistSVCNAME = false;
		        		allowSVCName = checkObj.db_svc_name;
		        		console.log(allowSVCName)
		        	}
		        	else {
		        		isExistSVCNAME = true;
		        		allowSVCName = '';
		        	}
		        },
		        error:function() {
		        	Swal.fire('','서버와의 통신에 실패하였습니다','error');
		        }
		    })
		});
	});
  