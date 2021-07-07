$(function(){
	var month = [];
	for(var i =1; i<13;i++){
		month[i] = i; 	
		$('#daily_selected_month').append(
			'<option value='+i+'>'+ i +'월 </option>'
		)
	}
	var today = new Date();
	var year = today.getFullYear();
	
	for(var i=year; i<=2050;i++){
		$('#daily_selected_year').append(
			'<option value ='+i+'>'+i+'년 </option>'
		)
		$('#daily_selected_year').append()
	}

	$('#daily_company_name').change(function(){
		var comp_no = $("#daily_company_name option:selected").val();
		getObj = new MainMatchingDTO(comp_no);
	$.ajax({
			url : '/main/group',
			type : 'get',
			data : getObj,
			success : function(d){
				if(d.data.errorCode == -1){
					$('#daily_proj_list').children().remove();
					$('#daily_proj_list').append('<option value="none">해당 없음</option>');
				}else{
					$('#daily_proj_list').children().remove();
					$('#daily_proj_list').append('<option value="default">-- 선택 --</option>');
					for(var i =0;i<d.data.length;i++){
						$('#daily_proj_list').append('<option>'+d.data[i]+'</option>');
					}
				}
			}
		}) 
	});	
	
	
	
//	$('#chartArea').hide();
	
	
	$('#dailySearchBtn').click(function(){
		$('#chartArea').removeAttr('hidden');
		
		var year = $('#daily_selected_year').val();
		var month = $('#daily_selected_month').val();
		if(month.length == 1){
			month = 0+month;
		}
		var issued_yymm = year.concat(month);
		var zone_name = $('#daily_proj_list').val();
		dailyObj = new DailyDTO(issued_yymm,zone_name);
		$.ajax({
			url : '/chart/daily',
			type : 'get',
			data : dailyObj,
			success : function(result){
			
			if(result.data.errorCode == -1){
				$('#chartArea').hide();
				$('#dailyChart').hide();
				$('#daily_alertMessage').text(result.data.errorMessage);
				$('#daily_alertMessage').show();
			}else{
				$('#chartArea').show();
				$('#dailyChart').show()
				$('#daily_alertMessage').hide();
			
				var length = 31;
				var static = [];
				var dynamic = [];
				var timelimit = [];
				var xValue = [];				
				
				for(var i =0;i<length;i++){
					xValue[i] = (i+1);
				}
				try{
					if(result.data.length == 1){
						var lic_type = result.data[0].lic_type;
					
						if(lic_type == 1){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								static[i-1] = result.data[0][day];
							}
						}
						
						if(lic_type == 2){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								dynamic[i-1] = result.data[0][day];
							}
						}
						
						if(lic_type == 3){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								timelimit[i-1] = result.data[0][day];
							} 
						}
					}
					
					if(result.data.length == 2){
						var lic_type_0 = result.data[0].lic_type;
						var lic_type_1 = result.data[1].lic_type;
						
						if(lic_type_0 == 1){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								static[i-1] = result.data[0][day];
							} 
						}
						if(lic_type_0 == 2){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								dynamic[i-1] = result.data[0][day];
							} 
						}
						if(lic_type_0 == 3){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								timelimit[i-1] = result.data[0][day];
							} 
						}
						
						if(lic_type_1 == 1){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								static[i-1] = result.data[1][day];
							} 
						}
						if(lic_type_1 == 2){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								dynamic[i-1] = result.data[1][day];
							} 
						}
						if(lic_type_1 == 3){
							for(var i =1;i<=length;i++){
								var day = 'd'+i;
								timelimit[i-1] = result.data[1][day];
							} 
						}
					}// if length 2
					
					if(result.data.length == 3){
						for(var i=1;i<=length;i++){
							var day = 'd'+i;
							static[i-1] = result.data[0][day];
						} 
					
						for(var i=1;i<=length;i++){
							var day = 'd'+i;
							dynamic[i-1] = result.data[1][day];
						} 
					
						for(var i=1;i<=length;i++){
							var day = 'd'+i;
							timelimit[i-1] = result.data[2][day];
						} 
					}// if length 3
			
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
							      fill: true,
							      borderWidth : 1
							    }, { 
							      label : 'dynamic',
							      data: dynamic,
							      borderColor: "cornflowerblue",
							      fill: true,
							      borderWidth : 1
							    }, {
							      label : 'timelimit',
							      data: timelimit,
							      borderColor: "#1CC88A",
							      fill: true,
							      borderWidth : 1
							    }]	
						  },
						  options: {
						 	maintainAspectRatio: true, 	 
						 	responsive : true,
						    legend: {
						    	display: true,
						    	labels : {
						    		padding : 10
						    	}
						    },
						    title : {
						    	display : true
						    },
						    scales:{
						    xAxes: [{
						    		gridLines : {
										lineWidth:0.4
																				
									},
										ticks:{
											fontColor : 'white',
											fontSize : 14
										},
										scaleLabel : {
												display : true,
												labelString : "day",
												fontColor : 'white'
										}
									}],
						    	yAxes : [{
						    	gridLines : {
										lineWidth:0.4
																				
									},
						    		ticks : {
						    			fontColor : 'white',
						    			fontSize : 14,
						    			stepSize : 1
						    		},
						    		scaleLabel : {
										display : true,
										labelString : "count",
										fontColor : 'white'
									}
						    	}]
						    }
						  }
					});	
							}// big try
						catch (e){
						}// big catch
				
				}// else end
			},
			error : function(result){
				
			}
		}) // /main/daily ajax flow end
		
		
		var year = $('#daily_selected_year').val();
		var month = $('#daily_selected_month').val();
		
		if(month.length == 1){
			month = '0'+month;
		}
		
		var issued_yymm = year.concat(month);
		var zone_name = $('#daily_proj_list').val();
		dailyTotalObj = new DailyTotalDTO(issued_yymm,zone_name);
		
		$.ajax({
			url : '/daily/total',
			type : 'get',
			data : dailyTotalObj,
			success : function(result){
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
							      hoverBorderColor: '#ccc',
							    }],
							  },
							  options: {
							    maintainAspectRatio: true,
							    responsive : true,
							    tooltips: {
								      backgroundColor: "rgb(255,255,255)",
								      bodyFontColor: "#858796",
								      borderColor: 'white',
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
		})// /daily/total ajax flow end 
		
	})// dailySearchBtn click function end
})


var DailyDTO = function (issued_yymm,zone_name) {
	this.issued_yymm = issued_yymm
	this.zone_name = zone_name
	return this;
}
var DailyTotalDTO = function (issued_yymm,zone_name) {
	this.issued_yymm = issued_yymm
	this.zone_name = zone_name
	return this;
}
var MainMatchingDTO = function (comp_no) {
	this.comp_no = comp_no
	return this;
}


