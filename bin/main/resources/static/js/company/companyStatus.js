$(function(){
	
$('#companyTables tbody tr').click(function() {
    $(this).addClass('bg-primary').siblings().removeClass('bg-primary');
    $(this).addClass('text-white').siblings().removeClass('text-white');
});

$("#companyTables tbody").on("click", "tr", function(){
	
	var zone_name = $(this).find("td:eq(2)").text();
	var revision_no = $(this).find("td:eq(7)").text();
	var limited_url = $(this).find("td:eq(8)").text();
	
	var static = $(this).find("td:eq(4)").text();
	var dynamic = $(this).find("td:eq(5)").text();
	console.log(static);
	console.log(dynamic);
	var static_val = static.split("/");
	var dynamic_val = dynamic.split("/");
	
	var asis_static_cnt = static_val[1];
	var tobe_static_cnt = static_val[0];
	var asis_dynamic_cnt = dynamic_val[1];
	var tobe_dynamic_cnt = dynamic_val[0];
	
	$('#integrityInfo').on('show.bs.modal', function(){
	      $('#zone_name')[0].innerText = zone_name;
	      $('#revision_no')[0].innerText = revision_no;
	   	  $('#zone_pl_count')[0].innerText = asis_static_cnt;
	      $('#zone_tpl_count')[0].innerText = asis_dynamic_cnt;
	      $('#zone_limited_url')[0].innerText = limited_url;
	});
	      $('#integrityInfo').modal('show');
});


$("#verifyBtn").click(function() {
		
			var zone_name =	$("b[id='zone_name']").text();
			var asis_revision_no = $("b[id='revision_no']").text();
			var asis_pl_count= $("b[id='zone_pl_count']").text();
			var asis_tpl_count = $("b[id='zone_tpl_count']").text();
			var asis_limited_url = $("b[id='zone_limited_url']").text();
			
			var tobe_pl_count = $("input[name='pl_count']").val();
			var tobe_tpl_count = $("input[name='tpl_count']").val();
			var tobe_limited_url = $("input[name='limited_url']").val();
			// === name , reno 를 제외한 3가지는 사용자 신규 입력
			
				if(tobe_pl_count =="" ){
					Swal.fire('','정적 라이선스 개수를 입력해주세요','error').then((value) => {
						location.reload();
					});
				}else if(tobe_tpl_count =="" ){
					Swal.fire('','동적 라이선스 개수를 입력해주세요','error').then((value) => {
						location.reload();
					});
				}else{
					verifyObj = new VerifyDTO(zone_name,asis_revision_no, asis_pl_count,asis_tpl_count,asis_limited_url, tobe_pl_count,tobe_tpl_count,tobe_limited_url);
					console.log(verifyObj);
				
		        $.ajax({
					url:'/zone/verify',
					type:'GET',
					data: verifyObj,
			          	success:function(sReqMsg){
			          		$("b[id='req_value']").text(sReqMsg.toString());
			          	},
						error:function(result) {
			            	Swal.fire('','서버와의 통신에 실패하였습니다','error');
						}
			   	})
						$('#zone_request').modal('show');
				}
    });
    
	
	
	document.getElementById("file_download").addEventListener("click", function(){
				var str = $("b[id='req_value']").text();
				console.log(str);
				var filename = "tas_req_license_info.txt";
				
				saveAsFile(str, filename);
				
				return new Promise((resolve, reject) => {
		        	resolve("success");
		        })
		        
			}, false);	



	      	function saveAsFile(str, filename) {
	      		var as = document.createElement('a');
				
				as.setAttribute('href', 'data:text/plain;charset=utf-8,'+encodeURIComponent(str));	      		
				as.setAttribute('download', filename);
				
				as.style.display = 'none';
				document.body.appendChild(as);
				
				as.click().then((resolve) =>{
					alert('a');
				});
				console.log('test');
				
				document.body.removeChild(as);
					
	      }
	
	
})// function end

