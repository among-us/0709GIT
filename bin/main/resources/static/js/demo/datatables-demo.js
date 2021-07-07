// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
  	info:false,
  	bPaginate: true,
	pagingType : "full_numbers",
	 language : {
			           paginate : {
				           	first : "처음",
				           	last : "마지막",
				           	next : "다음",
				           	previous : "이전"
			     		}
			}
  });
});
