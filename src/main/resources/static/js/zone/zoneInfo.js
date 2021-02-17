/**
 * 
 */



var EnrollDTO = function (zone_name, zone_info, pl_license_cnt,
		tpl_license_cnt, session_time, comp_no, integrity_value) {

	this.zone_name = zone_name;
	this.zone_info = zone_info;
	//this.pl_license_cnt = pl_license_cnt;
	//this.tpl_license_cnt = tpl_license_cnt;
	this.session_time = session_time;
	this.comp_no = comp_no; 
	this.integrity_value = integrity_value;
	
	return this;
}

var UpdateDTO = function (zone_no, zone_name, zone_info, comp_no, pl_license_cnt,
		tpl_license_cnt, session_time, integrity_value, exist) {

	this.zone_no = zone_no;
	this.zone_name = zone_name;
	this.zone_info = zone_info;
	this.comp_no = comp_no;
	this.pl_license_cnt = pl_license_cnt;
	this.tpl_license_cnt = tpl_license_cnt;
	this.session_time = session_time;
	this.integrity_value = integrity_value;
	this.exist = exist;

	return this;
}

var DeleteDTO = function (zone_no) {
	
	this.zone_no = zone_no;

	return this;
}

var CheckDTO = function (zone_name) {
	
	this.zone_name = zone_name;

	return this;
}


var VerifyDTO = function (zone_name, revision_no, pl_count, tpl_count, limit_url, integrity) {
	
	this.zone_name= zone_name;
	this.revision_no = revision_no;
	this.pl_count= pl_count;
	this.tpl_count= tpl_count;
	this.limit_url= limit_url;
	this.integrity = integrity;
	
	return this;
}



