/**
 * 
 */

/*
var userDTO = function (user_id, passwd, repasswd){
	this.user_id = user_id;
	this.passwd = passwd;
	this.repasswd = repasswd;
	return this;
}
*/
var userDTO = function (user_id,passwd,repasswd,user_company_name,user_dept_name,user_name,user_email,user_phone_num1,user_phone_num2){
	this.user_id = user_id;
	this.passwd = passwd;
	this.repasswd = repasswd;
	this.user_company_name = user_company_name;
	this.user_dept_name = user_dept_name;
	this.user_name = user_name;
	this.user_email = user_email;
	this.user_phone_num1 = user_phone_num1;
	this.user_phone_num2 = user_phone_num2;
	return this;
}

var UpdateDTO = function (user_no, user_id,perm_no,user_name,comp_no,zone_name,user_company_name,user_dept_name,user_email,user_phone_num1,user_phone_num2,exist) {
	this.user_no = user_no;
	this.user_id = user_id;
	this.perm_no = perm_no;
	this.user_name= user_name;
	this.comp_no= comp_no;
	this.zone_name= zone_name;
	this.user_company_name= user_company_name;
	this.user_dept_name= user_dept_name;
	this.user_email= user_email;
	this.user_phone_num1= user_phone_num1;
	this.user_phone_num2= user_phone_num2;
	this.exist = exist;
	
	return this;
}

var CheckDTO = function (user_id) {
	
	this.user_id = user_id;

	return this;
}

var ProjectDTO = function (comp_no) {
	
	this.comp_no = comp_no;

	return this;
}