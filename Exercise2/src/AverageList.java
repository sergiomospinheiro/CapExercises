import java.util.ArrayList;
import java.util.List;

public class AverageList {

	private List<Integer> listIntegers = new ArrayList<Integer>();
	private Integer average;

	public AverageList() {

	}

	public List<Integer> getListIntegers() {
		return listIntegers;
	}

	public void addIntegersList(Integer number) {
		listIntegers.add(number);

	}

	public Integer getLastNumber() {
		Integer average = listIntegers.get(3);
		return average;

	}

}
