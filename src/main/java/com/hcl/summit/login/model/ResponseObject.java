package com.hcl.summit.login.model;

import java.io.Serializable;

public class ResponseObject  implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = 5501223265992301581L;
private  User obj;
 private String messege;
 private String respcode;
public User getObj() {
	return obj;
}
public void setObj(User obj) {
	this.obj = obj;
}
public String getMessege() {
	return messege;
}
public void setMessege(String messege) {
	this.messege = messege;
}
public String getRespcode() {
	return respcode;
}
public void setRespcode(String respcode) {
	this.respcode = respcode;
}
@Override
public String toString() {
	return "ResponseObject [obj=" + obj + ", messege=" + messege + ", respcode=" + respcode + "]";
}
}
