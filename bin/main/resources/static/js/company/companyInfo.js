var EnrollDTO = function (comp_name) {
	this.comp_name = comp_name;
	return this;
}

var UpdateDTO = function (comp_no, comp_name) {
	this.comp_no = comp_no;
	this.comp_name = comp_name;
	
	return this;
}

var CompanyLicenseDTO = function (c_comp_no, z_zone_no) {
	this.c_comp_no = c_comp_no
	this.z_zone_no = z_zone_no
	return this;
}

var CheckDTO = function (comp_name) {
	this.comp_name = comp_name
	return this;
}

var updateCheckDTO = function (comp_no, comp_name,asis_comp_name) {
	this.comp_no = comp_no
	this.comp_name = comp_name
	this.asis_comp_name = asis_comp_name
	return this;
}
