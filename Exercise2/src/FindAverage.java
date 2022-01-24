import java.util.Scanner;

public class FindAverage {

	private AverageList averageList = new AverageList();

	/*
	 * A method that receives 4 numbers as input and adds them to a list
	 */

	public void getInput(Scanner in) {

		System.out.println("Please insert a number!");

		int counter = 0;

		while (counter < 4) {

			int number = in.nextInt();
			averageList.addIntegersList(number);
			counter++;

		}
		System.out.println("All numbers have been inserted!");

	}

	/*
	 * It calculates the average of the values input
	 */

	public void findAverage() {
		int total = 0;
		for (int i = 0; i < averageList.getListIntegers().size() - 1; i++) {
			total += averageList.getListIntegers().get(i);
		}

		int average = total / averageList.getLastNumber();

		System.out.println("The average of the list is: " + average);

	}

}
