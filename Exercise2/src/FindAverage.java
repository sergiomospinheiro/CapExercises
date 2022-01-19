import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.AuthenticationException;

public class FindAverage {

		private Scanner scanner;
		private int counter;
		
		private List<Integer> listIntegers;
		private Integer dividerInteger;
		
		public FindAverage() {
			
			listIntegers = new ArrayList<Integer>();

		}
		
		
		public void getInput (Scanner in) {
			
			
			System.out.println("Please insert a number!");
			
			while(counter < 4) {
		
				int number = in.nextInt();
				addIntegersList(number);
				counter++;
			
			} System.out.println("All numbers have been inserted!");
			
		}
		
		
		public void addIntegersList (Integer number) {
			listIntegers.add(number);
					
		}
		
		public Integer getDividerInteger () {
			Integer dividerInteger = listIntegers.get(3);
			return dividerInteger;
			
		}
		
		public List<Integer> getListIntegers() {
			return listIntegers;
		}
		
		
		
		public void findAverage () {
			Integer total = 0;
			for(int i = 0; i < listIntegers.size() -1; i++ ) {
				total += listIntegers.get(i);
			}
			
			Integer average = total / getDividerInteger();
			
			System.out.println("The average of the list is: " + average);
			
			
		}
		
		
}
