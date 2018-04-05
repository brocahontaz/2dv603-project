package controller;

public class ConnectInfo {
	
	private static final String url = "jdbc:mysql://soggarna.se:3306/2dv603";
	private static final String user = "2dv603";
	private static final String password = "b,7XMYC>heXuQt*~";
	
	public ConnectInfo() {
		
	}
	
	public static String getURL() {
		return url;
	}
	
	public static String getUser() {
		return user;
	}
	
	public static String getPassword() {
		return password;
	}

}
