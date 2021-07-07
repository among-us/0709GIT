$(function(){
    
    
    // 라이선스 등록 / 변경 이력 - 라이선스 변경 테이블 ( 타임라이선스 X )
    
		$('#licensehistoryTables').DataTable( {
				 serverSide : true,
			      processing : true,
			      info:false,
			      pageLength : 10,
			      responsive : true,
                scrollX: true,
                pagingType : "full_numbers",
                lengthChange : true,
	    	 ajax: {
			          url: "/zone/license-history",
			          type: "GET", 
			          dataSrc : "data",
			          data : function(d){
				          	var pageNumber = d.start;
				          	d.pageNum = pageNumber;
				          	
				          	var showCount= d.length;
				          	d.show_cnt = showCount;
				          	
				          	var ordering = d.order[0].dir;
				          	d.ordering = ordering;
				          	
							var index = d.order[0].column;
							var column = d.columns[index].data;
							d.column = column;
							
							var filter = $('input[type=search]').val();
					        d.filter = filter;
					        
			          },
			          dataFilter : function(d){
			          		var json = JSON.parse(d);
			          		json.recordsTotal = json.totalCount;
			          		json.recordsFiltered = json.totalCount;
			          		return JSON.stringify(json);
			          },
			},
			columns : [
	                    {"data": "license_history_no"},
	                    {"data": "zone_name"},
	                    {"data": "taa_ip"},
	                    {"data": "license_type"},
	                    {"data": "issuer_user_id"},
	                    {"data": "action"},
	                    {"data": "history_reg_date"}
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
			      },
	         columnDefs :[
	         	{targets : 3, 
			  			render : function(data, type, full, meta){
			  					if(full.license_type == "1"){
			  						return '<span>Static</span>';
			  					}else if(full.license_type == "2"){
			  						return '<span>Dynamic</span>';
			  					}else {
			  						return '<span>Timelimit</span>';
			  					}
			  				}
		  				},
	         	{targets : 5, 
			  			render : function(data, type, full, meta){
			  					if(full.action == "C"){
			  						return '<span>생성</span>';
			  					}else if(full.action == "U"){
			  						return '<span>수정</span>';
			  					}else {
			  						return '<span>삭제</span>';
			  					}
			  				}
		  				}
	         ]
	})
		
		
		$('#timelicensehistoryTables').DataTable( {
				 serverSide : true,
			      processing : true,
			      info:false,
			      pageLength : 10,
                searching: true,
                responsive : true,
                scrollX: true,
                pagingType : "full_numbers",
	    
	    	 ajax: {
			          url: "/zone/timelicense-history",
			          type: "GET", 
			          dataSrc : "data",
			          data : function(d){
					          	var pageNumber = d.start;
					          	d.pageNum = pageNumber;
					          	
					          	var showCount= d.length;
					          	d.show_cnt = showCount;
					          	
					          	var ordering = d.order[0].dir;
					          	d.ordering = ordering;
					          	
					          	var index = d.order[0].column;
								var column = d.columns[index].data;
								d.column = column;
								
								var filter = $('#timelicensehistoryTables_filter').children().children().val();
						        d.filter = filter;
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
	                    {"data": "zone_name"},
	                    {"data": "allowed_ip"},
	                    {"data": "allowed_cnt"},
	                    {"data": "limited_period"},
	                    {"data": "issuer_user_no"},
	                    {"data": "action"},
	                    {"data": "history_reg_date"}
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
			      },
			       columnDefs :[
	         		{targets : 6, 
			  			render : function(data, type, full, meta){
			  					if(full.action == "C"){
			  						return '<span>생성</span>';
			  					}else if(full.action == "U"){
			  						return '<span>수정</span>';
			  					}else {
			  						return '<span>삭제</span>';
			  					}
			  				}
		  				}
	         ]
	})

	
		
        
})	// function end