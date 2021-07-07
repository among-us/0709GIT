var FileInfoDTO = function (zone_name, tam_local_ip_1, tam_local_port_1,tam_local_ip_2,tam_local_port_2, taa_ip, license, hmac) {
	this.zone_name = zone_name;
	this.tam_local_ip_1 = tam_local_ip_1;
	this.tam_local_port_1 = tam_local_port_1;
	this.tam_local_ip_2 = tam_local_ip_2;
	this.tam_local_port_2 = tam_local_port_2;
	this.taa_ip = taa_ip;
	this.license = license;
	this.hmac = hmac;

	return this;
}

var TimeInfoDTO = function (zone_name, allowed_ip,allowed_cnt,limited_period, icv) {
	this.zone_name = zone_name;
	this.allowed_ip = allowed_ip;
	this.allowed_cnt = allowed_cnt;
	this.limited_period = limited_period;
	this.icv = icv;

	return this;
}

var TimeHistoryDTO = function (zone_name, allowed_ip,allowed_cnt,limited_period) {
	this.zone_name = zone_name;
	this.allowed_ip = allowed_ip;
	this.allowed_cnt = allowed_cnt;
	this.limited_period = limited_period;

	return this;
}

var ParseDTO = function (value) {
	this.value = value;

	return this;
}