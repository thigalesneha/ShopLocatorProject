package com.javatest.shoplocator.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javatest.shoplocator.dto.ShopDto;
import com.javatest.shoplocator.model.Shop;
import com.javatest.shoplocator.repository.ShopRepository;

@RestController
@RequestMapping("/")
public class ShopLocatorController {
	
	@Autowired
	private ShopRepository repository;    
	
	@RequestMapping(method = RequestMethod.POST,value = "/addresses")
	public ResponseEntity<String> addAddress(@RequestBody ShopDto shopDto) throws IOException{
		repository.addShopAddress(shopDto);
		return new ResponseEntity<String>("Resource created",HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/address/{latitude}/{longitude}")
	public ResponseEntity<List<Shop>> getAddress(@RequestParam double latitude,@RequestParam double longitude) throws IOException{
		return new ResponseEntity<List<Shop>>(repository.getNearestShopList(latitude, longitude),HttpStatus.OK);		
	}
}
