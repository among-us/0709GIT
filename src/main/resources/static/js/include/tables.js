$(function(){

	 	$('#tables').DataTable( {
		info:false,
		responsive: true,
        order: [ 0, 'desc' ],
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
    });
	 	
	 	$('#DBtables').DataTable( {
		info:false,
		responsive: true,
		paging: true,
		order: [ 0, 'desc' ],
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
    });

	 	$('#tables2').DataTable( {
		info:false,
		responsive: true,
		paging: true,
        ordering : true,
        order: [ 0, 'desc' ],
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
    });

	 	$('#companyTables').DataTable( {
		info:false,
		responsive: false,
		paging: true,
        order: [ 0, 'desc' ],
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
    });
    
    
     $('#companyEnrollTables').DataTable({
     	responsive : true,
    	info:false,
    	responsive : true,
    	searching : true,
    	pagingType : "full_numbers",
    	order : [[0,"desc"]],
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
		columnDefs : [
			{targets: 0, width : "6%"}
		]
    })
});











