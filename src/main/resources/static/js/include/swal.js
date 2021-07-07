// error Swal

// 계정 관리
function UserChangeSwal(){
	Swal.fire({
		text: '변경 완료',
		icon : 'success',
		iconColor : 'cornflowerblue',
		width: 500,
		padding: '2em',
		background: '#272C33',
		confirmButtonColor : 'cornflowerblue'
	}).then((value) =>{
		window.location.href ="/user/listPage";
	})
}

// Database 관리
function UserChangeSwal(){
	Swal.fire({
		text: '변경 완료',
		icon : 'success',
		iconColor : 'cornflowerblue',
		width: 500,
		padding: '2em',
		background: '#272C33',
		confirmButtonColor : 'cornflowerblue'
	}).then((value) =>{
		window.location.href ="/user/listPage";
	})
}