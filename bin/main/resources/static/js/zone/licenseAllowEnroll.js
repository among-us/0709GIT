$(function(){
	 $("#fileCreate").on("click", function () {
    var tam_name = $("input[name='tam_name']").val();
    var tam_ip = $("input[name='tam_local_ip']").val();
    var comm_type = $("input[name='comm_type']").val();
    var db_type = $('input[name="db_type"]').val();
    var db_ip = $('input[name="db_addr"]').val();		//암호화
    var db_port = $('input[name="db_port"]').val();		//암호화
    var db_id = $('input[name="db_id"]').val();			//암호화
    var db_pwd = $('input[name="db_pwd"]').val();			//암호화
    var db_name = $('input[name="db_name"]').val();		//암호화
  	//console.log(tam_name,tam_ip, comm_type,db_type,db_ip,db_port,db_id,db_pwd,db_name);
  
    manageObj = new ManageDTO(tam_name,tam_ip,comm_type,db_type,db_ip,db_port,db_id,db_pwd,db_name);
 	//console.log(manageObj);
  
    if (tam_name=="" || tam_ip =="" || comm_type=="" || db_type == "" || db_ip == "" || db_port == "" || db_id == "" || db_pwd=="" || db_name=="") {
        Swal.fire('', '환경파일 생성에 필요한 정보를 모두 입력바랍니다', 'error');
    }else if(db_port > 65535 || db_port < 0){
    	Swal.fire('범위 초과','포트번호에 올바른 범위의 값을 입력하세요','error');
    } else {
        $.ajax({
                url: '/manager/setting',
                type: 'get',
                data: manageObj,
                success: function (db) {
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
          })
            .then(function () {
              function saveAsFile(str, filename) {
                var hiddenElement = document.createElement('a');
                hiddenElement.href = 'data:attachment/text,' + encodeURI(str);
                hiddenElement.target = '_blank';
                hiddenElement.download = filename;
                hiddenElement.click();
              }
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
              
              var strdata = "tam_name=" + name +"\n" +"tam_ip=" + tam_ip +"\n"+"comm_type=" + comm_type +"\n" + "db_type=" + db_type +"\n" + "db_addr=" + db_addr +"\n" + "db_port=" + db_port +"\n" + "db_id=" + db_id +"\n" +"db_password=" + db_pwd +"\n" + "db_name=" + db_name +"\n" + "hmac=" + hmac;
              
              saveAsFile(strdata, "tam.conf");
         }).then(function(){
         	Swal.fire('성공','환경파일이 생성되었습니다','success').then((value) => {
            		 //window.location.href = "/main";
			});
      	 })
      }
  });
})