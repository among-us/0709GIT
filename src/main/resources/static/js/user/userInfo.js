/**
 * 
 */


var userDTO = function (user_id, passwd, repasswd, comp_no){
	this.user_id = user_id;
	this.passwd = passwd;
	this.repasswd = repasswd;
	this.comp_no = comp_no;
	return this;
}

var UpdateDTO = function (user_no, permissions, comp_no, exist) {
	this.user_no = user_no;
	this.permissions = permissions;
	this.comp_no = comp_no;
	this.exist = exist;
	
	return this;
}

var CheckDTO = function (user_id) {
	
	this.user_id = user_id;

	return this;
}