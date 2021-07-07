var UpdateDTO = function (db_no, db_svc_name, db_type_no, db_priv_ip, db_priv_port, db_pub_ip, db_pub_port, db_id, db_passwd, db_name) {

	this.db_no = db_no;
	this.db_svc_name = db_svc_name;
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

var DeleteDTO = function ( db_svc_name) {

	this.db_svc_name = db_svc_name;
	
	return this;
}

