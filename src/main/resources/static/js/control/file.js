$(function(){

	$('#svcNameBox').change(function(){
      selectNameNo = $(this).val();
      selectNameValue = $(this).children("option:selected").text()
      
      $("input[name='db_svc_name']").val(selectNameValue);
      
      var db_svc_name = selectNameValue;
      
      svcNameObj = new ServiceDTO(db_svc_name);
      
      $.ajax({
          url:'/manager/service-name',
          type:'get',
          data: svcNameObj,
          success:function(result){
          	console.log(result);
          var db_no = result[0];
          if (db_no == "1"){
		  		$("input[name='db_type']").val('mariaDB');
          }else{
		  		$("input[name='db_type']").val('oracleDB');
          }
		  $("input[name='db_addr']").val(result[1]);
		  $("input[name='db_port']").val(result[2]);
		  $("input[name='db_id']").val(result[3]);
		  $("input[name='db_pwd']").val(result[4]);
		  $("input[name='db_name']").val(result[5]);
		  	Swal.fire('알림','DB 정보 변경 필요 시에 [관리자 메뉴] -> [ DB 정보목록 ] \n서비스를 이용해주시기 바랍니다','warning')
          },
          error:function() {
            Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
          }
      })
  }) // svcNameBox change End
  
	//fileCreateBtn click
	$('#fileCreate').click(function(){
		var ctrl_name = $('#c_name').val();
		var local_ip = $('#c_local_ip').val();
		var db_svc_name = $('#db_svc_name').val();
		var db_type = $('#db_type').val();
		var db_addr = $('#db_addr').val();
		var db_port= $('#db_port').val();
		var db_id = $('#db_id').val();
		var db_pwd = $('#db_pwd').val();
		var db_name = $('#db_name').val();
		
		var ip = ValidateIP(local_ip);
		if(ctrl_name == "" || local_ip == "" || db_svc_name == "" || db_type == "" || db_addr == "" || db_port == "" || db_id == "" || db_pwd == "" || db_name == "" ){
			Swal.fire('','필수항목을 모두 입력바랍니다','error');	
		}else if(ip == false){
			Swal.fire('','올바른 내부 IP를 입력해주세요','error');	
		}else{
			fileObj = new FileDTO(ctrl_name, local_ip, db_svc_name, db_type, db_addr, db_port, db_id,db_pwd, db_name);
			$.ajax({
				url : '/controller/file',
				type : 'get',
				data : fileObj,
				success : function(result){
					var enc_db_addr = result.data[0];
					var enc_db_port = result.data[1];
					var enc_db_id = result.data[2];
					var enc_db_pwd = result.data[3];
					var enc_db_name = result.data[4];
					var hmac = result.data[5];
					var strdata = header+"\n"+dir_remark+"\nlog_path=../bin/logs\n\n"+deb_remark+"\nlog_level=debug\n\n"+ remark+mod+"controller_name="+ctrl_name+"\ncontroller_ip="+local_ip+"\n"+remark
					+"\ndb_type="+db_type+"\n\n#db_addr="+db_addr+"\ndb_addr="+enc_db_addr+"\n\n#db_port="+db_port+"\ndb_port="+enc_db_port+"\n\n#db_id="+db_id+"\ndb_id="+enc_db_id+"\n\n#db_passwd="+db_pwd+"\ndb_passwd="+enc_db_pwd+"\n\n#db_name="+db_name+"\ndb_name="+enc_db_name+"\n\n#정합성 코드\nhmac="+hmac;
					saveAsFile(strdata, "tnctrl.conf");
				},
				error : function(result){
					console.log(result.data);
				}
			})
		}
	})  

  
}) // function End

// Foreign Function
function saveAsFile(str, filename) {
    var hiddenElement = document.createElement('a');
    hiddenElement.href = 'data:attachment/text,' + encodeURIComponent(str);
    hiddenElement.target = '_blank';
    hiddenElement.download = filename;
    hiddenElement.click();
}