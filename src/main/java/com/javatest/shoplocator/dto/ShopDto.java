package com.javatest.shoplocator.dto;

import com.javatest.shoplocator.model.Address;

public class ShopDto {
	private String shopName;
	private Address shopAddress;
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
