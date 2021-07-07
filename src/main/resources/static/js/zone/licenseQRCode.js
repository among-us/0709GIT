	var generateQRCode = function() {
				var url = "/zone/QR";
				var text = $("b[id='req_value']").text();
				var size = 200;
				var imgObj = document.getElementById("qrcode_img");
				if (text) {
					text = encodeURIComponent(text);
					if (size > 0 && size < 500) {
						url += "?width=" + size + "&height=" + size + "&text="
								+ text;
						imgObj.src = url;
					}
				}
	}
	
	var removeQRCode = function() {
		$('#qrcode_img').hide();
	}
