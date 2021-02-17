/* 
var checkDTO = function (zone_name, revision_no, pl_count, tpl_count, limit_url, integrity){
	this.zone_name= zone_name;
	this.revision_no = revision_no;
	this.pl_count= pl_count;
	this.tpl_count= tpl_count;
	this.limit_url= limit_url;
	this.integrity = integrity;
	
	return this;
}

*/



var checkDTO = function (zone_name, revision_no, pl_license_cnt, tpl_license_cnt, limited_url,integrity_value) {
	
	this.zone_name= zone_name;
	this.revision_no = revision_no;
	this.pl_license_cnt= pl_license_cnt;
	this.tpl_license_cnt= tpl_license_cnt;
	this.limited_url= limited_url;
	this.integrity_value = integrity_value;
	
	return this;
}

