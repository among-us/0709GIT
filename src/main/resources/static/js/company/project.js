$(function(){

	$('#enrollBtn').click(function() {
	
			var comp_no = $('select[name="company_name"]').val();
			var zone_no = $('select[name="proj_name"]').val();
			
			if(comp_no == "default" || comp_no == "" || zone_no == "default" || zone_no == ""){
				Swal.fire('','GROUP과 PROJECT 모두 선택해주세요','error');
			}else{
			
			enrollObj = new EnrollDTO(comp_no, zone_no);
			
				$.ajax({
		            url:'/company/project',
		            type:'post',
		            data: enrollObj,
		            success:function(result){
		            console.log(result);
		            	if(result.data.errorCode == 0) {
			            	Swal.fire('성공','등록 성공', 'success')
							.then((value) => {
			            		window.location.href = "/company/enrollProjectPage"
							});
		            	}else if(result.data == true) {
			            	Swal.fire('실패','중복된 프로젝트입니다', 'error')
		            	}
		            },
		            error:function() {
		            	Swal.fire('','서버와의 통신에 실패하였습니다','error')
		            }
		        }) // ajax
			}
	}) // enrollbtn click end
			
		$('#companyProjectTables tbody').on('click', 'tr', function(){
			var comp_name = $(this).find("td:eq(0)").text();
			var zone_name = $(this).find("td:eq(1)").text();
			var exist = $(this).find("td:eq(2)").text();
			var reg_date = $(this).find("td:eq(3)").text();
			
			projObj = new ProjctInfoDTO(comp_name, zone_name, exist, reg_date);
				
			goProjectUpdatePage(comp_name);
		});
		
		$('#companyProjectTables').DataTable( {
				serverSide : true,
				processing : true,
				info:false,
				pageLength : 10,
                pagingType : "full_numbers",
			    	 ajax: {
					          url: "/company/project-list",
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
					          },
					          dataFilter : function(d){
					          		var json = JSON.parse(d);
					          		json.recordsTotal = json.totalCount;
					          		json.recordsFiltered = json.totalCount;
					          		return JSON.stringify(json);
					          },
					},
					columns : [
			                    {"data": "comp_name"},
			                    {"data": "zone_name"},
			                    {"data": "exist"},
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
			      }
	})// PROJECT DATATABLES END
	
	$('#updateBtn').click(function() {
		
		var comp_no = $('input[name="comp_no"]').val();
		var zone_no = $('select[name="proj_name"] option:selected').val();
		var proj_name = $('#proj_list option:selected').text();
		projUpdateObj = new ProjUpdateDTO(comp_no, zone_no);
		
		
			$.ajax({
			    url:'/project',
			    type:'put',
			    data: projUpdateObj,
			    success:function(result){
			    if(result.data.errorCode == -1){
			    	Swal.fire('',result.data.errorMessage,'error');
			    }else {
			    	Swal.fire('','변경 완료','success').then((value) =>{
			    		window.location.href ="/company/enrollProjectPage";
			    	});
			    }
			    },
			    error:function(result) {
			    	Swal.fire('','서버와의 통신에 실패하였습니다','error');
			    }
			})
	});


	
}) // function end


function ValidateCompName(name) {
	if (/^[가-힣|a-z|A-Z|0-9|_]+$/.test(name)) {
		return ("true")
	}else{
		return ("false")
	}
}

function goProjectUpdatePage(comp_name) {
	document.writeln('<form name=companyForm action=/project/updatePage METHOD=GET>');
	document.writeln('    <input name=comp_name type=hidden>');
	document.writeln('</form>');
	document.companyForm.comp_name.value=comp_name;
	document.companyForm.submit();
}
