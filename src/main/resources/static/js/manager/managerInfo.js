/**
 * 
 */


var EnrollDTO = function (tam_name, site_info, tam_local_ip,
		tam_local_port, tam_admin_port, shared_path, log_path, log_level, 
		shared_path, comm_type, watchdog) {

	this.tam_name = tam_name;
	this.site_info = site_info;
	this.tam_local_ip = tam_local_ip;
	this.tam_local_port = tam_local_port;
	this.tam_admin_port = tam_admin_port;
/*	this.tam_license = tam_license;*/
	this.log_path = log_path;
	this.log_level = log_level;
	this.shared_path = shared_path;
	this.comm_type = comm_type;
	this.watchdog = watchdog;
/*	this.tam_state = tam_state;*/

/*	this.hmac = hmac;*/

	return this;
}


var UpdateDTO = function (tam_no, tam_name, site_info, tam_local_ip,
		tam_local_port, tam_admin_port, tam_license, shared_path, log_path, log_level, comm_type, watchdog, exist) {

	this.tam_no = tam_no;
	this.tam_name = tam_name;
	this.site_info = site_info;
	this.tam_local_ip = tam_local_ip;
	this.tam_local_port = tam_local_port;
	this.tam_admin_port = tam_admin_port;
	this.tam_license = tam_license;
	this.log_path = log_path;
	this.log_level = log_level;
	this.shared_path = shared_path;
	this.comm_type = comm_type;
	this.watchdog = watchdog;
/*	this.tam_state = tam_state;*/
	this.exist = exist;
	
	return this;
}


var CheckDTO = function (tam_name) {
	this.tam_name = tam_name;

	return this;
}

var DeleteDTO = function (tam_no) {
	this.tam_no = tam_no;

	return this;
}








