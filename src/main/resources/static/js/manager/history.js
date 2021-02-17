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
function goToManagerZoneMatch(tam_no) {
	document.writeln('<form name=tamForm action=/manager/historyDetailPage METHOD=GET>');
	document.writeln('    <input name=tam_no type=hidden>');
	document.writeln('</form>');
	document.tamForm.tam_no.value=tam_no;
	document.tamForm.submit();
}
