var EnrollDTO = function (comp_no,zone_no) {
	this.comp_no = comp_no;
	this.zone_no= zone_no;
	return this;
}

var CheckDTO= function (proj_name) {
	this.proj_name = proj_name;
	return this;
}


var MatchDTO = function (comp_no) {
	this.comp_no = comp_no
	return this;
}
/*
var MatchingDTO = function (proj_name, zone_no) {
	this.proj_name = proj_name
	this.zone_no= zone_no
	return this;
}
*/
var MatchingDTO = function (comp_no,comp_name,proj_name, zone_no) {
	this.comp_no= comp_no
	this.comp_name = comp_name
	this.proj_name = proj_name
	this.zone_no= zone_no
	return this;
}

var ProjctInfoDTO = function (comp_name,  zone_name, exist, reg_date) {
	this.comp_name= comp_name
	this.zone_name = zone_name
	this.exist= exist
	this.reg_date= reg_date
	return this;
}

var ProjUpdateDTO = function (comp_no, zone_no) {
	this.comp_no= comp_no
	this.zone_no = zone_no
	return this;
}

