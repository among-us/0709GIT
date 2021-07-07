/*
var ManageDTO = function (tam_name,tam_ip, comm_type,db_type,db_ip,db_port,db_id,db_pwd,db_name) {

	this.tam_name = tam_name;
	this.tam_ip = tam_ip ;
	this.comm_type = comm_type;
	this.db_type = db_type;
	this.db_ip = db_ip;
	this.db_port = db_port;
	this.db_id = db_id;
	this.db_pwd = db_pwd;
	this.db_name = db_name;

	return this;
}
*/
var ManageDTO = function (tam_name,tam_ip,comm_type,db_type,db_ip,db_port,db_id,db_pwd,db_name) {

	this.tam_name = tam_name;
	this.tam_ip = tam_ip ;
	this.comm_type = comm_type;
	this.db_type = db_type;
	this.db_ip = db_ip;
	this.db_port = db_port;
	this.db_id = db_id;
	this.db_pwd = db_pwd;
	this.db_name = db_name;

	return this;
}
var PrivateDTO = function (tam_name) {

	this.tam_name = tam_name;

	return this;
}
	
var ServiceDTO = function (db_svc_name) {

	this.db_svc_name = db_svc_name;

	return this;
}
	

	