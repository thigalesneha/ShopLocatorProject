package com.javatest.shoplocator.model;

import com.javatest.shoplocator.model.googleresponse.Location;

public class Shop {
	
	private String shopName;
	private Address shopAddress;
	private Location location;
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Address getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(Address shopAddress) {
		this.shopAddress = shopAddress;
	}

}
