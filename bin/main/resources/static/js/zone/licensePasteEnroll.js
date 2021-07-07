$(function(){
	$('#lenrollBtn').click(function(){
			
			var value = $('input[name="license_enroll"]').val();

			valueDTO = new ValueDTO(value);
	          
	          $.ajax({
		            url:'/zone/verify-enroll',
		          	type:'get',
		            data: valueDTO,
		            success:function(result){
		            var a = Exist(result);
		            
		            if(a == 1){
			            	$('#zone_name').text(result.data[0]);
			            	$('#re_no').text(result.data[1]);
			            	$('#pl_cnt').text(result.data[2]);
			            	$('#asispl').text(result.data[3]);
			            	$('#tpl_cnt').text(result.data[4]);
			            	$('#asistpl').text(result.data[5]);
			            	$('#limited_url').text(result.data[6]);
			            	$('#integrity').text(result.data[7]);
			            	$('#licenseEnrollModal').modal('show');
		            }else{
		            	Swal.fire('','올바르지 않은 요청값 형식입니다.','error');
		            }
	            },
		            error:function(result) {
	            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
	            }
            }) // ajax
	})
	
	
	$('#tlenrollBtn').click(function(){
		var value = $('input[name="timelicense_enroll"]').val();
		
		   parseObj = new ParseDTO(value);
		   
				   		$.ajax({
				            url:'/license/parse',
				          	type:'get',
				            data: parseObj,
				            success:function(parse){
				            if(parse.data == "length null"){
				            	Swal.fire('','올바르지 않은 요청값 형식입니다.','error')
				            }else if(parse.data.errorCode == -1){
				            	Swal.fire('',parse.data.errorMessage,'error')
				            }else{
						          $('#t_z_name').text(parse.data[0]);
						          $('#t_ip').text(parse.data[1]);
						          $('#t_cnt').text(parse.data[2]);
						          $('#t_limit').text(parse.data[3]);
						          $('#tm_integrity').text(parse.data[4]);
									$('#timelicenseModal').modal('show');
						     }// else end
						            },
						            error:function(result) {
						            console.log(result);
						            	console.log(result.data.errorCode);
						            	console.log(result.data.errorMessage);
						            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
						            }
				        })
				        
	})
	
}) // function end 


// == 외부 function == 

function Exist(param){
  		if(param.data.errorCode == -1){
  			return 0;
  		}else {
  			return 1;
  		}
}