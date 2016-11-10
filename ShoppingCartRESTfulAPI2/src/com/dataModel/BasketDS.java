package com.dataModel;

public class BasketDS {
	private int basket_id;

	public int getBasket_id() {
		return basket_id;
	}

	public void setBasket_id(int basket_id) {
		this.basket_id = basket_id;
	}
	
	@Override
	public String toString() {
		return "{\"BasketDS\":[{\"basket_id\":\"" + basket_id  + "\"}]}";
	}
}
