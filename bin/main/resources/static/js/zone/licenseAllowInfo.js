
$(function(){



$('#licenseInfoTables').DataTable( {
				serverSide : true,
				processing : true,
				info:false,
				pageLength : 10,
				scrollX: true,
				pagingType : "full_numbers",
			ajax: {
			          url: "/zone/license",
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
						//	var filter = $('#myInput').val();
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
	                    {"data": "zone_no"},
	                    {"data": "zone_name"},
	                    {"data": "zone_info"},
	                    {"data": "pl_license_cnt"},
	                    {"data": "Pub_static"},
	                    {"data": "tpl_license_cnt"},
	                    {"data": "Pub_dynamic"},
	                    {"data": "allowed_cnt"},
	                    {"data": "Pub_timelimit"},
	                    {"data": "limited_period"},
	                    {"data": "session_time"},
	                    {"data": "exist"},
	                    {"data": "revision_no"},
	                    {"data": "limited_url"}
	                ],
             columnDefs :[
	         	{targets : 4, 
			  			render : function(data, type, full, meta){
			  					if(full.pub_static == null){
			  						return '<span>0</span>';
			  					}else{
			  						return full.pub_static;
			  					}
		  				}
  				},
  				{targets : 6, 
			  			render : function(data, type, full, meta){
			  					if(full.pub_dynamic == null){
			  						return '<span>0</span>';
			  					}else{
			  						return full.pub_dynamic;
			  					}
		  				}
  				},
  				{targets : 7, 
			  			render : function(data, type, full, meta){
			  					if(full.allowed_cnt == null){
			  						return '<span>0</span>';
			  					}else{
			  						return full.allowed_cnt;
			  					}
		  				}
  				},
  				{targets : 8, 
			  			render : function(data, type, full, meta){
			  					if(full.pub_timelimit== null){
			  						return '<span>0</span>';
			  					}else{
			  						return full.pub_timelimit;
			  					}
		  				}
  				},
  				{targets : 12, 
  					"visible" : false
  				},
  				{targets : 13, 
  					"visible" : false
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
	
	
	
	
	
	
	

//$('.table.table-bordered.display.nowrap.zone tbody tr').click(function() {
/*
$('#licenseInfoTables tr').click(function() {
    $(this).addClass('bg-primary').siblings().removeClass('bg-primary');
    $(this).addClass('text-white').siblings().removeClass('text-white');
});
*/
	

// 라이선스 요청 정보 list 클릭시 modal창에 들어갈 값 
	
	$("#licenseInfoTables").on("click","tr", function(){
		  
		var zone_name = $(this).find("td:eq(1)").text();
		nameObj = new nameDTO(zone_name);
					  
		  $.ajax({
		  	url : "/zone/other",
		  	type : "get",
		  	data : nameObj,
		  	success : function(result){
		  	
		  		var zone_name = result.data.zone_name;
				var re_no = result.data.revision_no;
				var pl_cnt = result.data.pl_license_cnt;
				var tpl_cnt = result.data.tpl_license_cnt;
				var limited_url = result.data.limited_url;
				
				$('#integrityInfo').on('show.bs.modal', function(){
					$('#zone_name')[0].innerText = zone_name;
					$('#revision_no')[0].innerText = re_no;
					$('#zone_pl_count')[0].innerText = pl_cnt;
					$('#zone_tpl_count')[0].innerText = tpl_cnt;
					$('#zone_limited_url')[0].innerText = limited_url;
				});
				$('#integrityInfo').modal('show');
		  	},
		  	error : function(result){
		  		alert(result.data);
		  	}
		  })
	});
});

/*
function myFunction() {
		var input, filter, table, tr, td, i, txtValue;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("licenseInfoTables");
		tr = table.getElementsByTagName("tr");
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[1];
			if (td) {
				txtValue = td.textContent || td.innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}
			*/
			
/*
				var as = $('<a href='+ 'data:attachment/text,'+ encodeURI(str) + 'target="_blank" download='+ filename +'>');
				
				as.click(function(e){
					alert('success');
				})
				
*/      
     
    /*
      	function saveAsFile(str, filename) {
      		
	        var hiddenElement = document.createElement('a');
	        
	        hiddenElement.href = 'data:attachment/text,' + encodeURI(str);
	        hiddenElement.target = '_blank';
	        hiddenElement.download = filename;
	        hiddenElement.click();
	        
			console.log(hiddenElement);
			
      }
      */
      
      

var nameDTO = function (zone_name) {
	this.zone_name = zone_name;

	return this;
}
      
$("#file_download").on("click", function() {
	var request_value = $("b[id='req_value']").text();
	saveAsFile(request_value, "tas_req_zone_info.txt");
});
  
function saveAsFile(str, filename) {
	var hiddenElement = document.createElement('a');
	hiddenElement.href = 'data:attachment/text,' + encodeURI(str);
	hiddenElement.target = '_blank';
	hiddenElement.download = filename;
	hiddenElement.click();
}
		
		