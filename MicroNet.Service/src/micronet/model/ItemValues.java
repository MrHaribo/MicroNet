package micronet.model;

public class ItemValues {

	public class ItemType {
		public static final int Vehicle = 0;
		public static final int PrimaryWeapon = 1;
		public static final int HeavyWeapon = 2;
		public static final int Material = 3;
	}

	private String name;
	private int type;
	private int quantity = 1;
	private int maxStackSize = 1;
	private int price = 100;

	public ItemValues(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public void setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
	}
}
