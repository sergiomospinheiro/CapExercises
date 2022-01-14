package Lists;

import java.util.ArrayList;
import java.util.List;

public class ShopList {
	
	private static ShopList shopList;
	private List<Shops> shopArrayList;
	private Shops foodList;
	private Shops otherList;
	
	
	private ShopList () {
		shopArrayList = new ArrayList<Shops>();
	}
	
	public static ShopList getShopList() {
		if(shopList == null) {
			shopList = new ShopList();
		}
		
		return shopList;
	}
	
	public boolean addShopList(Shops shops) {
		if((shops == foodList || shops == otherList) && (!shopArrayList.contains(shops)) {
			shopArrayList.add(shops);
			return true;
		}else { return false; }
			
		}
	}
	
	

}
