import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.print.attribute.IntegerSyntax;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Scanner in = new Scanner(System.in);
		
		AverageList averageList = new AverageList();
		
		FindAverage findAverage = new FindAverage();
		
		findAverage.getInput(in);
		
		findAverage.findAverage();
		
		
		
		
		
		
		
		
		
		
		
		
//
//		while(integersList.size() < 4) {
//			System.out.println("Please insert a number!");
//			integersList.add(in.nextInt());
//			
//		}
//		
//		integersList.getIntegersList();
		
	

		
		
		in.close();
		
		

	}

}
