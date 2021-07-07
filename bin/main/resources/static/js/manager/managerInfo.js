/**
 * 
 */


var EnrollDTO = function (tam_name, site_info, tam_local_ip,
		tam_local_port, tam_admin_port,tam_pub_ip,tam_pub_port,tam_pub_admin_port,
		shared_path, comm_type, watchdog) {

	this.tam_name = tam_name;
	this.site_info = site_info;
	this.tam_local_ip = tam_local_ip;
	this.tam_local_port = tam_local_port;
	this.tam_admin_port = tam_admin_port;
	this.tam_pub_ip = tam_pub_ip;
	this.tam_pub_port = tam_pub_port;
	this.tam_pub_admin_port = tam_pub_admin_port;
	this.shared_path = shared_path;
	this.comm_type = comm_type;
	this.watchdog = watchdog;

	return this;
}


var UpdateDTO = function (tam_no, tam_name, site_info, tam_local_ip,
		tam_local_port, tam_admin_port,tam_pub_ip,tam_pub_port,tam_pub_admin_port, tam_license, shared_path,  comm_type, watchdog, exist) {

	this.tam_no = tam_no;
	this.tam_name = tam_name;
	this.site_info = site_info;
	this.tam_local_ip = tam_local_ip;
	this.tam_local_port = tam_local_port;
	this.tam_admin_port = tam_admin_port;
	this.tam_pub_ip = tam_pub_ip;
	this.tam_pub_port = tam_pub_port;
	this.tam_pub_admin_port = tam_pub_admin_port;
	this.tam_license = tam_license;
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

var IntegrityDTO = function (tam_name, tam_local_ip, tam_local_port, tam_admin_port, tam_license) {

	this.tam_name = tam_name;
	this.tam_local_ip = tam_local_ip;
	this.tam_local_port = tam_local_port;
	this.tam_admin_port = tam_admin_port;
	this.tam_license = tam_license;

	return this;
}


var CreateDTO = function (tam_name,tam_ip,comm_type,db_type,db_ip,db_port,db_id,db_pwd,db_name) {

	this.tam_name = tam_name;
	this.tam_ip = tam_ip;
	this.comm_type = comm_type;
	this.db_type = db_type;
	this.db_ip = db_ip;
	this.db_port = db_port;
	this.db_id = db_id;
	this.db_pwd = db_pwd;
	this.db_name - db_name;
	
	 	
	return this;
}

var ManaDBDTO = function (db_svc_name,tam_name, db_type_no,db_priv_ip,db_priv_port,db_pub_ip,db_pub_port,db_id,db_passwd,db_name){

	this.db_svc_name = db_svc_name;
	this.tam_name = tam_name;
	this.db_type_no = db_type_no;
	this.db_priv_ip = db_priv_ip;
	this.db_priv_port = db_priv_port;
	this.db_pub_ip = db_pub_ip;
	this.db_pub_port = db_pub_port;
	this.db_id = db_id;
	this.db_passwd = db_passwd;
	this.db_name = db_name;
	 	
	return this;
}

var DBCheckDTO = function (db_svc_name) {
	this.db_svc_name = db_svc_name;

	return this;
}





