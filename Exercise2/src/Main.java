
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);

		FindAverage findAverage = new FindAverage();

		findAverage.getInput(in);

		findAverage.findAverage();

		in.close();

	}

}
