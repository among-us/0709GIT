$(function(){

  $('#tamListBox').change(function(){
      selectNameNo = $(this).val();
      selectNameValue = $(this).children("option:selected").text()
      $("input[name='tam_name']").val(selectNameValue);
      
      var tam_name = selectNameValue;
      privateObj = new PrivateDTO(tam_name);
      $.ajax({
          url:'/manager/priv-ip',
          type:'get',
          data: privateObj,
          success:function(privIp){
		   $("input[name='tam_local_ip']").val(privIp);
          },
          error:function() {
            Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
          }
      })
  })
  
  $('#commTypeBox').change(function(){
      selectNameNo = $(this).val();
      selectNameValue = $(this).children("option:selected").text()
      
      $("input[name='comm_type']").val(selectNameValue);
      
  })
    $('#dbTypeBox').change(function(){
      selectNameNo = $(this).val();
      selectNameValue = $(this).children("option:selected").text()
      
      $("input[name='db_type']").val(selectNameValue);
  })
  
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
      
  })
  
  
	$("#fileCreate").on("click", function () {
		  var tam_name = $("input[name='tam_name']").val();
		  var tam_ip = $("input[name='tam_local_ip']").val();
		  var comm_type = $("input[name='comm_type']").val();
		  var db_type = $('input[name="db_type"]').val();
		  var db_ip = $('input[name="db_addr"]').val();		
		  var db_port = $('input[name="db_port"]').val();	
		  var db_id = $('input[name="db_id"]').val();		
		  var db_pwd = $('input[name="db_pwd"]').val();		
		  var db_name = $('input[name="db_name"]').val();	
		
		  manageObj = new ManageDTO(tam_name,tam_ip,comm_type,db_type,db_ip,db_port,db_id,db_pwd,db_name);
		
		  if (tam_name=="" || tam_ip =="" || comm_type=="" || db_type == "" || db_ip == "" || db_port == "" || db_id == "" || db_pwd=="" || db_name=="") {
		      Swal.fire('', '환경파일 생성에 필요한 정보를 모두 입력바랍니다', 'error');
		  }else if(db_port > 65535 || db_port < 0){
		    Swal.fire('범위 초과','포트번호에 올바른 범위의 값을 입력하세요','error');
		  } else {
		      $.ajax({
		              url: '/manager/setting',
		              type: 'get',
		              data: manageObj,
		              success: function(db) {
			                $('#name').val(db[0]);
			                $('#tamip').val(db[1]);
			                $('#commtype').val(db[2]);
			                $('#dbtype').val(db[3]);
			                $('#dbaddr').val(db[4]);
			                $('#dbport').val(db[5]);
			                $('#dbid').val(db[6]);
			                $('#dbpwd').val(db[7]);
			                $('#dbname').val(db[8]);
			                $('#hmac').val(db[9]);
		              },
		              error:function() {
		             	 Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
		          }
		        }).then((value) => {
			            var name = $("#name").val();
			            var tam_ip = $("#tamip").val();
			            var comm_type= $("#commtype").val();
			            var db_type= $("#dbtype").val();
			            var db_addr= $("#dbaddr").val();
			            var db_port= $("#dbport").val();
			            var db_id= $("#dbid").val();
			            var db_pwd = $("#dbpwd").val();
			            var db_name= $("#dbname").val();
			            var hmac = $("#hmac").val();
			            var sub = "################################################\n# Unetsystem TrustNET Authentication Agent.\n# Caution !!! Space not allowed.\n\n"
			            var strdata = sub+"tamanager_name=" + name + "\ntamanager_ip=" + tam_ip+"\n\n#directory path\nlog_path=.\n\n#debug/info/warning/error\nlog_level=error\n\n#communication type (network / directory)\ncomm_type=network\n\n#local option\ntam_config=.\n\n#################################################\n#Please do not modify below.\n\n" + "db_type=" + db_type +"\n\n" + "db_addr=" + db_addr +"\n\n" + "db_port=" + db_port +"\n\n" + "db_id=" + db_id +"\n\n" +"db_password=" + db_pwd +"\n\n" + "db_name=" + db_name +"\n\n" +"#정합성 코드\n"+ "hmac=" + hmac;
						saveAsFile(strdata, "tam.conf");
		      })
		 	}
	});
}); //function end 
  
function saveAsFile(str, filename) {
    var hiddenElement = document.createElement('a');
    hiddenElement.href = 'data:attachment/text,' + encodeURIComponent(str);
    hiddenElement.target = '_blank';
    hiddenElement.download = filename;
    hiddenElement.click();
}