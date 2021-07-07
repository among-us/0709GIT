$(function(){
	
	selectNameValue = ''
	selectNameNo = ''
	
	// Agent 환경파일 생성 입력란 3개 붙여넣기 
	
$('#zoneListBox').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='zone_name']").val(selectNameValue);
		
	})

	$('#tamListBox').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='tam_name']").val(selectNameValue);
		
	})

	$('#taaListBox').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='taa_ip']").val(selectNameValue);
		
	})
	
	$('#tamIP1').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='tam_local_ip_1']").val(selectNameValue);
		
	})

	$('#tamLocalPort1').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='tam_local_port_1']").val(selectNameValue);
		
	})
	$('#tamIP2').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='tam_local_ip_2']").val(selectNameValue);
		
	})
	$('#tamLocalPort2').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		
		$("input[name='tam_local_port_2']").val(selectNameValue);
		
	})
	
	// taCreateFilePage 에서 tamanager1,2 ip/port 가져오는 script
	
	$('#tamListBox1').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		$("input[name='tam_name']").val(selectNameValue);
		
		var tam_name = selectNameValue;
		console.log(tam_name);
		privateObj = new PrivateDTO(tam_name);
		$.ajax({
          url:'/agent/private',
          type:'get',
          data: privateObj,
          success:function(result){
			   $("input[name='tam_local_ip_1']").val(result[0]);
			   $("input[name='tam_local_port_1']").val(result[1]);
		  $('#priv_btn').click(function(){
			   $("input[name='tam_local_ip_1']").val(result[0]);
			   $("input[name='tam_local_port_1']").val(result[1]);
		  });
		  $('#pub_btn').click(function(){
			   $("input[name='tam_local_ip_1']").val(result[2]);
			   $("input[name='tam_local_port_1']").val(result[3]);
		  });
		   
          },
          error:function(result) {
            Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
          }
      })
		//ajax
	})
	
	$('#tamListBox2').change(function(){
		selectNameNo = $(this).val();
		selectNameValue = $(this).children("option:selected").text()
		$("input[name='tam_name2']").val(selectNameValue);
		
		var tam_name = selectNameValue;
		console.log(tam_name);
		privateObj = new PrivateDTO(tam_name);
		$.ajax({
          url:'/agent/private',
          type:'get',
          data: privateObj,
          success:function(result){
			   $("input[name='tam_local_ip_2']").val(result[0]);
			   $("input[name='tam_local_port_2']").val(result[1]);
		  $('#priv_btn2').click(function(){
			   $("input[name='tam_local_ip_2']").val(result[0]);
			   $("input[name='tam_local_port_2']").val(result[1]);
		  });
		  $('#pub_btn2').click(function(){
			   $("input[name='tam_local_ip_2']").val(result[2]);
			   $("input[name='tam_local_port_2']").val(result[3]);
		  });
		   
          },
          error:function() {
            Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
          }
      })
		//ajax
	})
	

	

//
	
$("#enrollBtn").click(function() {
	zoneLicenseDTO = new ZoneLicenseDTO(selectNameNo,$("input[name='taa_ip']").val(), $("select[name='license_type'] option:selected").val());
	
	var zone_name = $('input[name="zone_name"]').val();
	var taa_ip = $("input[name='taa_ip']").val();
	var result = ValidateIPaddress(taa_ip);
	
	if(zone_name == "" || taa_ip == ""){
		Swal.fire('','모든 정보를 입력바랍니다','error');
	}else if(result == false){
		Swal.fire('','올바른 AGENT IPv4 주소를 입력해주세요','error');
	}else {
		$.ajax({
			url:'/zone/license',
			type:'post',
			data: zoneLicenseDTO,
			success:function(result){
				if(result.data.errorCode == -1){
					Swal.fire('',result.data.errorMessage, 'error')
				}
				if(result.data.errorCode == 0) { 
					Swal.fire('성공','등록 성공', 'success')
					.then((value) => {
						window.location.href = "/zoneLicense/listPage"
					});
				}
			},
			error:function() {
				Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
			}
		})
	}
});
	
	// Agent 환경파일 생성


$("#fileCreate").on("click", function () {
  var zone_name = $("input[name='zone_name']").val();
  var tam_local_ip_1 = $("input[name='tam_local_ip_1']").val(); // 암호화
  var tam_local_port_1 = $("input[name='tam_local_port_1']").val(); // 암호화
  
  var tam_local_ip_2 = $("input[name='tam_local_ip_2']").val(); 
  var tam_local_port_2 = $("input[name='tam_local_port_2']").val(); 
  
  var taa_ip = $("input[name='taa_ip']").val();
  //var license = $('select[name="license_type"]').val();
  var license = $("#select_license option:selected").text();
  

  createcObj = new CreatecDTO( zone_name, tam_local_ip_1, tam_local_port_1,tam_local_ip_2,tam_local_port_2,taa_ip,license);

  if(license == "정적"){
  	license == "static"
  }else if(license == "동적"){
  	license == "dynamic"
  }else{
  	license == "timelimit"
  }
  console.log('license : ' +license);
  
	var result = ValidateIPaddress(taa_ip);
  	var test = taa_ip.split('.');
	var ipcheck = test[0];
	
	if (zone_name == "" || tam_local_ip_1 == "" || tam_local_port_1 == "" || taa_ip == "") {
		Swal.fire('', '환경파일 생성에 필요한 정보를 모두 입력바랍니다', 'error');
	}else if(result == false){
		Swal.fire('입력 오류','올바른 TAAgent IP 주소를 입력해주세요','error');
	}else if(ipcheck =="127"){
		Swal.fire('입력 오류','TAAgent IP 주소를 확인해주세요','error');
	}else {
      $.ajax({
              url: '/zone/agent-file',
              type: 'GET',
              data: createcObj,
              success: function(taaFile) {
	              console.log(taaFile.data.length);
	              var size = taaFile.data.length;
	              
              if(size == 3){
                  $('#enc_ip').val(taaFile.data[0]);
                  $('#enc_port').val(taaFile.data[1]);
                  $('#hmac').val(taaFile.data[2]);
              }else {
                  $('#enc_ip').val(taaFile.data[0]);
                  $('#enc_port').val(taaFile.data[1]);
                  $('#enc_ip2').val(taaFile.data[2]);
                  $('#enc_port2').val(taaFile.data[3]);
                  $('#hmac').val(taaFile.data[4]);
              }
          }, 	//success
          error : function(result) {
              Swal.fire('', '서버와의 통신에 실패하였습니다', 'error');
          }//error
      })// ajax
	       .then(function () {
			var enc_ip = $("#enc_ip").val();
			var enc_port =$("#enc_port").val();
			var hmac = $("#hmac").val();
			var ip_2 = $("#enc_ip2").val();
			var port_2 = $("#enc_port2").val();

			if(license == "정적"){
				license = "static";
			}else if(license =="동적"){
				license = "dynamic";
			}else if(license =="시간제한"){
				license ="timelimit";
			}
			var sub = "#############################################\n# Unetsystem TrustNET Authentication Agent.\n# Caution !!! Space not allowed.\n\n\#shared directory\n"+ "#shared_path=" +"\n"+"\n#log path\nlog_path=.\n\n#############################################\n#Please do not modify below.\n\n"
			var strdata = sub+"zone_name="+zone_name +"\n\n"+ "#communication type (network or directory)\ncomm_type=network\n\n"   + "tamanager_local_ip_1=" +enc_ip + "\ntamanager_local_port_1=" + enc_port + "\n\ntamanager_local_ip_2="+ip_2 +"\ntamanager_local_port_2="+port_2+"\n\ntaa_ip="+taa_ip +"\n\n#license type (static/dynamic/timelimit)\n"+"license="+license+"\n\n#정합성코드\nhmac="+hmac;
			saveAsFile(strdata, "taa.conf");
       })
 	 }
});

	
});
//전역
function saveAsFile(str, filename) {
	var hiddenElement = document.createElement('a');
	hiddenElement.href = 'data:attachment/text,' +  encodeURIComponent(str); //encodeURI()
	hiddenElement.target = '_blank';
	hiddenElement.download = filename;
	hiddenElement.click();
}
            
function goToZoneLicenseInfo(zone_no, taa_ip, license_type) {
	document.writeln('<form name=zoneLicenseForm action=/zoneLicense/updatePage METHOD=GET>');
	document.writeln('    <input name=zone_no type=hidden>');
	document.writeln('    <input name=taa_ip type=hidden>');
	document.writeln('    <input name=license_type type=hidden>');
	document.writeln('</form>');
	document.zoneLicenseForm.zone_no.value=zone_no;
	document.zoneLicenseForm.taa_ip.value=taa_ip;
	document.zoneLicenseForm.license_type.value=license_type;
	document.zoneLicenseForm.submit();
}

function ValidateIPaddress(ip) {
	if (/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])((\/)([0-9]|[0-2][0-9]|3[0-2])){0,1}$/.test(ip)) {
		return (true)
	}else{
		Swal.fire('','올바른 로컬 IPv4 주소를 입력해주세요','error');
		return (false)
	}
}
