var FileInfoDTO = function (tam_name, tam_ip, comm_type,db_type,db_addr,db_port, db_id, db_pwd, db_name,hmac){
	this.tam_name = tam_name;
	this.tam_ip = tam_ip;
	this.comm_type = comm_type;
	this.db_type = db_type;
	this.db_addr = db_addr;
	this.db_port = db_port;
	this.db_id = db_id;
	this.db_pwd = db_pwd;
	this.db_name = db_name;
	this.hmac = hmac;

	return this;
}