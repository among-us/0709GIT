/**
 * 
 */	

							
$(function(){
/*
	$('#m_history_tables').DataTable( {
				serverSide : true,
//				processing : true,
				info:false,
				scrollX : true,
                bPaginate: true,
                bLengthChange: true,
                pageLength : 10,
            	lengthMenu: [ [10, 25, 50, 100], [10, 25, 50, 100] ],
                responsive: true,
                bAutoWidth: false,
                processing: true,
                ordering: true,
                searching: true,
                
	    	 ajax: {
		          url: "/manager/history",
		          type: "GET",
		          dataSrc : "data",
		          data : function(d) {
		          		
		          		
			          	var pageNumber = d.start;
			          	console.log("Page Number : " + pageNumber );
			          	
		          		var showCount = d.length;
		          		console.log('Show Count : '+showCount);
		          		
		          		d.pageNum = pageNumber;
		          		d.show_cnt = showCount;
		          		
		          		
		            	
	           	 }
			},
			columns : [
	                    {"data": "tam_history_no"},
	                    {"data": "tam_name"},
	                    {"data": "site_info"},
	                    {"data": "tam_local_ip"},
	                    {"data": "tam_local_port"},
	                    {"data": "tam_adm_port"},
	                    {"data": "tam_license"},
	                    {"data": "shared_path"},
	                    {"data": "comm_type"},
	                    {"data": "watchdog"},
	                    {"data": "tam_state"},
	                    {"data": "exist"},
	                    {"data": "hmac"},
	                    {"data": "issuer_user_id"},
	                    {"data": "action"},
	                    {"data": "history_reg_date"}
	                ],
	          language : {
	           search: ""
	         }
	})
	
		// // bkup origin
	*/
	
	// Manager 현황이력 DATATABLES 
	$('#m_history_tables').DataTable( {
				serverSide : true,
				processing : true,
				info:false,
				pageLength : 10,
				scrollX: true,
				pagingType : "full_numbers",
			ajax: {
			          url: "/manager/history",
			          type: "GET", 
			          dataSrc : "data",
			          data : function(d){
			          	console.log(d.length)
			          		
				          	var pageNumber = d.start;
				          	d.pageNum = pageNumber;
				          	
				          	var showCount= d.length;
				          	d.show_cnt = showCount;
						
							var ordering = d.order[0].dir;
							d.ordering = ordering;
							
							var filter = $('input[type=search]').val();
				          	d.filter = filter;
									
							var index = d.order[0].column;
							
							var column = d.columns[index].data;
							d.column = column;
			          },
			          dataFilter : function(d){
			          		var json = JSON.parse(d);
			          		json.recordsTotal = json.totalCount;
			          		json.recordsFiltered = json.totalCount;
			          		return JSON.stringify(json);
			          },
			},
			columns : [
	                    {"data": "tam_history_no"},
	                    {"data": "tam_name"},
	                    {"data": "site_info"},
	                    {"data": "tam_local_ip"},
	                    {"data": "tam_local_port"},
	                    {"data": "tam_adm_port"},
	                    {"data": "tam_pub_ip"},
	                    {"data": "tam_pub_port"},
	                    {"data": "tam_pub_adm_port"},
	                    {"data": "comm_type"},
	                    {"data": "watchdog"},
	                    {"data": "exist"},
	                    {"data": "issuer_user_id"},
	                    {"data": "action"},
	                    {"data": "history_reg_date"}
	                ],
				    columnDefs: [
		  				{targets : 13,
		  						 render: function (data, type, full, meta) { 
		  						 	if(full.action == "C"){
		  						 		return '<span>생성</span>'; 
		  						 	}else if(full.action == "U"){
		  						 		return '<span>변경</span>'; 
		  						 	}else{
		  						 		return '<span>삭제</span>'; 
		  						 	}
		  						 }
	  					}                          
		  			],
		          language : {
			           search: "", searchPlaceholder: "Search",
			           emptyTable : "데이터가 없습니다",
			           lengthMenu : " _MENU_ 개씩 보기",
			           loadingRecords : "로딩중입니다 ... ",
			           processing : "불러오는 중입니다 ... ",
			           zeroRecords : "조회하신 데이터가 없습니다",
			           paginate : {
				           	first : "처음",
				           	last : "마지막",
				           	next : "다음",
				           	previous : "이전"
			      }
	         }
	         
	         
	         
	})// datatables end 
		
	
	
})// function end



/*
	table2 = $('#tables2').DataTable( {
		info:false,
		responsive: true,
	  bAutoWidth: false,
	   scrollX: true,
	   	order: [ 0, 'asc' ]
	});
	
*/
	
//전역
function goToManagerZoneMatch(tam_no) {
	document.writeln('<form name=tamForm action=/manager/historyDetailPage METHOD=GET>');
	document.writeln('    <input name=tam_no type=hidden>');
	document.writeln('</form>');
	document.tamForm.tam_no.value=tam_no;
	document.tamForm.submit();
}
