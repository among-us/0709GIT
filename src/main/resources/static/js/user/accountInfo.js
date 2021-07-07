/**
 * 
 */

//var UpdateDTO = function (user_id, passwd, repasswd) {
var UpdateDTO = function (user_id, passwd, repasswd, user_company_name, user_dept_name,user_name,user_email,user_phone_num1,user_phone_num2) {
	
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

