/**
 * SkipList Comparison
 * @author Soorya Prasanna Ravichandran
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

//Driver program for comparison

public class SkipListComparison {
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
		Long returnValue = null;
		SkipListImpl<Long> skipList = new SkipListImpl<>();
		// Initialize the timer
		Timer timer = new Timer();	

		timer.start();
		while (!((operation = sc.next()).equals("End"))) {
			int s = skipList.size();
			int maxlevel = (int) Math.ceil((Math.log(s+1) / Math.log(2)));
			if(maxlevel > SkipListImpl.max)
			{
				skipList.rebuild();
			}
			switch (operation) {
			case "Add": {
				operand = sc.nextLong();

				if(skipList.add(operand)) {
					result = (result + 1) % modValue;    
				}
				break;
			}
			case "Remove": {
				operand = sc.nextLong();
				if (skipList.remove(operand) != null) {
					result = (result + 1) % modValue;
				}
				break;
			}
			case "Contains":{
				operand = sc.nextLong();
				if (skipList.contains(operand)) {
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