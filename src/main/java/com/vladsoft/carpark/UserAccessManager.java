package com.vladsoft.carpark;

import org.apache.log4j.Logger;

class UserAccessManager {
	static final Logger logger=Logger.getLogger(LoginController.class);
	public DatabaseManager databaseManager;
	private String userName;	
	private String userRole;
	private String permitName[] = {"gatecontrol","flux","statistics","history","admin"};
	private Boolean permit[]= {false,false,false,false,false};
	
	
	UserAccessManager(String userName, String userRole){
		super();
		this.databaseManager= new DatabaseManager();
		this.userName=userName;
		this.userRole=userRole;
		ParseRole(this.userRole);
	}
	
	public String getUserName() {
		return userName;
	}
	public String getUserRole() {
		return userRole;
	}
	
	public String printPermits(){
		String s="";
		for(int i=0;i<permit.length;i++) {
			if(permit[i]) {
				s=s.concat("1");
			}
			else s=s.concat("0");
		}		
		return s;
	}
	
	public String AccessPage(int pageId) {
		try {
			if(permit[pageId].equals(true)){
				return permitName[pageId];
			}
			else {
				logger.warn("User "+ userName + " was forbidden to page" + permitName[pageId]);
				return "pageforbidden";
			}
		}
		catch(Exception e){
			logger.error("User "+ userName + " encountered unexpected error on page" + permitName[pageId]);
			return "login";			
		}
	}
	
	private void GrantPermit(int id) {permit[id]=true;}
	private void RevokePermit(int id){permit[id]=false;}
	
	
	private void ParseRole(String userRole) {		
		for(int i=0;i<permit.length;i++) {
			RevokePermit(i);
		}
		switch(userRole){
			case "admin":
				for(int i=0;i<permit.length;i++) {
					GrantPermit(i);
				}
				break;				
			case "operator":
				GrantPermit(0);
				GrantPermit(1);
				GrantPermit(3);
				break;
			default: break;			
		}	
	}	
	
}
