package Lists;

public class Main {

	public static void main(String[] args) {
		
		ShopList shopListInstance = ShopList.getShopListInstance();
		
		shopListInstance.addItem("Other Tyres");
		shopListInstance.addItem("Food Bananas");
		shopListInstance.addItem("Other Tyres");
		shopListInstance.addItem("Other Razor Blades");
		shopListInstance.addItem("Other Notebook");
		shopListInstance.addItem("Food Cabbage");
		shopListInstance.addItem("Food Oranges");
		shopListInstance.addItem("Food Oranges");
		
		shopListInstance.printFoodList();
		
		shopListInstance.printOtherList();
		
		
		
		
		
		
		

	}

}
