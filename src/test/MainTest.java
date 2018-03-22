package test;
import java.util.ArrayList;

import controller.DBParser;

public class MainTest {
	
	public static void main(String[] args) {
		DBParser testParser = new DBParser();
		
		testParser.insertIntoTestTableTest("11", "testarn deluxe!");

		ArrayList<model.testTableClass> test = testParser.getTestTableClass();
		
		for(model.testTableClass testclass : test) {
			System.out.print(testclass.toString());
		}
		
	}
}
