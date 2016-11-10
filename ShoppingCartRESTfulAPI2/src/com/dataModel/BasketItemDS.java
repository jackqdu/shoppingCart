package com.dataModel;

public class BasketItemDS{	
	private int basketId;
	private int itemId;
	private String name;
	private double price;
	private int quantity;
	private int scale;
	
	

	public int getBasketId() {
		return basketId;
	}

	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}



	@Override
	public String toString() {
		return "{\"BasketItemDS\":[{\"basketId\":\"" + basketId  + "\"},"
				+ "{\"itemId\":\"" + itemId  + "\"},"
				+ "{\"name\":\"" + name  + "\"},"
				+ "{\"price\":\"" + price  + "\"},"
				+ "{\"quantity\":\"" + quantity  + "\"},"
				+ "{\"scale\":\"" + scale  + "\"}]}";
	}
}
