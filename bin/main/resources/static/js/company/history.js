$(function(){
	$('#companyHistoryTable').DataTable( {
				serverSide : true,
				processing : true,
				info:false,
				pageLength : 10,
                searching : true,
                pagingType : "full_numbers",
			    	 ajax: {
					          url: "/company/history",
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
			                    {"data": "comp_name"},
			                    {"data": "issuer_user_id"},
			                    {"data": "action"},
			                    {"data": "reg_date"}
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
			      columnDefs: [
				      	{targets: 0 , width : "6%"},
				      	{targets: 1},
		              	{targets: 2,
		                    className: 'state',
		                    render: function (data, type, full, meta) {
		                        if (full.issuer_user_no == '58') {
		                            return '<span id="allow">admin</span>';
		                        }else {
		                        	return '<span id="allow">'+data+'</span>';
		                        } 
		                    }
		                },	
						{targets: 3, 
							render: function (data,type,full,meta){
							if(full.action == "C"){
								return '<span>생성</span>';
							}else if(full.action =="U"){
								return '<span>변경</span>';
							}else{
								return '<span>삭제</span>';
							}
							
							}//render
						}
            		]
	})// PROJECT DATATABLES END
	
	$('#companyProjectHistoryTables').DataTable( {
				serverSide : true,
				processing : true,
				info:false,
				pageLength : 10,
                //scrollX: true,
                searching : true,
                pagingType : "full_numbers",
			    	 ajax: {
					          url: "/company/project-history",
					          type: "GET", 
					          dataSrc : "data",
					          data : function(d){
							          	var pageNumber = d.start;
							          	d.pageNum = pageNumber;
							          	
							          	var showCount= d.length;
							          	d.show_cnt = showCount;
							          	
							          	var ordering = d.order[0].dir
							          	d.ordering = ordering;
							          	
							          	var filter = $('input[type="search"]').val();
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
			                    {"data": "comp_name"},
			                    {"data": "zone_info"},
			                    {"data": "exist"},
			                    {"data": "issuer_user_id"},
			                    {"data": "reg_date"}
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
	})// PROJECT DATATABLES END
	
})