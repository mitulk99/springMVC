package com.amazon.Datastore;

import javax.inject.Inject;
import javax.inject.Named;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazon.lib.NearByStore;
import com.amazon.util.Constants;
import com.amazon.util.DistanceCalc;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.geo.GeoDataManager;
import com.amazonaws.geo.GeoDataManagerConfiguration;
import com.amazonaws.geo.model.GeoPoint;
import com.amazonaws.geo.model.QueryRadiusRequest;
import com.amazonaws.geo.model.QueryRadiusResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/*
 * GetStoreDataDDB.java
 * 
 * in this class, StoresDetails will be retrieved from AWS DDB
 * with the use of Geo-library of DDb.
 * 
 */
@Named
public class GetStoreDataDDB implements GetStoreData {

    private AWSCredentials credentials;

    private AmazonDynamoDBClient ddb;
    
    private DistanceCalc distanceCalc;
    
    @Inject
    public GetStoreDataDDB(AmazonDynamoDBClient amazonDynamoDBClient, AWSCredentials aWSCredentials , DistanceCalc distanceCalc) {
		this.credentials=aWSCredentials;
		this.ddb=amazonDynamoDBClient;
		this.distanceCalc=distanceCalc;
	}
    public List < StoresDetails > getstoredata(final NearByStore User) throws Exception {

        List < StoresDetails > details = new ArrayList < StoresDetails > ();

        /*
         * Providing IAM role credentials here using AWSCredentials.
         */

        /*
         * GeoDataManagerConfiguration(ddb, "geo-test") will search for "geo-test" table on my AWS account.
         */
        GeoDataManagerConfiguration config = new GeoDataManagerConfiguration(ddb, Constants.GEO_TABLE);
        GeoDataManager geoDataManager = new GeoDataManager(config);

        /*
         * Here I am setting up centerPoint (user's location)
         * and setting radius within which user wants Stores to be retrived.
         */
        GeoPoint centerPoint = new GeoPoint(User.getLat(), User.getLon());
        QueryRadiusRequest queryRadiusRequest = new QueryRadiusRequest(centerPoint, User.getRadius() * 1000);
        QueryRadiusResult queryRadiusResult = geoDataManager.queryRadius(queryRadiusRequest);
        List < Map < String, AttributeValue >> queryResults = queryRadiusResult.getItem();

        /*
         * Parsing data retrived from "geo-test" table to make compatible with StoresDetails Model.
         */
        for (Map < String, AttributeValue > item: queryResults) {
            if (User.getCategory().equals(item.get(Constants.STORE_CATEGORY).getS()) || User.getCategory().equals(Constants.ALL)) {
                StoresDetails sd = StoresDetails.builder().build();

                sd.setAddress(item.get(Constants.ADDRESS).getS());
                sd.setCategory(item.get(Constants.STORE_CATEGORY).getS());
                sd.setClosetime(item.get(Constants.CLOSE_TIME).getS());
                sd.setOpentime(item.get(Constants.OPEN_TIME).getS());
                sd.setMerchantname(item.get(Constants.MERCHANT_NAME).getS());
                JSONObject object=new JSONObject(item.get(Constants.GEOJSON).getS());
                JSONArray coordinates=object.getJSONArray(Constants.COORDINATES);
                Double distance=distanceCalc.distance(User.getLat(), coordinates.getDouble(0), User.getLon(), coordinates.getDouble(1));
                
                sd.setDistance(distance);
                if (item.containsKey(Constants.HOME_DELIVERY))
                    sd.setRangeofhomedelivery(item.get(Constants.RANGE_OF_DELIVERY).getN().toString());

                details.add(sd);
            }

        }
        return details;
    }

}