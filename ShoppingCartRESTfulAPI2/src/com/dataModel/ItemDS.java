package com.dataModel;

public class ItemDS {
	private int itemId;
	private String name;
	private double rate;
	private int inventory;
	
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	@Override
	public String toString() {
		return "{\"ItemDS\":[{\"item_id\":\"" + itemId  + "\", \"name\":\"" + name  + "\", \"rate\":\"" + rate  + "\", \"inventory\":\"" + inventory  + "\"}]}";
	}
}
