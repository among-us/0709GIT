$(function(){

	$('#db_history_tables').DataTable( {
				 serverSide : true,
			      processing : true,
			      info:false,
			      pageLength : 10,
                pagingType : "full_numbers",
	    	 ajax: {
			          url: "/db/history",
			          type: "GET", 
			          dataSrc : "data",
			          data : function(d){
			          
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
	                    {"data": "history_no"},
	                    {"data": "db_svc_name"},
	                    {"data": "db_type_no"},
	                    {"data": "db_priv_ip"},
	                    {"data": "db_priv_port"},
	                    {"data": "db_pub_ip"},
	                    {"data": "db_pub_port"},
	                    {"data": "issuer_user_id"},
	                    {"data": "action"},
	                    {"data": "reg_date"}
	                ],
				    columnDefs: [
				    
				    {targets : 2, 
					    render : function(data,type,full,meta){
							if(full.db_type_no == 1){
								return '<span>mariaDB</span>';
							}else {
								return '<span>oracleDB</span>';
							}		
					    }
				    },
		  				{targets : 8,
		  						 render: function (data, type, full, meta) { 
		  						 	if(full.action == "C"){
		  						 		return '<span>생성</span>'; 
		  						 	}else if(full.action == "U"){
		  						 		return '<span>수정</span>'; 
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
})