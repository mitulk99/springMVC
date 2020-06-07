package com.amazon.Datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazon.Controller.StoresDetails;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.geo.GeoDataManager;
import com.amazonaws.geo.GeoDataManagerConfiguration;
import com.amazonaws.geo.model.GeoPoint;
import com.amazonaws.geo.model.QueryRadiusRequest;
import com.amazonaws.geo.model.QueryRadiusResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class GetStoreDataDDBimplt implements GetStoreDataInterface {

	private AmazonDynamoDBClient ddb=null;
	
	public List<StoresDetails> nearbystoredata(LibToDatastoreModel User)  {
		
		List<StoresDetails> details= new ArrayList<StoresDetails>(); 
		
		AWSCredentials credentials = new BasicAWSCredentials("SECRET_KEY", "SECRET_ACCESS_KEY");
		 ddb = new AmazonDynamoDBClient(credentials);
		 
		 GeoDataManagerConfiguration config = new GeoDataManagerConfiguration(ddb, "geo-test");
			GeoDataManager geoDataManager = new GeoDataManager(config);
			
			GeoPoint centerPoint = new GeoPoint(User.getLat(), User.getLon());
			QueryRadiusRequest queryRadiusRequest = new QueryRadiusRequest(centerPoint, User.getRadius()*1000);
	        QueryRadiusResult queryRadiusResult = geoDataManager.queryRadius(queryRadiusRequest);
	        List<Map<String, AttributeValue>> queryResults = queryRadiusResult.getItem();
	        for(Map<String, AttributeValue> item : queryResults)
	        {	
	        	if(User.getCategory().equals(item.get("category").getS()) || User.getCategory().equals("all"))
	        	{
	        		StoresDetails sd=StoresDetails.builder().build();
	        		
	        		sd.setAddress(item.get("address").getS());
	        		sd.setCategory(item.get("category").getS());
	        		sd.setClosetime(item.get("close time").getS());
	        		sd.setOpentime(item.get("open time").getS());
	        		sd.setMerchantname(item.get("merchant name").getS());
	        		
	        		if(item.containsKey("home delivery")==true)
	        			sd.setRangeofhomedelivery(item.get("range of home delivery").getN().toString());
	        		
	        		details.add(sd);
	        	}
	        	
	        }
		return details;
	}

}
