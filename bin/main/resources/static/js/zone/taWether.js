$(function(){

$('.table.display.nowrap.zone tbody tr').click(function() {
    $(this).addClass('bg-primary').siblings().removeClass('bg-primary');
    $(this).addClass('text-white').siblings().removeClass('text-white');
});

  $('#licenseTables').DataTable({
	  	info:false,
	  	bPaginate: true,
	  	searching : true,
	 	language : {
			           search: "<b>ZONE 이름을 검색해주세요 : </b>",
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
  });
  
var ntable =  $('#nowStatus').DataTable({
  	info:false,
  	bPaginate: false,
  	searching : false,
  	ordering: false, // 너비 맞추려고 없앰 굳이 필요없음 
}); 
  

//     $('.table.table-bordered.display.nowrap.zone tbody tr').click(function() {
     $('#licenseTables tbody tr').click(function() {
	         var tr = $(this);
	         var td = tr.children();
	         var zone_no = td.eq(1).text();
	         $('#zone_no').val(zone_no);
	         
	         wetherObj = new WetherDTO(zone_no);
	         
		          $.ajax({
		              url: '/zone/agent-wether',
		              type: 'GET',
		              data: wetherObj,
		              success: function(cntdto) {
		              
			              	$("#pl_allow").text(cntdto[0]);
			              	$("#tpl_allow").text(cntdto[1]);
			              	$("#pl_publish").text(cntdto[2]);
			              	$("#tpl_publish").text(cntdto[3]);
			              	if(cntdto[4] == "null"){
			              		$("#pl_deny").text("0");	
			              	}else{
				              	$("#pl_deny").text(cntdto[4]);
			              	}
			              	
			              	if(cntdto[5] == "null"){
			              		$("#tpl_deny").text("0");	
			              	}else{
				              	$("#tpl_deny").text(cntdto[5]);
			              	}	
			              	var zone_no= $('#zone_no').val();
			              	plLicense(zone_no);
							tplLicense(zone_no);
							tmLicense(zone_no);
			              },
			              error: function() {
			                Swal.fire('실패', '서버와의 통신에 실패하였습니다', 'error');
			              }
		          })
		});   
// ============================== TIMELICENSE ===================================================================================================
		
		
     $('#licenseTables tbody tr').click(function() {
     
	         var tr = $(this);
	         var td = tr.children();
	         var zone_no = td.eq(1).text();
	         $('#zone_no').val(zone_no);
	         
	         //console.log(zone_no);
	         
	         timeObj = new TimeDTO(zone_no);
	         
		          $.ajax({
		              url: '/zone/agent-timelicense',
		              type: 'GET',
		              data: timeObj,
		              success: function(result) {
		              	//console.log('success')
			              	console.log(result);
			              	$('#tl_allow').text(result.data[0]);
			              	$('#tl_publish').text(result.data[1]);
			              	$('#tl_deny').text(result.data[2]);
				              	plLicense(zone_no);
								tplLicense(zone_no);
								tmLicense(zone_no);
			              },
			              error: function (result) {
			              	$('#tl_allow').text('0');
			              	$('#tl_publish').text('0');
			              	$('#tl_deny').text('0');
			              }
		          })
		});   
		
// ============================== TIMELICENSE ===================================================================================================
		/* ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ ORIGIN ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ
     $('.table.table-bordered.display.nowrap.zone tbody tr').click(function() {
     
	         var tr = $(this);
	         var td = tr.children();
	         var zone_no = td.eq(1).text();
	         $('#zone_no').val(zone_no);
	         
	         wetherObj = new WetherDTO(zone_no);
	         
		          $.ajax({
		              url: '/zone/agent-wether',
		              type: 'GET',
		              data: wetherObj,
		              success: function(cntdto) {
		              
			              	$("#pl_allow").text(cntdto[0]);
			              	$("#tpl_allow").text(cntdto[1]);
			              	$("#pl_publish").text(cntdto[2]);
			              	$("#tpl_publish").text(cntdto[3]);
			              	if(cntdto[4] == "null"){
			              		$("#pl_deny").text("0");	
			              	}else{
				              	$("#pl_deny").text(cntdto[4]);
			              	}
			              	
			              	if(cntdto[5] == "null"){
			              		$("#tpl_deny").text("0");	
			              	}else{
				              	$("#tpl_deny").text(cntdto[5]);
			              	}
			              	$("#tl_allow").text(cntdto[6]);
			              	$("#tl_publish").text(cntdto[7]);
			              	$("#tl_deny").text(cntdto[8]);
			              	
			              	var zone_no= $('#zone_no').val();
			              	plLicense(zone_no);
							tplLicense(zone_no);
							
			              },
			              error: function (d) {
			                Swal.fire('실패', '서버와의 통신에 실패하였습니다', 'error');
			              }
		          })
		});   
*/


// ============================== RENEWAL ===================================================================================================

	$('#renewal').click(function(){			// renewal Button 
			dtable.ajax.reload();
	 		ttable.ajax.reload();
	 		tmtable.ajax.reload();
			nowStat();
		//location.reload();
	});
				
				
// ============================== RENEWAL===================================================================================================

	
});
// ============================== FUNCTION ===================================================================================================

function renewal(){
	dtable.ajax.reload();
	ttable.ajax.reload();
	tmtable.ajax.reload();
	nowStat();
}

function ntablerefresh(){
	ntable.fnClearTable(); 
	ntable.fnAddData(); 
}
	
var dtable;
function plLicense(zone_no) {
    var req_no = "zone_no=" + $('#zone_no').val();
    
    if (dtable != null) {
        dtable.ajax.reload();
        var rows = dtable
		    .rows()
		    .remove()
		    .draw();
    }else {
        dtable = $('#plTables').DataTable({
            //serverSide: true,
            bAutoWidth : true,
            info: false,
            bPaginate: true,
            bLengthChange: false,
            ordering: false,
            searching: false,
            lengthMenu: [
                5, 10, 15, 20
            ],
            retrieve: true, // ?

            ajax: {
                url: "/agent/pl-license",
                type: "GET",
                data: function (d) {
                	var req_no = "zone_no="+$('#zone_no').val();
                	return req_no;
                },
                dataSrc: function(d){
                	return d.data === undefined ? [] : d.data;
                	//return d.data;
                } ,
                dataFilter: function (d) {
                    ////console.log(d);
                    return d;
                },
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8;'
            },
            columns: [
                {
                    "data": "taa_ip"
                }, {
                    "data": "taa_hostname"
                }, {
                    "data": "registed_date"
                }, {
                    "data": "allow_state"
                }
            ],
            columnDefs: [
                {
                    targets: 0,
                    className: 'text-center'
                }, {
                    targets: 1,
                    className: 'text-center'
                }, {
                    targets: 2,
                    className: 'text-center'
                }, {
                    targets: 3,
                    className: 'text-center state',
                    render: function (data, type, full, meta) {
                        if (data == 1) {
                            return '<span id="allow" class="text-primary" style="font-weight : 700;">허용</span>';
                        } else {
                            return '<span class="text-danger" style="font-weight : 700">거부</span>';
                        }
                    }
                }, {
                    targets: 4,
                    className: 'text-center',
                    render: function (data, type, full, meta) { 
	                    if(full.allow_state == "1"){// 해당되는 버튼 하나만
		                       return  '<button id="rejectBtn" style="border : 2px solid #ccc" class="btn btn-light mr-3" onclick= "reject( \'' + full.taa_ip +'\')">거부</button>';
		                       //'<button disabled id="allowBtn"  class="btn btn-dark"      onclick=  "allow( \'' + full.taa_ip +'\')">허용</button>'+
	                    }//if end test
	                    else{
	                    	 return '<button id="allowBtn" style="border : 2px solid #ccc" class="btn btn-light mr-3" onclick=  "allow( \'' + full.taa_ip +'\')">허용</button>';
	                    	 //+'<button disabled id="rejectBtn" class="btn btn-dark ml-3" onclick= "reject( \'' + full.taa_ip +'\')">거부</button>'
	                    }// else end test
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
        })
    }
}

var ttable;
function tplLicense(zone_no){

	////console.log("Function tpl license success");
	var req_no = "zone_no="+$('#zone_no').val();
		
	if (ttable != null) {
        ttable.ajax.reload();
        var rows = ttable
    .rows()
    .remove()
    .draw();
    }else {
		ttable = $('#tplTables').DataTable({
			bAutoWidth : true,
		  	info:false,
		  	bPaginate: true,
		  	bLengthChange: false, 
		  	ordering : false,
		  	searching : false,
		  	pageLength : 5,
	  		ajax: {
	  			url:"/agent/tpl-license",
	  			type:"GET",
	  			data : function(d) {
	  			var req_no = "zone_no="+$('#zone_no').val();
	  				return req_no;
	  			},
	  			//dataSrc : 'data',
	  			/*
	  			//console.log(d);
	  				if (d.data === undefined) d.data = [];
	  				return d.data;
	  			*/
	  			dataSrc : function(d) {
	  				return d.data === undefined ? [] : d.data;
	  			},
	  			dataFilter : function(d) {
	  				return d;
	  			},
	  			contentType: 'application/x-www-form-urlencoded; charset=UTF-8;'
	  		},
	  		columns : [
						{"data": "taa_ip"},
						{"data": "taa_hostname"},
						{"data": "registed_date"},
						{"data": "allow_state"}
	  		],
	  		columnDefs: [
		  				{targets : 0, className : 'text-center'},
		  				{targets : 1, className : 'text-center'},
		  				{targets : 2, className : 'text-center'},
		  				{targets : 3, className : 'text-center',
			  			render : function(data, type, full, meta){
			  					if(data == 1){
			  						return '<span class="text-primary" style="font-weight : 700;">허용</span>';
			  					}else{
			  						return '<span class="text-danger" style="font-weight : 700;">거부</span>';
			  					}
			  				}
		  				},
		  				{targets : 4,
		  				className : 'text-center',
		  						 render: function (data, type, full, meta) { 
	                    if(full.allow_state == "1"){// 해당되는 버튼 하나만
		                       return  '<button id="rejectBtn" style="border : 2px solid #ccc"  class="btn  btn-light mr-3 " onclick= "reject( \'' + full.taa_ip +'\')">거부</button>';
		                       //'<button disabled id="allowBtn"  class="btn btn-dark"      onclick=  "allow( \'' + full.taa_ip +'\')">허용</button>'+
	                    }//if end test
	                    else{
	                    	 return '<button id="allowBtn" style="border : 2px solid #ccc"  class="btn btn-light mr-3"      onclick=  "allow( \'' + full.taa_ip +'\')">허용</button>';
	                    	 //+'<button disabled id="rejectBtn" class="btn btn-dark ml-3" onclick= "reject( \'' + full.taa_ip +'\')">거부</button>'
	                    }// else end test
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
		})
	}
}
      
var tmtable;
function tmLicense(zone_no){

	////console.log("Function tpl license success");
	var req_no = "zone_no="+$('#zone_no').val();
		
	if (tmtable != null) {
        tmtable.ajax.reload();
        var rows = tmtable
    .rows()
    .remove()
    .draw();
    }else {
		tmtable = $('#tmTables').DataTable({
			bAutoWidth : true,
		  	info:false,
		  	bPaginate: true,
		  	bLengthChange: false, 
		  	ordering : false,
		  	searching : false,
		  	pageLength : 5,
	  		ajax: {
	  			url:"/agent/time-license",
	  			type:"GET",
	  			data : function(d) {
	  			var req_no = "zone_no="+$('#zone_no').val();
	  				return req_no;
	  			},
	  			dataSrc : function(d) {
	  				return d.data === undefined ? [] : d.data;
	  			},
	  			dataFilter : function(d) {
	  				return d;
	  			},
	  			contentType: 'application/x-www-form-urlencoded; charset=UTF-8;'
	  		},
	  		columns : [
						{"data": "taa_ip"},
						{"data": "taa_hostname"},
						{"data": "registed_date"},
						{"data": "allow_state"}
	  		],
	  		columnDefs: [
		  				{targets : 0, className : 'text-center'},
		  				{targets : 1, className : 'text-center'},
		  				{targets : 2, className : 'text-center'},
		  				{targets : 3, className : 'text-center',
			  			render : function(data, type, full, meta){
			  					if(data == 1){
			  						return '<span class="text-success" style="font-weight : 700;">허용</span>';
			  					}else{
			  						return '<span class="text-danger" style="font-weight : 700;">거부</span>';
			  					}
			  				}
		  				},
		  				{targets : 4,
		  				className : 'text-center',
		  						 render: function (data, type, full, meta) { 
	                    if(full.allow_state == "1"){// 해당되는 버튼 하나만
		                       return  '<button id="rejectBtn"  style="border : 2px solid #ccc" class="btn  btn-light mr-3 " onclick= "reject( \'' + full.taa_ip +'\')">거부</button>';
		                       //'<button disabled id="allowBtn"  class="btn btn-dark"      onclick=  "allow( \'' + full.taa_ip +'\')">허용</button>'+
	                    }//if end test
	                    else{
	                    	 return '<button id="allowBtn"  style="border : 2px solid #ccc" class="btn btn-light mr-3"      onclick=  "allow( \'' + full.taa_ip +'\')">허용</button>';
	                    	 //+'<button disabled id="rejectBtn" class="btn btn-dark ml-3" onclick= "reject( \'' + full.taa_ip +'\')">거부</button>'
	                    }// else end test
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
		})
	}
}   
      
      
      
      
      
      
      
      
	
	// =================================================================================================================================
	
	function allow(taa_ip) {
		var zone_no = $('#zone_no').val();
		var allow_state = 1;
		var taa_ip = taa_ip;
		
		// zone no 중복가능하니 구분할것이 필요 -> taa_ip가 적합 ?
		// zone no랑 taaip가 일치하는 유저의 allow_state를 변경해줘야한다
		
		allowObj = new allowDTO(zone_no, allow_state, taa_ip);
		
		$.ajax({
            url:"/zone/wether-allow", 
            type:"PUT",
            data: allowObj,
            success:function(result){
            	if(result.data.errorCode == 0){
            	 	dtable.ajax.reload();
            	 	ttable.ajax.reload();
            	 	tmtable.ajax.reload();
            	 	
            		Swal.fire('성공','변경 성공', 'success')
            	}
            	if(result.data.errorCode == 2004) {
            		window.location.href = "/"
            	}
            },
            error:function(result) {
            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
        
  	     renewal();
        
	}
	
	function reject(taa_ip) {
		var taa_ip = taa_ip
		var zone_no = $('#zone_no').val();
		var reject_state = 2;
		rejectObj = new rejectDTO(zone_no, reject_state ,taa_ip);
		
		$.ajax({
            url:"/zone/wether-reject", 
            type:"PUT",
            data: rejectObj,
            success:function(result){
            	if(result.data.errorCode == 0) {
            		
            	 	dtable.ajax.reload();
            	 	ttable.ajax.reload();
            	 	tmtable.ajax.reload();
            		Swal.fire('성공','변경 성공', 'success')
            	}
            	if(result.data.errorCode == 2004) {
            		window.location.href = "/"
            	}
            },
            error:function() {
            	Swal.fire('경고','서버와의 통신에 실패하였습니다','error');
            }
        })
        renewal();
	}
	
	function nowStat(){
			var zone_no = $('#zone_no').val();
			 wetherObj = new WetherDTO(zone_no);
	         
		          $.ajax({
		              url: '/zone/agent-wether',
		              type: 'GET',
		              data: wetherObj,
		              success: function(cntdto) {
		              
			              	$("#pl_allow").text(cntdto[0]);
			              	$("#tpl_allow").text(cntdto[1]);
			              	$("#pl_publish").text(cntdto[2]);
			              	$("#tpl_publish").text(cntdto[3]);
			              	if(cntdto[4] == "null"){
			              		$("#pl_deny").text("0");	
			              	}else{
				              	$("#pl_deny").text(cntdto[4]);
			              	}
			              	
			              	if(cntdto[5] == "null"){
			              		$("#tpl_deny").text("0");	
			              	}else{
				              	$("#tpl_deny").text(cntdto[5]);
			              	}	
			              	var zone_no= $('#zone_no').val();
			              	plLicense(zone_no);
							tplLicense(zone_no);
							tmLicense(zone_no);
			              },
			              error: function (d) {
			                Swal.fire('실패', '서버와의 통신에 실패하였습니다', 'error');
			              }
		          })
		          
		          // timelicense 
		          
		          timeObj = new TimeDTO(zone_no);
	         
		          $.ajax({
		              url: '/zone/agent-timelicense',
		              type: 'GET',
		              data: timeObj,
		              success: function(result) {
		              	//console.log('success')
			              	console.log(result);
			              	$('#tl_allow').text(result.data[0]);
			              	$('#tl_publish').text(result.data[1]);
			              	$('#tl_deny').text(result.data[2]);
				              	plLicense(zone_no);
								tplLicense(zone_no);
								tmLicense(zone_no);
			              },
			              error: function (result) {
			              	$('#tl_allow').text('0');
			              	$('#tl_publish').text('0');
			              	$('#tl_deny').text('0');
			              }
		          })
		          
		          
			
	}
	
function myFunction() {
		var input, filter, table, tr, td, i, txtValue;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("licenseTables");
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