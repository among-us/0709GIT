/**
 * 
 */

var EnrollDTO = function (comp_name, group_no) {
	this.comp_name = comp_name;
	this.group_no = group_no;
	return this;
}


var UpdateDTO = function (comp_no, comp_name, group_no) {
	this.comp_no = comp_no;
	this.comp_name = comp_name;
	this.group_no = group_no;
	
	return this;
}


var CompanyLicenseDTO = function (c_comp_no, z_zone_no) {
	this.c_comp_no = c_comp_no
	this.z_zone_no = z_zone_no
	return this;
}


