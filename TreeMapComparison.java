/**
 * TreeMap Comparison with SkipList
 * @author Soorya Prasanna Ravichandran
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

//Driver program for comparison

public class TreeMapComparison {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;
		if (args.length > 0) {
			File file = new File(args[0]);
			sc = new Scanner(file);
		} else {
			sc = new Scanner(System.in);
		}
		String operation = "";
		long operand = 0;
		int modValue = 999983;
		long result = 0;
		// Initialize the timer
		Timer timer = new Timer();	

		/*
		 * Can replace TreeMap to TreeSet to avoid hardcoding of
		 * the value field in TreeMap 
		 */
		TreeMap tm = new TreeMap();

		while (!((operation = sc.next()).equals("End"))) {

			switch (operation) {
			case "Add": {
				operand = sc.nextLong();
				int addValue;
				if(!tm.containsKey(operand))
				{
					tm.put(operand, 1);
					addValue = 1;
				}
				else
				{
					addValue = 0;
				}
				result = (result + addValue) % modValue;
				break;
			}
			case "Remove": {
				operand = sc.nextLong();
				int removeValue;
				if(tm.containsKey(operand))
				{
					tm.remove(operand);
					removeValue = 1;
				}
				else
				{
					removeValue = 0;
				}
				result = (result + removeValue) % modValue;
				break;
			}
			case "Contains":{
				operand = sc.nextLong();
				if (tm.containsKey(operand)) 
				{
					result = (result + 1) % modValue;
				}
				break;
			}	
			}
		}
		// End Time
		timer.end();
		System.out.println(result);
		System.out.println(timer);
	}
}