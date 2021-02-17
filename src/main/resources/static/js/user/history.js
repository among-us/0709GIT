/**
 * 
 */

$(function(){
	 table = $('#tables').DataTable( {
		info:false,
		responsive: false,
        order: [ 0, 'asc' ]
    });
});




//전역
function goToUserInfoDetails(user_no) {
	document.writeln('<form name=userForm action=/user/historyDetailPage METHOD=GET>');
	document.writeln('    <input name=user_no type=hidden>');
	document.writeln('</form>');
	document.userForm.user_no.value=user_no;
	document.userForm.submit();
}
