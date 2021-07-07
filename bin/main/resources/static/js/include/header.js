var ProfileDTO = function (passwd) {
	
	this.passwd= passwd;

	return this;
}

function back(){
	window.history.back();
}

$(function(){

/*
$.ajax({
	url: '/user/perm-code',
	get: 'get',
	success:function(result){
	},
	error:function(result){
		alert('error');
	}
})
*/
	hover();
	hideLink();

	$('#logout').click(function(){
	
		Swal.fire({
			  title: '',
			  text: "로그아웃 하시겠습니까 ?",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#E74A3B',
			  cancelButtonColor: 'gray',
			  confirmButtonText: '로그아웃',
			  cancelButtonText : '아니오'
			}).then((result) => {
				if (result.isConfirmed) {
					window.location.href = "/logout";
			  }// if
			})	//then
		})// LOGOUT CLICK FUNCTION END 
	
			$('#user_info_update').click(function(){
				Swal.fire({
						  title: '',
						  input: 'password',
						  inputLabel: '비밀번호를 입력해주세요',
						  inputPlaceholder: '계정 비밀번호 입력',
					  inputAttributes: {
					    maxlength: 100,
					    autocapitalize: 'off',
					    autocorrect: 'off'
					  }
				}).then((value) => {
				var passwd = $('#swal2-input').val();
				console.log(passwd);
				profileObj = new ProfileDTO(passwd);
					$.ajax({
						url : '/user/profile',
						type : 'get',
						data : profileObj,
						success : function(d){
							if(d.data == true){
								window.location.href="/user/myAccountPage";
							}else{
								Swal.fire('','비밀번호가 일치하지 않습니다','error');
							}
						},
						error : function(d){
							Swal.fire('','','error');
						}
					}) // ajax end
				})// then flow
		}) // function end
});

function hover(){

		var link = document.location.href;
		var inputClass = document.getElementsByClassName("collapse-item");
		for(var i =0; i < inputClass.length; i++){
				if(inputClass[i].href == link){
					inputClass[i].className += " bg-dark text-white";
					inputClass[i].parentNode.parentNode.className = "collapse";
					inputClass[i].parentNode.parentNode.className += " show";
				}
		}
}

function hideLink(){
	var pathname = location.pathname;
	
	if(pathname == "/user/updatePage"){
		$('#user_list').addClass(' bg-secondary text-light');
		$('#user_list').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/user/historyDetailPage"){
		$('#user_history').addClass(' bg-secondary text-light');
		$('#user_history').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/manager/dbupdatePage"){
		$('#db_list').addClass(' bg-secondary text-light');
		$('#db_list').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/manager/updatePage"){
		$('#manager_list').addClass(' bg-secondary text-light');
		$('#manager_list').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/manager/settingPage"){
		$('#manager_cfile').addClass(' bg-secondary text-light');
		$('#manager_cfile').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/zone/updatePage"){
		$('#zone_list').addClass(' bg-secondary text-light');
		$('#zone_list').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/zoneLicense/updatePage"){
		$('#zonelicense_list').addClass(' bg-secondary text-light');
		$('#zonelicense_list').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/zone/historyDetailPage"){
		$('#zone_history').addClass(' bg-secondary text-light');
		$('#zone_history').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/project/updatePage"){
		$('#project_setting').addClass(' bg-secondary text-light');
		$('#project_setting').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/managerZone/enrollPage"){
		$('#manager_matching').addClass(' bg-secondary text-light');
		$('#manager_matching').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/company/updatePage"){
		$('#group_info').addClass(' bg-secondary text-light');
		$('#group_info').parent().parent().addClass(" collapse show");
	}
	if(pathname == "/controller/updatePage"){
		$('#taControllerInfo').addClass(' bg-secondary text-light');
		$('#taControllerInfo').parent().parent().addClass(" collapse show");
	}
	
	if(pathname == "/controller/filePage"){
		$('#taControllerConfFile').addClass(' bg-secondary text-light');
		$('#taControllerConfFile').parent().parent().addClass(" collapse show");
	}
}