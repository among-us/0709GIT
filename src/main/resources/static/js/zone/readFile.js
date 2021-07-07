$(function(){

});

//======================= up origin ==================DOWN MOD ===================================================================================================================================+

/*
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
	try{
			var full = reader.result.split("\n");
            var a = full[13];
            var zone_name = a.split("=");
            var name = zone_name[1];
            $("#name").val(name);

            var b = full[18];
           	var ip1=b.substring(21).trim();
            $("#ip1").val(ip1);

            var c = full[19];
            var port1 = c.substring(23).trim();
            $("#port1").val(port1);
            
            var d = full[21];
            var ip2 = d.substring(21).trim();
            $("#ip2").val(ip2);
            
            var e = full[22];
            var port2 = e.substring(23).trim();
            $("#port2").val(port2);
            
            var f = full[24];
            var taaip = f.split("=");
            var taa_ip = taaip[1];
            $("#taaip").val(taa_ip);
            
            var g = full[27];
            var taatype = g.split("=");
            var license = taatype[1];
            $("#taatype").val(license);

            var h = full[30];
            var hmac = h.substring(5).trim();
            $("#hmac").val(hmac);
            
					var zone_name = $("#name").val();
					var tam_local_ip_1 = $("#ip1").val();
					var tam_local_port_1 = $("#port1").val();
					var tam_local_ip_2 = $("#ip2").val();
					var tam_local_port_2 = $("#port2").val();
					var taa_ip = $("#taaip").val();
					var license = $("#taatype").val();
					var hmac = $("#hmac").val();	// 파일에서 읽은 hmac값 
					
						if(license != "static" && license != "dynamic" && license != "timelimit"){
							Swal.fire('','올바르지 않은 라이선스 타입입니다','error').then((value) =>{
								location.reload();
							});
						}else {
							console.log('else flow');
									
									if(license == "static"){
										license ="정적"
									}
									if(license == "dynamic"){
										license ="동적"
									}
									if(license =="timelimit"){
										license ="시간제한"
									}
					         fileInfoDTO = new FileInfoDTO(zone_name, tam_local_ip_1,tam_local_port_1,tam_local_ip_2,tam_local_port_2, taa_ip, license, hmac);
					         console.log("file info dto :"+fileInfoDTO);
					         
						         
							   		$.ajax({
							            url:'/zone/verify-content',
							          	type:'get',
							            data: fileInfoDTO,
							            success:function(value){
							            console.log(value);
							            var length = value.data.length;
							            
							            if(length == 9){ 	// full 
							            console.log('full');
									            $('#name').text(value.data[0]);
								            	$('#ip1').text(value.data[1]);
								            	$('#port1').text(value.data[2]);
								            	$('#ip2').text(value.data[3]);
								            	$('#port2').text(value.data[4]);
								            	$('#taaip').text(value.data[5]);
								            	$('#taatype').text(value.data[6]);
								            	$('#hmac').text(value.data[7]);	// modal에 hmac 값 적어주기
												var hmac = $("#hmac").val();
								            	if(value.data[8] == value.data[7]){
													$("#verifyResult").text("검증 성공");					            	
								            	}else{
								            		$('.TaaResult').addClass("fail");
								            		$("#verifyResult").text("검증 실패");
								            	}
								            	$('#taaFilemodal').modal('show');
								            	
							            } else {			// part 
									            $('#name').text(value.data[0]);
								            	$('#ip1').text(value.data[1]);
								            	$('#port1').text(value.data[2]);
								            	$('#taaip').text(value.data[3]);
								            	if(value.data[4] == "정적"){
									            	$('#taatype').text("static");
								            	}
								            	if(value.data[4] == "동적"){
									            	$('#taatype').text("dynamic");
								            	}
								            	if(value.data[4] == "시간제한"){
									            	$('#taatype').text("timelimit");
								            	}
								            	
								            	$('#hmac').text(value.data[5]);
												var hmac = $("#hmac").val();
												if(value.data.errorCode == -1){
													Swal.fire('','파일을 임의로 수정할시 오류가 발생할 수 있습니다','error')
												}
								            	else if(value.data[5] == value.data[6]){
													$("#verifyResult").text("검증 성공");
													$('#taaFilemodal').modal('show');																	            	
								            	}else{
								            		$('.TaaResult').addClass("fail");
								            		$("#verifyResult").text("검증 실패");
								            		$('#taaFilemodal').modal('show');
								            	}
							            } // big else end
							            },
							            error:function() {
							            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
							            }
							        })// ajax
						        } // big else
						       } // try
						       catch(e){
						       		Swal.fire('','올바르지 않은 파일 형식입니다','error')
						       }
  };
  reader.readAsText(file,  "UTF-8")
  $('#myModal').modal('show');
}
        
*/

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
  reader.onload = function(){
  try{
  console.log('try');
  
        var full = reader.result.split("\n");
        var a = full[13];
        var zone_name = a.split("=");
        var name = zone_name[1];
        $("#name").val(name);

        var b = full[18];
        var ip1=b.substring(21).trim();
        $("#ip1").val(ip1);

        var c = full[19];
        var port1 = c.substring(23).trim();
        $("#port1").val(port1);
        
        var d = full[21];
        var ip2 = d.substring(21).trim();
        $("#ip2").val(ip2);
        
        var e = full[22];
        var port2 = e.substring(23).trim();
        $("#port2").val(port2);
        
        var f = full[24];
        var taaip = f.split("=");
        var taa_ip = taaip[1];
        $("#taaip").val(taa_ip);
        
        var g = full[27];
        var taatype = g.split("=");
        var license = taatype[1];
        $("#taatype").val(license);

        var h = full[30];
        var hmac = h.substring(5).trim();
        $("#hmac").val(hmac);

        var zone_name = $("#name").val();
        var tam_local_ip_1 = $("#ip1").val();
        var tam_local_port_1 = $("#port1").val();
        var tam_local_ip_2 = $("#ip2").val();
        var tam_local_port_2 = $("#port2").val();
        var taa_ip = $("#taaip").val();
        var license = $("#taatype").val();
        var hmac = $("#hmac").val();

        if(license != "static" && license != "dynamic" && license != "timelimit"){
          Swal.fire('','올바르지 않은 라이선스 파일입니다','error').then((value) =>{
            location.reload();
          });
        }else{
          console.log('license > '+license);
          switch(license){
              case 'static' : license ="정적";break;
              case 'dynamic' : license ="동적";break;
              case 'timelimit' : license ="시간제한";break;
          } //switch   
          
          fileInfoDTO = fileInfoDTO = new FileInfoDTO(zone_name, tam_local_ip_1,tam_local_port_1,tam_local_ip_2,tam_local_port_2, taa_ip, license, hmac);

          $.ajax({
            url:'/zone/verify-content',
            type:'get',
            data: fileInfoDTO,
            success:function(value){
            	if(value.data.errorCode == -1){
            		Swal.fire('','파일을 임의로 수정할 시 검증이 불가합니다','error');	
            	}else if(value.data.length == 9){
                  $('#name').text(value.data[0]);
                  $('#ip1').text(value.data[1]);
                  $('#port1').text(value.data[2]);
                  $('#ip2').text(value.data[3]);
                  $('#port2').text(value.data[4]);
                  $('#taaip').text(value.data[5]);
                  $('#taatype').text(value.data[6]);
                  $('#hmac').text(value.data[7]);	// modal에 hmac 값 적어주기
	                  if(value.data[7] == value.data[8]){
	                    $("#verifyResult").text("검증 성공");
	                    $('.TaaResult').removeClass("fail");
	                  }
	                  if(value.data[7] != value.data[8]){
	                    $('.TaaResult').addClass("fail"); 
	                    $("#verifyResult").text("검증 실패");
	                  }
	                  $('#taaFilemodal').modal('show');
                }// if length 9 
                else{ 
            	if(value.data.errorCode == -2){
            		Swal.fire('',value.data.errorMessage,'error');
            	}else{
                  $('#name').text(value.data[0]);
                  $('#ip1').text(value.data[1]);
                  $('#port1').text(value.data[2]);
                  $('#taaip').text(value.data[3]);
                  $('#taatype').text(value.data[4]);
                  $('#hmac').text(value.data[5]);

	                  if(value.data[5] == value.data[6]){
	                    $("#verifyResult").text("검증 성공");
	                    $('#taaFilemodal').modal('show');	
	                  }else{
	                    $('.TaaResult').addClass("fail");
	                    $("#verifyResult").text("검증 실패");
	                    $('#taaFilemodal').modal('show');
	                  }
                  }
                  
                }
            }// success
            ,error:function(){
              Swal.fire('','서버와의 통신에 실패하였습니다','error');
            }
          })
        }
      }catch(e){
      console.log('catch');
        Swal.fire('','올바르지 않은 파일 형식입니다','error')
      }// catch
  } // function end
      reader.readAsText(file,  "UTF-8")
      $('#myModal').modal('show');
}
