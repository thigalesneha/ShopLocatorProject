package com.javatest.shoplocator.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.javatest.shoplocator.dto.ShopDto;
import com.javatest.shoplocator.exception.ContentNotFoundException;
import com.javatest.shoplocator.exception.GoogleResponseException;
import com.javatest.shoplocator.exception.InsufficientInputException;
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
	
	public void addShopAddress(ShopDto shopDto) {
		GoogleResponse response = null;		
		//input check
		if(shopDto.getShopName()==null|shopDto.getShopAddress().getPostCode()==null|shopDto.getShopAddress().getAddress()==null){
			throw new InsufficientInputException("inputs are missing");
		}
		response = GoogleResponseHelper.getLocationDetails(shopDto.getShopName()+" "+shopDto.getShopAddress().getAddress()+" "+shopDto.getShopAddress().getPostCode());
		if(response.getStatus().equals("OK")){
			Shop shop = new Shop();
			shop.setShopName(shopDto.getShopName());
			shop.setShopAddress(shopDto.getShopAddress());
			shop.setLocation(response.getResults()[0].getGeometry().getLocation());
			shopList.add(shop);
		}else if(response.getStatus().equals("ZERO_RESULTS")){
			throw new ContentNotFoundException("no content from google api");
		}else{
			throw new GoogleResponseException("UNKNOWN_ERROR");
		}
	}
	
	public List<Shop> getNearestShopList(Double latitude,Double longitude){
		List<Shop> shopLists = new ArrayList<Shop>();
		if(latitude == null||longitude == null){
			throw new InsufficientInputException("inputs are missing");
		}
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