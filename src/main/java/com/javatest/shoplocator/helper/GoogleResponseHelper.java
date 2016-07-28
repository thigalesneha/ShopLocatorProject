package com.javatest.shoplocator.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatest.shoplocator.exception.GoogleResponseException;
import com.javatest.shoplocator.model.googleresponse.GoogleResponse;

public class GoogleResponseHelper {
	
	private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";
	
	
	public static GoogleResponse getLocationDetails(String address) throws GoogleResponseException{
		
    	URL url = null;
    	GoogleResponse response = null;
		try {
			url = new URL(URL + "?address="+URLEncoder.encode(address, "UTF-8") + "&sensor=false");
		} catch (MalformedURLException |UnsupportedEncodingException e) {
			throw new GoogleResponseException(e.getMessage());
		} 
    	URLConnection conn;
		try {
			conn = url.openConnection();
			InputStream in = conn.getInputStream() ;
	    	ObjectMapper mapper = new ObjectMapper();
	    	response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
	    	in.close();
		} catch (IOException e) {
			throw new GoogleResponseException(e.getMessage());
		}
    	
    	return response;
    }
    
	public static Double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;		
		return (dist);
	}
    
    private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
