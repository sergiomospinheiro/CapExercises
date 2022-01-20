import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.AuthenticationException;

public class FindAverage {

		private Scanner scanner;	
		private AverageList averageList = new AverageList();
		
		
		public void getInput (Scanner in) {
			
			System.out.println("Please insert a number!");
			
			int counter = 0;
			
			while(counter < 4) {
		
				int number = in.nextInt();
				averageList.addIntegersList(number);
				counter++;
			
			} System.out.println("All numbers have been inserted!");
			
		}
		
	
		public void findAverage () {
			Integer total = 0;

			for(int i = 0; i < averageList.getListIntegers().size() -1; i++ ) {
				total += averageList.getListIntegers().get(i);

			}
			
			Integer average = total / averageList.getLastNumber();
			
			System.out.println("The average of the list is: " + average);
			
			
		}
		
		
}
