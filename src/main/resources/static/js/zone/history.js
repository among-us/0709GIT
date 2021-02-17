/**
 * 
 */

$(function(){
	
	 statetable = $('#statetables').DataTable( {
			info:false,
			responsive: false,
	        order: [ 8, 'desc' ]
	    });
	 
	 table = $('#tables').DataTable( {
		info:false,
		responsive: false,
        order: [ 0, 'asc' ]
    });
    
	table2 = $('#tables2').DataTable( {
		info:false,
		responsive: false,
	    order: [ 0, 'asc' ]
	});
});


//전역
function goToZoneUpdatePage(zone_no) {
	document.writeln('<form name=zoneForm action=/zone/historyDetailPage METHOD=GET>');
	document.writeln('    <input name=zone_no type=hidden>');
	document.writeln('</form>');
	document.zoneForm.zone_no.value=zone_no;
	document.zoneForm.submit();
}
