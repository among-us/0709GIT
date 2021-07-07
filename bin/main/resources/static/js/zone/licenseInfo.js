/**
 * 
 */

var ZoneLicenseDTO = function (zone_no, taa_ip, license_type) {
	
	this.zone_no = zone_no;
	this.taa_ip = taa_ip;
	this.license_type = license_type;	

	return this;
}

var ZoneLicenseUpdateDTO = function (zone_no, taa_ip, asis_taa_ip, license_type) {
	
	this.zone_no = zone_no;
	this.taa_ip = taa_ip;
	this.asis_taa_ip = asis_taa_ip;
	this.license_type = license_type;

	return this;
}

var ZoneLicenseDeleteDTO = function (zone_no, taa_ip, license_type) {
	
	this.zone_no = zone_no;
	this.taa_ip = taa_ip;
	this.license_type = license_type;

	return this;
}

var CreateDTO = function (zone_name,tam_local_ip_1,tam_local_port_1,taa_ip,license) {
	
	this.zone_name= zone_name;
	this.tam_local_ip_1 = tam_local_ip_1;
	this.tam_local_port_1= tam_local_port_1;
	this.taa_ip= taa_ip;
	this.license= license;
	
	return this;
}

var CreatecDTO = function (zone_name,tam_local_ip_1,tam_local_port_1,tam_local_ip_2,tam_local_port_2,taa_ip,license) {
	
	this.zone_name= zone_name;
	this.tam_local_ip_1 = tam_local_ip_1;
	this.tam_local_port_1= tam_local_port_1;
	this.tam_local_ip_2 = tam_local_ip_2;
	this.tam_local_port_2= tam_local_port_2;
	this.taa_ip= taa_ip;
	this.license= license;
	
	return this;
}

var EncryptDTO = function (tam_local_ip_1) {
	
	this.tam_local_ip_1 = tam_local_ip_1;
	//this.tam_local_port_1= tam_local_port_1;
	
	return this;
}

var ValueDTO = function (value) {
	
	this.value= value;
	
	return this;
}
var YesDTO = function (zone_name,revision_no,pl_license_cnt,tpl_license_cnt,limited_url,integrity_value) {
	
	this.zone_name= zone_name;
	this.revision_no= revision_no;
	this.pl_license_cnt= pl_license_cnt;
	this.tpl_license_cnt= tpl_license_cnt;
	this.limited_url= limited_url;
	this.integrity_value= integrity_value;
	
	return this;
}

var AllowDTO = function (zone_name,revision_no,pl_license_cnt,tpl_license_cnt,limited_url,integrity_value) {
	
	this.zone_name= zone_name;
	this.revision_no= revision_no;
	this.pl_license_cnt= pl_license_cnt;
	this.tpl_license_cnt= tpl_license_cnt;
	this.limited_url= limited_url;
	this.integrity_value= integrity_value;
	
	return this;
}

var PrivateDTO = function (tam_name) {
	
	this.tam_name= tam_name;
	
	return this;
}