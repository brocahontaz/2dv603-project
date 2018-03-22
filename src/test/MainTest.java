package test;
import java.util.ArrayList;

import controller.DBParser;

/**
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class MainTest {
	
	public static void main(String[] args) {
		DBParser testParser = new DBParser();
		
		testParser.insertIntoTestTableTest("11", "testarn deluxe!");

		ArrayList<test.testTableClass> test = testParser.getTestTableClass();
		
		for(test.testTableClass testclass : test) {
			System.out.print(testclass.toString());
		}
		
	}
}
