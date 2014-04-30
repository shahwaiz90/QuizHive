package com.devtuts.quizhive.models;

public  class UserModel  {
   
	public String displayName;
	public String emailAddress; 
	public String token;
	public String password;
	
	public UserModel(){  
		
	} 
	public void setdisplayName(String name){ 
		this.displayName = name;
	}
	public String getDisplayName(){ 
		return this.displayName;
	}
	
	public void setEmailAddress(String str){
		this.emailAddress = str;
	}
	public String getEmailAddress(){
		return this.emailAddress;
	}
	
	public void setUserToken(String str){
		this.token = str;
	}
	public String getUserToken(){
		return this.token;
	}
	
	public void setUserPassword(String str){
		this.password = str;
	}
	public String getUserPassword(){
		return this.password;
	}
}
