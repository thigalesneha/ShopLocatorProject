package com.javatest.shoplocator.controller;

import java.util.ArrayList;
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
import com.javatest.shoplocator.exception.ContentNotFoundException;
import com.javatest.shoplocator.exception.GoogleResponseException;
import com.javatest.shoplocator.exception.InsufficientInputException;
import com.javatest.shoplocator.model.Shop;
import com.javatest.shoplocator.repository.ShopRepository;

@RestController
@RequestMapping("/")
public class ShopLocatorController {
	
	@Autowired
	private ShopRepository repository;    
	
	@RequestMapping(method = RequestMethod.POST,value = "/addresses")
	public ResponseEntity<String> addAddress(@RequestBody ShopDto shopDto){
		try {
			repository.addShopAddress(shopDto);
		}catch (InsufficientInputException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch (GoogleResponseException e1) {
			return new ResponseEntity<String>(e1.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(ContentNotFoundException e2){
			return new ResponseEntity<String>(e2.getMessage(),HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Resource created",HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/address/{latitude}/{longitude}")
	public ResponseEntity<List<Shop>> getAddress(@RequestParam Double latitude,@RequestParam Double longitude){
		try {
			return new ResponseEntity<List<Shop>>(repository.getNearestShopList(latitude, longitude),HttpStatus.OK);
		} catch (InsufficientInputException e) {
			return new ResponseEntity<List<Shop>>(new ArrayList<Shop>(),HttpStatus.BAD_REQUEST);
		}		
	}
}
