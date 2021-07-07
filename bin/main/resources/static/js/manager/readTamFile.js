 $(function(){
 
 /* //document.getElementById('file1').addEventListener('change', readFile1, false);
   $('.btn.btn-primary.verifyBtn').click(function () {
  				document.getElementById('file1').addEventListener('change', readFile1, false);
				readFile2();
				
					var tam_name = $("#name").val();
					var tam_ip = $("#tamip").val();
					var comm_type = $("#commtype").val();
					var db_type = $("#dbtype").val();
					var db_addr = $("#dbaddr").val();
					var db_port = $("#dbport").val();
					var db_id = $("#dbid").val();
					var db_pwd = $("#dbpwd").val();
					var db_name = $("#dbname").val();
					var hmac = $("#hmac").val();
					
			        fileInfoDTO = new FileInfoDTO(tam_name, tam_ip, comm_type,db_type,db_addr,db_port, db_id, db_pwd, db_name,hmac);
			        	//////console.log(JSON.stringify(fileInfoDTO));
			   		$.ajax({
			            url:'/manager/verify-content',
			          	type:'get',
			            data: fileInfoDTO,
			            success:function(value){
			           	 ////console.log('ajax success');
					            	$('#name').text(value[0]);
					            	$('#tamip').text(value[1]);
					            	$('#commtype').text(value[2]);
					            	$('#dbtype').text(value[3]);
					            	$('#dbaddr').text(value[4]);
					            	$('#dbport').text(value[5]);
					            	$('#dbid').text(value[6]);
					            	$('#dbpwd').text(value[7]);
					            	$('#dbname').text(value[8]);
					            	var hmac = $("#hmac").val();
								if( value[9] == $('#hmac').text()){
									$("#verifyResult").text("검증 성공");					            	
								}else{
					            	$('.TaaResult').addClass("fail");
					            	$("#verifyResult").text("검증 실패");
					            }
			            },
			            error:function() {
			            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			            }
			        })
			        
			        
			        
			        
			   });
			   */
});

function openTextFile() {
  var input = document.createElement("input");
  input.type = "file";
  input.accept = ".conf"; 
  input.onchange = function (event) {
      processFile(event.target.files[0]);
  };
  input.click();
}

function processFile(file) {
  var reader = new FileReader();
  reader.onload = function () {
//  	    output.innerText = reader.result;
		var full = reader.result.split("\n");
		//console.log(full);
		//==========================================================
			var a = full[4];
            var tam_name = a.split("=");
            var name = tam_name[1];
            $("#name").val(name);
            console.log(name);
			
				if(name == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            var z = full[5];
            var tam_ip = z.split("=");
            var tamip = tam_ip[1];
            $("#tamip").val(tamip);
            console.log(tamip);
				if(tamip == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return;
				}
				

            var b = full[14];
            var commtype = b.split("=");
            var comm_type = commtype[1];
            $("#commtype").val(comm_type);
            console.log(comm_type);
				if(comm_type == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            
            var c = full[22];
            var port = c.split("=");
            var db_type = port[1];
            $("#dbtype").val(db_type);
            console.log(db_type);
            	if(db_type == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            var d = full[24];
            var daddr = d.substring(8).trim();
            $("#dbaddr").val(daddr);
            console.log(daddr);
            	if(daddr == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            var e = full[26];
            var dport = e.substring(8).trim();
            $("#dbport").val(dport);
            console.log(dport);
            	if(dport == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            var f = full[28];
            var did = f.substring(6).trim();
            $("#dbid").val(did);
            console.log(did);
            	if(did == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            var g = full[30];
           	var dpwd = g.substring(12).trim();
            $("#dbpwd").val(dpwd);
            console.log(dpwd);
	if(dpwd == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            var h = full[32];
          	var dname = h.substring(8).trim();
            $("#dbname").val(dname);
            console.log(dname);
            	if(dname == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
            var i = full[35];
            var dhmac = i.substring(5).trim();
            $("#hmac").val(dhmac);		
            console.log(dhmac);
            	if(dhmac == undefined ){
					Swal.fire('파일 오류','올바른 파일 형식인지 확인해주세요','error')
					return ;
				}
				
		// 현재 문서 파싱 끝난상황 ==========================================================
		// ajax 태워서 다시 return 받아야됨 
		var tam_name = $("#name").val();
					var tam_ip = $("#tamip").val();
					var comm_type = $("#commtype").val();
					var db_type = $("#dbtype").val();
					var db_addr = $("#dbaddr").val();
					var db_port = $("#dbport").val();
					var db_id = $("#dbid").val();
					var db_pwd = $("#dbpwd").val();
					var db_name = $("#dbname").val();
					var hmac = $("#hmac").val();
					
			        fileInfoDTO = new FileInfoDTO(tam_name, tam_ip, comm_type,db_type,db_addr,db_port, db_id, db_pwd, db_name,hmac);
			        
			   		$.ajax({
			            url:'/manager/verify-content',
			          	type:'get',
			            data: fileInfoDTO,
			            success:function(value){
			           	 console.log('ajax success');
					            	$('#name').text(value.data[0]);
					            	$('#tamip').text(value.data[1]);
					            	$('#commtype').text(value.data[2]);
					            	$('#dbtype').text(value.data[3]);
					            	$('#dbaddr').text(value.data[4]);
					            	$('#dbport').text(value.data[5]);
					            	$('#dbid').text(value.data[6]);
					            	$('#dbpwd').text(value.data[7]);
					            	$('#dbname').text(value.data[8]);
					            	$('#hmac').text(value.data[9]);
					            	var hmac = $("#hmac").val();
					            	if(value.data.errorCode == -1){	// 암호화된 부분을 수정한것
					            		Swal.fire('오류 발생','파일을 임의로 수정할시 오류가 발생할 수 있습니다','error')
					            	}else if( value.data[9] ==  value.data[10]){
										$("#verifyResult").text("검증 성공");					            	
										$('#tamverifyModal').modal('show');
									}else{	// hmac 값이 서로안맞는것 / hmac값을 임의로 수정한것
						            	$('.TaaResult').addClass("fail");
						            	$("#verifyResult").text("검증 실패");
					            		Swal.fire('오류 발생','파일을 임의로 수정할시 오류가 발생할 수 있습니다','error')
					            }
			            },
			            error:function() {
			            console.log('error');
			            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			            }
			        })
  };
  reader.readAsText(file,  "UTF-8")
  $('#myModal').modal('show');
}


			   
//=============================================================================================
  