var CheckDTO = function (ctrl_name) {
	this.ctrl_name= ctrl_name;
	return this;
}

var EnrollDTO = function (ctrl_name, local_ip, local_port, admin_port, watchdog, enable) {
	this.ctrl_name= ctrl_name;
	this.local_ip= local_ip;
	this.local_port= local_port;
	this.admin_port= admin_port;
	this.watchdog= watchdog;
	this.enable= enable;
	return this;
}

var UpdateDTO = function (ctrl_name, local_ip, local_port, admin_port, watchdog, enable) {
	this.ctrl_name= ctrl_name;
	this.local_ip= local_ip;
	this.local_port= local_port;
	this.admin_port= admin_port;
	this.watchdog= watchdog;
	this.enable= enable;
	return this;
}

var ServiceDTO = function (db_svc_name) {

	this.db_svc_name = db_svc_name;

	return this;
}

	
var FileDTO = function (ctrl_name, local_ip, db_svc_name, db_type, db_addr, db_port, db_id,db_pwd, db_name) {
	this.ctrl_name = ctrl_name;
	this.local_ip = local_ip;
	this.db_svc_name = db_svc_name;
	this.db_type = db_type;
	this.db_addr = db_addr;
	this.db_port = db_port;
	this.db_id = db_id;
	this.db_pwd = db_pwd;
	this.db_name = db_name;

	return this;
}