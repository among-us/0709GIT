 var dateDTO = function (issued_date,zone_name) {
	
	this.issued_date= issued_date;
	this.zone_name= zone_name;
	
	return this;
}

var TotalDTO = function (start_value, end_value,zone_name) {
	this.start_value= start_value
	this.end_value= end_value
	this.zone_name = zone_name
	return this;
}

$(function(){
// main page 최초 접속 후 권한에 따른 접근 제어

	$('#help_toggle1').mouseover(function(){
		$('#help_span1').removeAttr('hidden');
	})
	$('#help_toggle1').mouseout(function(){
		$('#help_span1').attr('hidden', 'hidden');
	})
	
	$('#help_toggle2').mouseover(function(){
		$('#help_span2').removeAttr('hidden');
	})
	$('#help_toggle2').mouseout(function(){
		$('#help_span2').attr('hidden', 'hidden');
	})

		
	$.ajax({
		url : '/main/permission',
		type : 'get',
		success : function(result){
			console.log(result.data)
			
			if(result.data.errorCode == -1){	// no data
				$('.row.mt-2').hide();
				$('.chart-area').hide();
				$('#alertMessage').text(' NO DATA ');
			}
			
			if(result.data[3] == 10){			// permission == group
				$('#company_name').children().remove();
				$('#company_name').attr('disabled', 'true');
				$('select[name="company_name"]').append('<option id="test">'+result.data[1] +'</option>');
				$('#company_name').css("background-color", "#EAECF4");
				
				$('#proj_list').children().remove();
				$('select[name="proj_name"]').append('<option id="test">'+result.data[5] +'</option>');
			}			

			if(result.data[3] == 20){			// permission == project
				$('#company_name').children().remove();
				$('#company_name').attr('disabled', 'true');
				$('select[name="company_name"]').append('<option id="test">'+result.data[1] +'</option>');
				$('#company_name').css("background-color", "#EAECF4");
				
				$('#proj_list').children().remove();
				$('select[name="proj_name"]').append('<option id="test">'+result.data[5] +'</option>');
			}			
		},
		error : function(result){
			alert(JSON.stringify(result));
		}
	})	// permission ajax end
	
	
	// hourly chart -> 현재 날짜 기준으로 차트 호출
	
	var today = new Date();
	var year = today.getFullYear();
	var month = today.getMonth() +1
	var m_length = month.toString().length;
	//console.log("month length > "+m_length)
	
	if(m_length == 1){
		month = "0".concat(month);
		//console.log(month)
	}
	
	var date = today.getDate();
	var d_length = date.toString().length;
	
	if(d_length == 1){
		date = "0".concat(date);
	}
	
	var issued_date = year+"-"+month+"-"+date;
	//console.log('TODAY > '+issued_date)
	$('.card-body.1').hide();
	
	// page 첫 접속 시도시 호출되는 ajax	
	dateObj = new dateDTO(issued_date);
	//console.log("date Object > "+JSON.stringify(dateObj));
	
	$.ajax({
		url : '/chart/hourly/first-access',
		type : 'get',
		data : dateObj,
		success : function(result){
		
			if(result.data.errorCode == -1){
				//$('#myChart').hide();
				//$('.card-body.1').hide();
				//$('#chart1_message').show();
				//$('#chart1_message').text(result.data.errorMessage);
			}else{
				$('.card-body.1').show();
				$('#myChart').show();
				$('#chart1_message').hide();
				
				var length = 24;
				var xValue = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]; // x측
				var static = [];
				var dynamic = [];
				var timelimit = [];
				var data_length = result.data.length;
				
				if(data_length == 1){
					var lic_type = result.data[0].lic_type;
					if(lic_type == 1){
						for(var i=0;i<length;i++){
							var hour = 'h'+i;
							static[i] = result.data[0][hour];
						}
					}
					
					if(lic_type == 2){
						for(var i=0;i<length;i++){
							var hour = 'h'+i;
							dynamic[i] = result.data[0][hour];
						}
					}
					
					if(lic_type == 3){
						for(var i=0;i<length;i++){
							var hour = 'h'+i;
							timelimit[i] = result.data[0][hour];
						}
					}
					
				}// hour length 1 
						
				if(data_length == 2){
					var lic_type0 = result.data[0].lic_type;
					var lic_type1 = result.data[1].lic_type;
					
					if(lic_type0 == 1){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							static[i] = result.data[0][hour]
						}
					} 
					
					if(lic_type0 == 2){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							dynamic[i] = result.data[0][hour]
						}
					} 
					
					if(lic_type0 == 3){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							timelimit[i] = result.data[0][hour]
						}
					} 
					
					if(lic_type1 == 1){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							static[i] = result.data[1][hour]
						}
					} 
					
					if(lic_type1 == 2){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							dynamic[i] = result.data[1][hour]
						}
					} 
					
					if(lic_type1 == 3){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							timelimit[i] = result.data[1][hour]
						}
					} 
					
					
				}// hour length 1 
						
				if(data_length == 3){
					for(var i=0; i <length ;i++){ //length == 23
						var hour = 'h' + i;
						static[i] = result.data[0][hour];
					}		
					for(var i=0; i <length ;i++){ //length == 23
						var hour = 'h' + i;			
						dynamic[i] = result.data[1][hour];
					}		
					for(var i=0; i <length ;i++){ //length == 23
						var hour = 'h' + i;			
						timelimit[i] = result.data[2][hour];
					}		
				}
				
				if(window.chartObj != undefined){
					window.chartObj.destroy();
				}
				window.chartObj = new Chart("myChart", {
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
				 			maintainAspectRatio : true,
						    legend: {display: true},
						    responsive : true,
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
												labelString : "hour",
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
								    			stepSize : 1,
							    				min : 0,
					    						stepSize : 1
								    		},
								    		scaleLabel : {
												display : true,
												labelString : "count",
												fontColor : 'white'
											}
								    	}]
								    }
						  }	//option
			});	//1번 chart end
				} // errorCode -1 else flow
					},// success flow	-- chartObj 존재 O
						error : function(result){
							alert(result)
						}
				})	// ajax end
		
//==========================================================================================================================================================

// 날짜 변경 시
$('#datepicker').change(function(){	
	$('#daily_alert_message').show();
	var zone_name = $('select[name="proj_name"] option:selected').val();
	if(zone_name == "default"){
		$('#daily_alert_message').text('< GROUP과 PROJECT를 선택해주세요 >');
	}else{
	var issued_date = $('#datepicker').val();
		var zone_name = $('select[name="proj_name"] option:selected').val();
		dateObj = new dateDTO(issued_date, zone_name);
		//console.log(dateObj);
		$.ajax({
		url : '/chart/hourly',
		type : 'get',
		data : dateObj,
		success : function(result){
		//console.log("result > "+ JSON.stringify(result));
			if(result.data.errorCode == -1){
				$('#myChart').hide();
				$('.card-body.1').hide();
				$('#chart1_message').show();
				$('#chart1_message').text(result.data.errorMessage);
				$('#help_toggle1').removeAttr('hidden');
		//		$('#none').removeAttr('hidden')
			}else{
				$('.card-body.1').show();
				$('#myChart').show();
				$('#chart1_message').hide();
				$('#help_toggle1').attr('hidden', 'hidden');
		//		$('#none').attr('hidden', 'hidden');
				
				var length = 24;
				var xValue = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]; // x측
				var static = [];
				var dynamic = [];
				var timelimit = [];
				
			var data_length = result.data.length;
		
		if(data_length == 1){
			var lic_type = result.data[0].lic_type;
			if(lic_type == 1){
				for(var i=0;i<length;i++){
					var hour = 'h'+i;
					static[i] = result.data[0][hour];
				}
			}
			
			if(lic_type == 2){
				for(var i=0;i<length;i++){
					var hour = 'h'+i;
					dynamic[i] = result.data[0][hour];
				}
			}
			
			if(lic_type == 3){
				for(var i=0;i<length;i++){
					var hour = 'h'+i;
					timelimit[i] = result.data[0][hour];
				}
			}
			
		}// hour length 1 
		
		if(data_length == 2){
			var lic_type0 = result.data[0].lic_type;
			var lic_type1 = result.data[1].lic_type;
			
			if(lic_type0 == 1){
				for(var i =0 ; i<length ; i++){
					var hour = 'h'+i;
					static[i] = result.data[0][hour]
				}
			} 
			
			if(lic_type0 == 2){
				for(var i =0 ; i<length ; i++){
					var hour = 'h'+i;
					dynamic[i] = result.data[0][hour]
				}
			} 
			
			if(lic_type0 == 3){
				for(var i =0 ; i<length ; i++){
					var hour = 'h'+i;
					timelimit[i] = result.data[0][hour]
				}
			} 
			
			
			if(lic_type1 == 1){
				for(var i =0 ; i<length ; i++){
					var hour = 'h'+i;
					static[i] = result.data[1][hour]
				}
			} 
			
			if(lic_type1 == 2){
				for(var i =0 ; i<length ; i++){
					var hour = 'h'+i;
					dynamic[i] = result.data[1][hour]
				}
			} 
			
			if(lic_type1 == 3){
				for(var i =0 ; i<length ; i++){
					var hour = 'h'+i;
					timelimit[i] = result.data[1][hour]
				}
			} 
		}// hour length 1 
				
		if(data_length == 3){
			for(var i=0; i <length ;i++){ //length == 23
				var hour = 'h' + i;
				static[i] = result.data[0][hour];
			}		
			for(var i=0; i <length ;i++){ //length == 23
				var hour = 'h' + i;			
				dynamic[i] = result.data[1][hour];
			}		
			for(var i=0; i <length ;i++){ //length == 23
				var hour = 'h' + i;			
				timelimit[i] = result.data[2][hour];
			}		
		}

				if(window.chartObj != undefined){
					window.chartObj.destroy();
				}
				
				window.chartObj = new Chart("myChart", {
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
				  maintainAspectRatio : true,
				    legend: {display: true},
				    responsive : true,
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
												labelString : "hour",
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
						    			stepSize : 1,
					    				min : 0,
			    						stepSize : 1
						    		},
						    		scaleLabel : {
										display : true,
										labelString : "count",
										fontColor : 'white'
									}
						    	}]
						    }
				  }	//option
			});	//1번 chart end
				} // errorCode -1 else flow
					},// success flow	-- chartObj 존재 O
						error : function(result){
							alert(result)
						}
				})	// ajax end	
	}// else
})

$('#proj_list').change(function(){
		//console.log('line 351')
				$('#daily_alert_message').hide();
				var issued_date = $('#datepicker').val();
				var zone_name = $('select[name="proj_name"] option:selected').val();
				dateObj = new dateDTO(issued_date, zone_name);
				$.ajax({
			url : '/chart/hourly',
			type : 'get',
			data : dateObj,
			success : function(result){
			
			if(result.data.errorCode == -1){
				$('#myChart').hide();
				$('.card-body.1').hide();
				$('#chart1_message').show();
				$('#chart1_message').text(result.data.errorMessage);
				$('#help_toggle1').removeAttr('hidden');
			//	$('#none').removeAttr('hidden')
			}else{
				$('.card-body.1').show();
				$('#myChart').show();
				$('#chart1_message').hide();
				$('#help_toggle1').attr('hidden', 'hidden');				
			//	$('#none').attr('hidden', 'hidden');
				
				var length = 24;
				var xValue = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]; // x측
				
				var static = [];
				var dynamic = [];
				var timelimit = [];
						
				var hour_length = result.data.length;
				//console.log("24 hour chart length : "+hour_length)
				
				if(hour_length == 1){
					var lic_type = result.data[0].lic_type;
					if(lic_type == 1){
						for(var i=0;i<length;i++){
							var hour = 'h'+i;
							static[i] = result.data[0][hour];
						}
					}
					
					if(lic_type == 2){
						for(var i=0;i<length;i++){
							var hour = 'h'+i;
							dynamic[i] = result.data[0][hour];
						}
					}
					
					if(lic_type == 3){
						for(var i=0;i<length;i++){
							var hour = 'h'+i;
							timelimit[i] = result.data[0][hour];
						}
					}
					
				}// hour length 1 
						
				
				if(hour_length == 2){
					var lic_type0 = result.data[0].lic_type;
					var lic_type1 = result.data[1].lic_type;
					
					if(lic_type0 == 1){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							static[i] = result.data[0][hour]
						}
					} 
					
					if(lic_type0 == 2){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							dynamic[i] = result.data[0][hour]
						}
					} 
					
					if(lic_type0 == 3){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							timelimit[i] = result.data[0][hour]
						}
					} 
					
					
					if(lic_type1 == 1){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							static[i] = result.data[1][hour]
						}
					} 
					
					if(lic_type1 == 2){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							dynamic[i] = result.data[1][hour]
						}
					} 
					
					if(lic_type1 == 3){
						for(var i =0 ; i<length ; i++){
							var hour = 'h'+i;
							timelimit[i] = result.data[1][hour]
						}
					} 
				}// hour length 2
				
				if(hour_length == 3){
					for(var i=0; i <length ;i++){ //length == 23
						var hour = 'h' + i;
						static[i] = result.data[0][hour];
					}		
					for(var i=0; i <length ;i++){ //length == 23
						var hour = 'h' + i;			
						dynamic[i] = result.data[1][hour];
					}		
					for(var i=0; i <length ;i++){ //length == 23
						var hour = 'h' + i;			
						timelimit[i] = result.data[2][hour];
					}		
				}
				
				if(window.chartObj != undefined){
					window.chartObj.destroy();
					//console.log('destroyFLOW');
				}
				
				window.chartObj = new Chart("myChart", {
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
				  maintainAspectRatio : true,
				    legend: {
				    	labels : {
				    		fontColor : "white",
				    		fontSize : 13
				    	},
				    	display: true,
				    },
				    responsive : true,
					  scales:{
						    xAxes: [{
						    		display : true,
						    		gridLines : {
										lineWidth:0.4,
																				
									},
										ticks:{
											fontColor : 'white',
											fontSize : 14
										},
										scaleLabel : {
											display : true,
											labelString : "hour",
											fontColor : 'white'
										}
									}],
						    	yAxes : [{
						    	display : true,
						    	gridLines : {
										lineWidth:0.4,
																				
									},
						    		ticks : {
						    			fontColor : 'white',
						    			fontSize : 14,
						    			stepSize : 1,
					    				min : 0,
			    						stepSize : 1
						    		},
						    		scaleLabel : {
											display : true,
											labelString : "count",
											fontColor : 'white'
									}
						    	}]
						    }
				  }	//option
			});	//1번 chart end
				} // errorCode -1 else flow
					},// success flow	-- chartObj 존재 O
						error : function(result){
							alert(result)
						}
				})	// ajax end
		})
	//	$('#total_none').attr('hidden', 'hidden');
		$('#hourlyTotalBtn').click(function(){
			var st_date = $('#st_datepicker').val();
//			var st_time = $('#st_time').val();
			var start_value = st_date;
			var end_date = $('#end_datepicker').val();
//			var end_time = $('#end_time').val();
			var end_value = end_date;
			var zone_name = $('#proj_list').val();
			totalObj = new TotalDTO(start_value, end_value, zone_name);
			
			
			
		$.ajax({
				url : '/main/total',
				type : 'get',
				data : totalObj,
				success: function(result){
				
							if(window.myPieChart != undefined){
									window.myPieChart.destroy();
							} 
							
							$('#totalChart').show();
							$('#total_alertMessage').hide();
				//			$('#total_none').removeAttr('hidden');
							var total = [];
							
							var static = result.data.total_static;
							var dynamic = result.data.total_dynamic;
							var timelimit = result.data.total_timelimit;
							
							total[0] = static;
							total[1] = dynamic;
							total[2] = timelimit;
							if(total[0] == 0 && total[1] == 0 && total[2] == 0){
								$('#totalChart').hide();
								$('#total_alertMessage').text('해당 조건의 범위에 일치하는 데이터가 존재하지 않습니다');
								$('#total_alertMessage').show();
								$('#total_none').show();
								$('#help_toggle2').removeAttr('hidden');
							}else{
								$('#help_toggle2').attr('hidden', 'hidden');
								
								const labels = ['static','dynamic','timelimit']
								
								if(window.myPieChart != undefined){
									window.myPieChart.destroy();
								} 
								var ctx = document.getElementById('totalChart').getContext('2d');
								window.myPieChart = new Chart(ctx, {
										  type: 'doughnut',
										  data: {
										    labels: labels,
										    datasets: [{
												data: total,
												backgroundColor:['IndianRed','cornflowerblue','#1CC88A'],
												hoverBackgroundColor:  ['FireBrick','dodgerblue','lawngreen'],
												//hoverBorderColor: "rgba(234, 236, 244, 1)",
										    }],
										  },
										  options: {
											    maintainAspectRatio: true,
											    tooltips: {
												      backgroundColor: "rgb(255,255,255)",
												      bodyFontColor: "#858796",
												      borderWidth: 10,
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
						},	//success
						error: function(result){
							alert(JSON.stringify(result));
						}
					})			
	})
	// daily License Count Search End
	
	// >>>>>>>>>>>>>>> datepicker <<<<<<<<<<<<<<<
	$("#datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	});
	$("#st_datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		showAnim: "slide",
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	});
	$("#end_datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		showAnim: "slide",
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	});
// >>>>>>>>>>>>>>> datepicker <<<<<<<<<<<<<<<
})	// function end