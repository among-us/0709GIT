var WetherDTO = function (zone_no) {
	this.zone_no = zone_no;

	return this;
}

var PlLicenseDTO = function (zone_no) {
	this.zone_no = zone_no;

	return this;
}

var allowDTO = function (zone_no, allow_state, taa_ip) {
	this.zone_no = zone_no;
	this.allow_state = allow_state;
	this.taa_ip = taa_ip;

	return this;
}
var rejectDTO = function (zone_no, reject_state,taa_ip) {
	this.zone_no = zone_no;
	this.reject_state = reject_state;
	this.taa_ip = taa_ip;

	return this;
}

var TimeDTO = function (zone_no) {
	this.zone_no = zone_no;

	return this;
}