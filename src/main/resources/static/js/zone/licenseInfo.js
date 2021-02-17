/**
 * 
 */

var ZoneLicenseDTO = function (zone_no, taa_ip, license_type) {
	
	this.zone_no = zone_no;
	this.taa_ip = taa_ip;
	this.license_type = license_type;	

	return this;
}

var ZoneLicenseUpdateDTO = function (
		zone_no, asis_taa_ip, asis_license_type, taa_ip, license_type) {
	
	this.zone_no = zone_no;
	this.asis_taa_ip = asis_taa_ip;
	this.asis_license_type = asis_license_type;
	this.taa_ip = taa_ip;
	this.license_type = license_type;

	return this;
}

