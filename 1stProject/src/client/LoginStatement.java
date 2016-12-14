package client;

import vo.Member;

public class LoginStatement {
	private static Member loginUser;

	private LoginStatement() {}

	public static void setLogInUser(Member loginUser){
		LoginStatement.loginUser = loginUser;
	}
	
	public static void setLogOutUser(){
		LoginStatement.loginUser = null;
	}
	
	public static Member getLoginUser(){
      return loginUser;
	}
}
