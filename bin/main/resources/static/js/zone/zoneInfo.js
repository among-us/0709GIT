/**
 * 
 */



var EnrollDTO = function (zone_name, zone_info,session_time) {

	this.zone_name = zone_name;
	this.zone_info = zone_info;
	this.session_time = session_time;
	//this.pl_license_cnt = pl_license_cnt;
	//this.tpl_license_cnt = tpl_license_cnt;
//	this.comp_no = comp_no; 
//	this.integrity_value = integrity_value;
	return this;
}

var UpdateDTO = function (zone_no, zone_name, zone_info, pl_license_cnt, tpl_license_cnt, session_time, exist) {

	this.zone_no = zone_no;
	this.zone_name = zone_name;
	this.zone_info = zone_info;
	this.pl_license_cnt = pl_license_cnt;
	this.tpl_license_cnt = tpl_license_cnt;
	this.session_time = session_time;
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


var VerifyDTO = function (zone_name,asis_revision_no, asis_pl_count,asis_tpl_count,asis_limited_url, tobe_pl_count,tobe_tpl_count,tobe_limited_url) {
	
	this.zone_name= zone_name;
	this.asis_revision_no = asis_revision_no;
	this.asis_pl_count= asis_pl_count;
	this.asis_tpl_count= asis_tpl_count;
	this.asis_limited_url= asis_limited_url;
	this.tobe_pl_count= tobe_pl_count;
	this.tobe_tpl_count= tobe_tpl_count;
	this.tobe_limited_url= tobe_limited_url;
	
	return this;
}

var CountDTO = function (pl_count,tpl_count) {
	this.pl_count= pl_count;
	this.tpl_count= tpl_count;

	return this;
}


