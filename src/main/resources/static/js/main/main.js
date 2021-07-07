$(function(){
	
	// now
	var today = new Date();
	console.log(today)
	var year = today.getFullYear();
	var month = today.getMonth();
	var date = today.getDate();
	
	var hours = today.getHours();
	var minutes = today.getMinutes();
	var seconds = today.getSeconds();
	
	//$('#now').text(year+"/"+month+"/"+date+" "+hours+":"+minutes+":"+seconds+ " 기준");
	
	// company_name Change function Start	
	$('#company_name').change(function(){
		var comp_no = $("#company_name option:selected").val();
		console.log("COMP NUMBER >>> "+comp_no);
		getObj = new MainMatchingDTO(comp_no);
		console.log("GET OBJECT >>> "+JSON.stringify(getObj));
	$.ajax({
			url : '/main/group',
			type : 'get',
			data : getObj,
			success : function(d){
				if(d.data.errorCode == -1){
					$('#proj_list').children().remove();
					$('#proj_list').append('<option value="none">해당 없음</option>');
				}else{
					$('#proj_list').children().remove();
					$('#proj_list').append('<option value="default">-- 선택 --</option>');
					for(var i =0;i<d.data.length;i++){
						$('#proj_list').append('<option>'+d.data[i]+'</option>');
					}
				}
			}
		}) 
	});	
	
	
	/*
	//	daily select box option
	var month = [];
	for(var i =1; i<13;i++){
		month[i] = i; 	
		$('#daily_select_month').append(
			'<option value='+i+'>'+ i +'월 </option>'
		)
		$('#daily_selected_month').append(
			'<option value='+i+'>'+ i +'월 </option>'
		)
	}
	
	
	$('#daily_selected_month').change(function(){
		var year = $('#daily_selected_year').val();
		var month = $('#daily_selected_month').val();
		
		if(month.length == 1){
			month = 0+month;
		}
		
		var issued_yymm = year.concat(month);

		dailyObj = new DailyDTO(issued_yymm);
		
		$.ajax({
			url : '/main/daily',
			type : 'get',
			data : dailyObj,
			success : function(result){
				var length = 31;
				var xValue = [];				
				for(var i =0;i<length;i++){
					xValue[i] = (i+1);
				}
				var static = [];
				var dynamic = [];
				var timelimit = [];
				
				
				try{
					if(result.data.length == 1){
						var lic_type = result.data[0].lic_type;
					
						if(lic_type == 1){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								static[i-1] = result.data[0][day];
							}//for 
						}// if lic type 1
						
						if(lic_type == 2){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								dynamic[i-1] = result.data[0][day];
							}//for 
						}// if lic type 2
						
						if(lic_type == 3){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								timelimit[i-1] = result.data[0][day];
							}//for 
						}// if lic type 3
						
					}// if length 1
					
					
					if(result.data.length == 2){
					
					console.log("length 2 flow")
						
						var lic_type_0 = result.data[0].lic_type;
						var lic_type_1 = result.data[1].lic_type;
					
					console.log("[0] type =  "+lic_type_0 +"[1] type =  " + lic_type_1);
					// lic length == 2 / 2 of 1 
					
					
						if(lic_type_0 == 1){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								static[i-1] = result.data[0][day];
							}//for 
						}// if lic type 1
						
						if(lic_type_0 == 2){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								dynamic[i-1] = result.data[0][day];
							}//for 
						}// if lic type 2
						
						if(lic_type_0 == 3){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								timelimit[i-1] = result.data[0][day];
							}//for 
						}// if lic type 3
					
					// lic length == 2 / 2 of 1 
					
						
						if(lic_type_1 == 1){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								static[i-1] = result.data[1][day];
							}//for 
						}// if lic type 1
						
						if(lic_type_1 == 2){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								dynamic[i-1] = result.data[1][day];
							}//for 
						}// if lic type 2
						
						if(lic_type_1 == 3){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								timelimit[i-1] = result.data[1][day];
							}//for 
						}// if lic type 3
					}// if length 2
								
					
					if(result.data.length == 3){
						for(var i=1;i<=length;i++){
							var day = 'd'+i;
							static[i-1] = result.data[0][day];
						}// static 
					
						for(var i=1;i<=length;i++){
							var day = 'd'+i;
							dynamic[i-1] = result.data[1][day];
						}// static 
					
						for(var i=1;i<=length;i++){
							var day = 'd'+i;
							timelimit[i-1] = result.data[2][day];
						}// static 
						
					}// if length 3
					

			// chart
			
			const labels = ['static','dynamic','timelimit']
				
			if(window.dailyCtx != undefined){
				window.dailyCtx.destroy();
			}
			var daily = document.getElementById('dailyChart').getContext('2d');
			window.dailyCtx = new Chart(daily, {
				  type: "line",
				  data: {
				    labels: xValue,
				    datasets: [{ 
					      label : 'static',
					      data: static,
					      borderColor: "IndianRed",
					      fill: false
					    }, { 
					      label : 'dynamic',
					      data: dynamic,
					      borderColor: "blue",
					      fill: false
					    }, { 
					      label : 'timelimit',
					      data: timelimit,
					      borderColor: "#1CC88A",
					      fill: false
					    }]	
				  },
				  options: {
				 	maintainAspectRatio: false, // div 맞춰 그려줌 ? 
				    legend: {display: true},
				    title : {
				    	display : true
				    },
				    scales:{
				    	yAxes : [{
				    		ticks : {
				    			stepSize : 1
				    		}
				    	}]
				    }
							    
				  }	//option
			});	//1번 chart end
					//chart  end 
					
					}// big try
				catch (e){
				}// big catch
				
				
			},
			error : function(result){
				
			}
		})//ajax end
	})
	
	// daily project change function start
	
	$('#daily_proj_list').change(function(){
		var year = $('#daily_selected_year').val();
		var month = $('#daily_selected_month').val();
		
		if(month.length == 1){
			month = '0'+month;
		}
		
		var issued_yymm = year.concat(month);

		dailyTotalObj = new DailyTotalDTO(issued_yymm);
		
		$.ajax({
			url : '/daily/total',
			type : 'get',
			data : dailyTotalObj,
			success : function(result){
				console.log('daily total ajax flow')
				
				console.log(result.data);
				
				var total = [];
				
				var static = result.data.daily_total_static;
				var dynamic = result.data.daily_total_dynamic;
				var timelimit = result.data.daily_total_timelimit;
				
				if(static == 0 && dynamic == 0 && timelimit == 0){
					$('#dailyTotalChart').hide();
					$('#d_total_alertMessage').show();
					$('#d_total_alertMessage').text('해당 조건의 범위에 일치하는 데이터가 존재하지 않습니다');
				}else{
					$('#dailyTotalChart').show();
					$('#d_total_alertMessage').hide();
					
					total[0] = static;
					total[1] = dynamic;
					total[2] = timelimit;
					
					console.log(static);
					console.log(dynamic);
					console.log(timelimit);
					
					console.log(total);
					// doughnut total chart 
					const labels = ['static','dynamic','timelimit']
					
					if(window.dailyTotalCtx != undefined){
						window.dailyTotalCtx.destroy();
					}
					var dailyTotal = document.getElementById('dailyTotalChart').getContext('2d');
					window.dailyTotalCtx = new Chart(dailyTotal, {
							  type: 'doughnut',
							  data: {
							    labels: labels,
							    datasets: [{
							      data: total,
							      backgroundColor:['IndianRed','cornflowerblue','#1CC88A'],
							      hoverBackgroundColor:  ['FireBrick','dodgerblue','lawngreen'],
							      hoverBorderColor: "#ccc",
							    }],
							  },
							  options: {
							    maintainAspectRatio: false,
							    tooltips: {
							      backgroundColor: "rgb(255,255,255)",
							      bodyFontColor: "#858796",
							      borderColor: '#dddfeb',
							      borderWidth: 1,
							      xPadding: 15,
							      yPadding: 15,
							      displayColors: false,
							      caretPadding: 10,
							    },
							    cutoutPercentage: 80,
							    plugins:{
							    	legend : {
							    		position : 'bottom'
							    	}
							    }
							  },
					});
			}
					
					
				},
				error : function(result){
					alert(result);
				}
		})
		
	})
	
	
	// daily project change function end
	
	*/
	
	
	
	
})// function end 



var MainMatchingDTO = function (comp_no) {
	this.comp_no = comp_no
	return this;
}

var TotalDTO = function (start_value, end_value,zone_name) {
	this.start_value= start_value
	this.end_value= end_value
	this.zone_name = zone_name
	return this;
}


