package com.vladsoft.carpark;

abstract public class LoginService {
	public static UserAccessManager validateUser(String user, String pass) {
		if (user.toLowerCase().equals("vlad")&&pass.equals("4321")) {
			return new UserAccessManager("Vlad","admin");
		}
		else if (user.toLowerCase().equals("laura")&&pass.equals("1234")) {
			return new UserAccessManager("Laura","operator");
		}
		else if (user.toLowerCase().equals("vali")&&pass.equals("1111")) {
			return new UserAccessManager("Valentina","operator");
		}
		else return null;
	}
}
