
  $(function(){
   
   
   	$('#time_license_yes').click(function(){
        	var zone_name = $('#t_z_name').text();
        	var allowed_ip = $('#t_ip').text();
        	var allowed_cnt = $('#t_cnt').text();
        	var limited_period = $('#t_limit').text();
        	var icv = $('#t_integrity').text();
        	
	        timeLicenseObj = new TimeInfoDTO(zone_name,allowed_ip,allowed_cnt,limited_period,icv);
			//console.log(timeLicenseObj);
		
				$.ajax({
		            url:'/license/timelimit',
		          	type:'post',
		            data: timeLicenseObj,
		            success:function(){
						Swal.fire('성공','데이터베이스에 해당 정보를 등록했습니다','success').then((value) => {
			            		 		window.location.href = "/main";
									});
		            },
		            error:function() {
		            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
		            }
		        })
	 	})
	 	
	 	
	 	
});
// 설명 

function timeopenTextFile() {
  var input = document.createElement("input");
  input.type = "file";
  input.accept = ".txt"; 
  input.onchange = function (event) {
      timeprocessFile(event.target.files[0]);
  };
  input.click();
}

function timeprocessFile(file) {
  var reader = new FileReader();
  reader.onload = function () {
  
		var value = reader.result;
          //console.log(value);
           parseObj = new ParseDTO(value);
			   		$.ajax({
			            url:'/license/parse',
			          	type:'get',
			            data: parseObj,
			            success:function(parse){
			            alert('success');
			            //console.log(parse);
					            $('#t_z_name').text(parse[0]);
				            	$('#t_ip').text(parse[1]);
				            	$('#t_cnt').text(parse[2]);
				            	$('#t_limit').text(parse[3]);
				            	$('#t_integrity').text(parse[4]);
			            },
			            error:function() {
			            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			            }
			        })
			        
  };
  reader.readAsText(file,  "UTF-8")
    $('#myModal2').modal('show');
}