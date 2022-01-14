package Lists;

public class Main {

	public static void main(String[] args) {
		
		ShopList shopList = ShopList.getShopList();
		
		System.out.println(shopList);
		
		Shops shops = new Shops();
		
		shops.setFoodList("Sopas");
		shops.setOtherList("Preservativos");
		
		shopList.addShopList(shops);
		
		
		
		
		
		

	}

}
