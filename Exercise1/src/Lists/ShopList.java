package Lists;

import java.util.ArrayList;
import java.util.List;

public class ShopList {

	private static ShopList shopListInstance;
	private List<String> shopList = new ArrayList<String>();
	private boolean isValid = false;

	private ShopList() {

	}

	/*
	 * Singleton instance
	 */

	public static ShopList getShopListInstance() {
		if (shopListInstance == null) {
			shopListInstance = new ShopList();
		}

		return shopListInstance;
	}

	/*
	 * Verifies the type of item
	 */

	public void verifyItem(String item) {
		if ((item.startsWith("Other") || item.startsWith("Food")) && !shopList.contains(item)) {
			System.out.println("Ok");
			isValid = true;
		} else {
			System.out.println("Item invalid!");
			isValid = false;
		}

	}

	public boolean addItem(String item) {
		verifyItem(item);
		if (isValid) {
			shopList.add(item);
			System.out.println(item + " has been added to the list");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 
	 */

	public void printFoodList() {
		System.out.println("--------------------------------\nThis is your current Food List: \n");
		shopList.stream().filter(item -> item.startsWith("Food")).map(item -> item.replace("Food", ""))
				.forEach(System.out::println);

	}

	public void printOtherList() {
		System.out.println("-------------------------------- \nThis is your current Other List: \n");
		shopList.stream().filter(item -> item.startsWith("Other")).map(item -> item.replace("Other", ""))
				.forEach(System.out::println);

	}

}
