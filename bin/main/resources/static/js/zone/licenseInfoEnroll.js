  $(function(){
   	$('#license_yes').click(function(){
        	var zone_name = $('#zone_name').text();
        	var revision_no = $('#re_no').text();
        	var pl_license_cnt = $('#pl_cnt').text();
        	var tpl_license_cnt = $('#tpl_cnt').text();
        	var limited_url = $('#limited_url').text();
        	var integrity_value = $('#integrity').text();

			var pl = $('#asispl').text();
			var tpl = $('#asistpl').text();
			
			var tobepl = parseInt(pl_license_cnt);
			var tobetpl = parseInt(tpl_license_cnt);
			var asispl = parseInt(pl);
			var asistpl = parseInt(tpl);
			
			if(asispl > tobepl || asistpl > tobetpl ){
				Swal.fire('','  등록 갯수가 허용 갯수보다 적을 경우 현재의 허용 갯수에 맞게 조정해야합니다','error');
			}else{
	        	yesObj = new YesDTO(zone_name,revision_no,pl_license_cnt,tpl_license_cnt,limited_url,integrity_value);
		
				$.ajax({
		            url:'/zone/license-enroll',
		          	type:'put',
		            data: yesObj,
		            success:function(result){
		            console.log(result.data.errorMessage);
						Swal.fire('','요청하신 라이선스 정보가 등록 완료되었습니다','success').then((value) => {
			            		 		window.location.href = "/zoneLicense/LicenseAllowInfoPage";
						});
		            },
		            error:function(result) {
		            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
		            }
		        })
	    	}
	 })
	 	
	 
	 
	 	// == TIMELICENSE ENROLL / UPDATE == 
	 	$('#time_license_yes').click(function(){
        	var zone_name = $('#t_z_name').text();
          	var allowed_ip = $('#t_ip').text();
        	var cnt = $('#t_cnt').text().trim();
        	var allowed_cnt = parseInt(cnt);
        	var limited_period = $('#t_limit').text();
        	var icv = $('#tm_integrity').text();
        	
	        timeLicenseObj = new TimeInfoDTO(zone_name,allowed_ip,allowed_cnt,limited_period,icv);
	        
				$.ajax({
		            url:'/license/timelimit',
		          	type:'post',							
		            data: timeLicenseObj,
		            success:function(result){
						Swal.fire('성공','데이터베이스에 해당 정보를 입력했습니다','success').then((value) => {
			            		 		window.location.href = "/zoneLicense/LicenseAllowInfoPage";
									});
		            },	
		            error:function(result) {
		            	Swal.fire('경고',result.data.errorMessage,'error');
		            }
		        })
		        
		        $.ajax({
		            url:'/license/timelimit-history',
		          	type:'post',
		            data: timeLicenseObj,
		            success:function(){
		            },
		            error:function() {
		            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
		            }
		        })
		        
	 	})
	 	
	 	
	 	/*
	 	// == TIMELICENSE HISTORY == 
	 	$('#time_license_yes').click(function(){
        	var zone_name = $('#t_z_name').text();
	        var allowed_ip = $('#t_ip').text();
        	var cnt = $('#t_cnt').text().trim();
        	var allowed_cnt = parseInt(cnt);
        	var limited_period = $('#t_limit').text();
        	var icv = $('#tm_integrity').text();
        	
        	console.log("test"+zone_name, allowed_ip, allowed_cnt, limited_period,icv);
        	
	        timelicenseHistoryObj = new TimeHistoryDTO(zone_name,allowed_ip,allowed_cnt,limited_period,icv);
	        
				$.ajax({
		            url:'/license/timelimit-history',
		          	type:'post',
		            data: timelicenseHistoryObj,
		            success:function(){
						Swal.fire('성공','데이터베이스에 해당 정보를 입력했습니다','success').then((value) => {
			            		 		window.location.href = "/zoneLicense/LicenseAllowInfoPage";
									});
		            },
		            error:function() {
		            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
		            }
		        })
	 	})
	 	*/
});

function licenseopenTextFile() {
		  var input = document.createElement("input");
		  input.type = "file";
		  input.accept = ".*"; 
		  input.onchange = function (event) {
		      processFile(event.target.files[0]);
		  };
		  input.click(); 
}

function processFile(file) {	// file read 읽는곳 
  var reader = new FileReader();
  reader.onload = function () {
		var value = reader.result
		console.log(value);
		
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
			            	Swal.fire('','올바른 파일을 등록해주세요','error');
			            }
		            },
			            error:function(result) {
		            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
		            }
	            })
  };
  reader.readAsText(file,  "UTF-8")
}
      
// ===================== TIME LICENSE ======================================================================================================================================
      
function timeopenTextFile() {
      
	console.log('timeopenTextFile()');
	      
	  var input = document.createElement("input");
	  input.type = "file";
	  input.accept = ".*"; 
	  input.onchange = function (event) {
	      timeprocessFile(event.target.files[0]);
	  };
	  input.click();
}

function timeprocessFile(file) {
	var reader = new FileReader();
	reader.onload = function () {
		var value = reader.result;
		parseObj = new ParseDTO(value);
			$.ajax({
				url:'/license/parse',
				type:'get',
				data: parseObj,
				success:function(result){
					if(result.data == "length null"){
						Swal.fire('','올바른 파일을 등록해주세요','error')
					}else if(result.data.errorCode == -1){
						Swal.fire('',result.data.errorMessage,'error')
					}else{
						$('#t_z_name').text(result.data[0]);
						$('#t_ip').text(result.data[1]);
						$('#t_cnt').text(result.data[2]);
						$('#t_limit').text(result.data[3]);
						$('#tm_integrity').text(result.data[4]);
						$('#timelicenseModal').modal('show');
					}// else end
				},
				error:function(result) {
					Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
				}
			})
		};
	reader.readAsText(file,"UTF-8")
}
  
function Exist(param){
	if(param.data.errorCode == -1){
		return 0;
	}else {
		return 1;
	}
}
  