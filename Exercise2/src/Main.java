import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.print.attribute.IntegerSyntax;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Scanner in = new Scanner(System.in);
		
		FindAverage findAverage = new FindAverage();
		
		findAverage.getListIntegers();
		
		findAverage.getInput(in);
		
		findAverage.findAverage();
		
		in.close();
		
		

	}

}
