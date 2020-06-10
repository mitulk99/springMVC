package com.amazon.Datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.amazon.lib.GetStoreDataM;
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

public class GetStoreDataDDB implements GetStoreData {

    private AWSCredentials credentials = new BasicAWSCredentials("SECRET_KEY", "ACCESS_KEY");

    private AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(credentials);;

    public List < StoresDetails > getstoredata(final GetStoreDataM User) throws Exception {

        List < StoresDetails > details = new ArrayList < StoresDetails > ();

        /*
         * Providing IAM role credentials here using AWSCredentials.
         */

        /*
         * GeoDataManagerConfiguration(ddb, "geo-test") will search for "geo-test" table on my AWS account.
         */
        GeoDataManagerConfiguration config = new GeoDataManagerConfiguration(ddb, "geo-test");
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
            if (User.getCategory().equals(item.get("category").getS()) || User.getCategory().equals("all")) {
                StoresDetails sd = StoresDetails.builder().build();

                sd.setAddress(item.get("address").getS());
                sd.setCategory(item.get("category").getS());
                sd.setClosetime(item.get("close time").getS());
                sd.setOpentime(item.get("open time").getS());
                sd.setMerchantname(item.get("merchant name").getS());

                if (item.containsKey("home delivery") == true)
                    sd.setRangeofhomedelivery(item.get("range of home delivery").getN().toString());

                details.add(sd);
            }

        }
        return details;
    }

}