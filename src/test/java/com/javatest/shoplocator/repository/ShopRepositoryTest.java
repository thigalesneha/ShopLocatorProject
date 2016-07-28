package com.javatest.shoplocator.repository;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.javatest.shoplocator.App;
import com.javatest.shoplocator.dto.ShopDto;
import com.javatest.shoplocator.exception.GoogleResponseException;
import com.javatest.shoplocator.exception.InsufficientInputException;
import com.javatest.shoplocator.model.Address;
import com.javatest.shoplocator.model.Shop;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class ShopRepositoryTest {

	@Autowired
	private ShopRepository shopRepo;
	
	@After
	public void clear(){
		shopRepo.getShopList().clear();
	}
	
	@Test
	public void testAddShopAddress() throws GoogleResponseException, InsufficientInputException{
		ShopDto shop1 = getShopDto("Domino's Pizza","Windsor Park, Datta Mandir Road, Shankar Kalat Nagar, Wakad, Pimpri-Chinchwad, Maharashtra","411057");
		ShopDto shop2 = getShopDto("Pizza Hut","Shop No 1 to 4, Ozone Spring Survey No 1, 240, Wakad Rd, Kaspate Wasti, Wakad, Pune, Maharashtra","411057");
		shopRepo.addShopAddress(shop1);
		shopRepo.addShopAddress(shop2);
		//verification
		Assert.assertEquals(shopRepo.getShopList().size(), 2);
	}
	
	@Test
	public void testGetNearestShopListWithNearestShops() throws InsufficientInputException, GoogleResponseException{
		ShopDto shop1 = getShopDto("Domino's Pizza","Windsor Park, Datta Mandir Road, Shankar Kalat Nagar, Wakad, Pimpri-Chinchwad, Maharashtra","411057");
		ShopDto shop2 = getShopDto("Pizza Hut","Shop No 1 to 4, Ozone Spring Survey No 1, 240, Wakad Rd, Kaspate Wasti, Wakad, Pune, Maharashtra","411057");
		shopRepo.addShopAddress(shop1);
		shopRepo.addShopAddress(shop2);
		List<Shop> nearestShopList = shopRepo.getNearestShopList(18.598676, 73.7636565);
		//verification
		Assert.assertEquals(nearestShopList.size(), 1);
	}
	
	@Test
	public void testGetNearestShopListWithOutNearestShops() throws IOException, GoogleResponseException, InsufficientInputException{
		ShopDto shop1 = getShopDto("Domino's Pizza","Windsor Park, Datta Mandir Road, Shankar Kalat Nagar, Wakad, Pimpri-Chinchwad, Maharashtra","411057");
		ShopDto shop2 = getShopDto("Pizza Hut","Shop No 1 to 4, Ozone Spring Survey No 1, 240, Wakad Rd, Kaspate Wasti, Wakad, Pune, Maharashtra","411057");
		shopRepo.addShopAddress(shop1);
		shopRepo.addShopAddress(shop2);
		List<Shop> nearestShopList = shopRepo.getNearestShopList(18.5946784,73.7095365);
		//verification
		Assert.assertEquals(nearestShopList.size(), 0);
	}
	
	@Test(expected=InsufficientInputException.class)
	public void testAddShopAddressWithException(){
		ShopDto shop1 = getShopDto("Domino's Pizza",null,"411057");
		shopRepo.addShopAddress(shop1);
	}
	
	private ShopDto getShopDto(String shopName,String shopAddress,String postalCode){
		ShopDto shopDto = new ShopDto();
		shopDto.setShopName(shopName);
		Address address  = new Address();
		address.setAddress(shopAddress);
		address.setPostCode(postalCode);
		shopDto.setShopAddress(address);
		return shopDto;
	}
}
