package com.javatest.shoplocator.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.javatest.shoplocator.dto.ShopDto;
import com.javatest.shoplocator.helper.GoogleResponseHelper;
import com.javatest.shoplocator.model.Shop;
import com.javatest.shoplocator.model.googleresponse.GoogleResponse;

@Repository
public class ShopRepository {

	private List<Shop> shopList;
	
	//distance in km
	private final double NEAREST_DISTANCE=1.0;
		
	@PostConstruct
	public void init(){
		shopList = new ArrayList<Shop>();
	}
	
	public void addShopAddress(ShopDto shopDto) throws IOException{
		GoogleResponse response = null;		
		response = GoogleResponseHelper.getLocationDetails(shopDto.getShopName()+" "+shopDto.getShopAddress().getAddress()+" "+shopDto.getShopAddress().getPostCode());
		Shop shop = new Shop();
		shop.setShopName(shopDto.getShopName());
		shop.setShopAddress(shopDto.getShopAddress());
		shop.setLocation(response.getResults()[0].getGeometry().getLocation());
		shopList.add(shop);
	}
	
	public List<Shop> getNearestShopList(double latitude,double longitude) throws IOException{
		List<Shop> shopLists = new ArrayList<Shop>();
		for(Shop shop : shopList){
			double distance = GoogleResponseHelper.getDistance(shop.getLocation().getLat(),shop.getLocation().getLng(),latitude,longitude);
			if(distance<=NEAREST_DISTANCE){
				shopLists.add(shop);
			}
		}
		return shopLists;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
}