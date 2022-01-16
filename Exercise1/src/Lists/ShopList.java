package Lists;

import java.util.ArrayList;

public class ShopList {
	
	private static ShopList shopListInstance;
	private ArrayList <String> shopList;
	
	private ShopList () {
		
		shopList = new ArrayList<String>();
		
	}
	
	public static ShopList getShopListInstance () {
		if(shopListInstance == null) {
			shopListInstance = new ShopList();
		}
		
		return shopListInstance;
	}
	
	public boolean addItem (String item) {
		
		if((item.startsWith("Other") || item.startsWith("Food")) && !shopList.contains(item)) {
			shopList.add(item);
			return true; 
		} else {
			return false;
		}
				
			}
	
	public void printFoodList () {
		System.out.println("--------------------------------\nThis is your current Food List: \n");
		shopList.stream()
		.filter(item -> item.startsWith("Food"))
		.forEach(System.out::println);
		
	}
	
	public void printOtherList() {
		System.out.println("-------------------------------- \nThis is your current Other List: \n");
		shopList.stream()
		.filter(item -> item.startsWith("Other"))
		.forEach(System.out::println);
		
	}
	

}
