
$(function(){
	$("#list_del_btn").click(function(){ 
	 Swal.fire({
		  title: '',
		  text: 'DB 정보를 삭제하시겠습니까 ?',
		  icon: 'warning',
		  width : 500,
		  padding : '2em',
		  background : '#272C33',
		  iconColor : 'orange',
		  showCancelButton: true,
		  confirmButtonColor: '#d33',
		  cancelButtonColor: 'lightgray',
		  cancelButtonText: '<span style="color : black">취소</span>',
		  confirmButtonText: '삭제하겠습니다'
		}).then((result) => {
		  if (result.isConfirmed) {
				var rowData = new Array();
				var checkbox = $("input[name='checkbox']:checked");
				console.log(checkbox);
				
				checkbox.each(function(i) {
						var tr = checkbox.parent().parent().eq(i);
						var td = tr.children();
						rowData.push(tr.text());
						var svc_name = td.eq(1).text();
						
						checkDelObj = new DeleteDTO(svc_name);
						$.ajax({
					            url:'/manager/db',
					            type:'delete',
					            data: checkDelObj,
					            success:function(result){
					            	Swal.fire({
						            	title : '경고',
						            	text : result.data.errorMessage,
						            	icon : 'error',
						            	background : '#272C33'
					            	})
					            	if(result.data.errorCode == 0) {
										Swal.fire({
											title : '성공',
											text : '삭제 성공', 
											icon : 'success',
											iconColor : 'cornflowerblue',
											background : '#272C33',
											width : 500,
											padding : '2em'
										})	
										.then((value) => {
						            		window.location.href = "/manager/listDBPage"
										});
						            }
						         },
					            error:function() {
					            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
					            }
				        })
		        }) // each
		        } //if
			}) // then
		});// btn
});// function end 
	
	function goToDbUpdatePage(db_no) {
		document.writeln('<form name=dbForm action=/manager/dbupdatePage METHOD=GET>');
		document.writeln('    <input name=db_no type=hidden>');
		document.writeln('</form>');
		document.dbForm.db_no.value=db_no;
		document.dbForm.submit();
	}