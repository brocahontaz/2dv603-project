package model;

public class testTableClass {
	
	private int testid;
	private String testname;
	
	public testTableClass(int testid, String testname) {
		this.testid = testid;
		this.testname = testname;
	}
	
	public int gettestid() {
		return this.testid;
	}
	
	public String gettestname() {
		return this.testname;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("--------------\n");
		sb.append("id: " + this.testid + "\n");
		sb.append("name: " + this.testname + "\n");
		
		return sb.toString();
	}

}
