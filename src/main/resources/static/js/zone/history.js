/**
 * 
 */



$(function(){

var stateTBL = 

$('#searchBtn').click(function(){
	$('#agentInput').val($('#agentInput').val());
	$('#statetables').DataTable().ajax.reload();	
}) // searchBtn Click flow 

		console.time("time");

		$('#statetables').DataTable( {
			serverSide : true,
		    processing : true,
		    paging : true,
		    info:false,
            responsive : true,
            scrollX: true,
            pagingType : "full_numbers",
            order : [[0,"desc"]],
            searching : false,
    	
    	 ajax: {
		          url: "/zone/history",
		          type: "GET", 
		          dataSrc : "data",
		          data : function(d){
				          	var pageNumber = d.start;
				          	d.pageNum = pageNumber;
				          	
				          	var showCount= d.length;
				          	d.show_cnt = showCount;
				          	
				          	var ordering = d.order[0].dir;
				          	d.ordering = ordering;
			          	
			          		var filter = $('#agentInput').val();
				          	d.filter = filter;
				          	
							var index = d.order[0].column;
							var column = d.columns[index].data;
							console.log(column);
							
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
                    {"data": "state_no"},
                    {"data": "tam_name"},
                    {"data": "tam_local_ip"},
                    {"data": "taa_ip"},
                    {"data": "taa_hostname"},
                    {"data": "zone_name"},
                    {"data": "license_type"},
                    {"data": "req_type"},
                    {"data": "req_date"},
                    {"data": "req_result"},
                    {"data": "result_reason"}
                ],
          language : {
		           search: '',
		           searchPlaceholder : "Search",
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
		      },
         columnDefs: [
         
	  				{targets : 6,
	  						 render: function (data, type, full, meta) { 
	  						 
	                    if(full.license_type == 1){
		                       return  '<span>Static</span>';
	                    }//if end test
	                    else if (full.license_type == 2){
	                       	return  '<span>Dynamic</span>';
	                    }else {
	                       	return  '<span>Timelimit</span>';
	                    }
	  						}
  					},
  					{targets : 7,
	  						 render: function (data, type, full, meta) { 
	                    if(full.req_type== "1"){
		                       return  '<span>초기요청</span>';
	                    }//if end test
	                    else{
		                       return  '<span>재요청</span>';
	                    }// else end test
	  						}
  					},
  					{targets : 9,
	  						 render: function (data, type, full, meta) { 
	                    if(full.req_result== "S"){
	                    
		                       return  '<span>성공</span>';
	                    }//if end test
	                    else{
		                       return  '<span>실패</span>';
	                    }// else end test
	  						}
  					}
  					
	  	]
	  	
})



		  	console.timeEnd("time");
})







		
// =======================================================================================================================	
    



//전역
function goToZoneUpdatePage(zone_no) {
	document.writeln('<form name=zoneForm action=/zone/historyDetailPage METHOD=GET>');
	document.writeln('    <input name=zone_no type=hidden>');
	document.writeln('</form>');
	document.zoneForm.zone_no.value=zone_no;
	document.zoneForm.submit();
}
