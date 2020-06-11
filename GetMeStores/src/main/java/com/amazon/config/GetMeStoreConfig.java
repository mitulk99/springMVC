package com.amazon.config;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.amazon.lib.NearByStoreService;
import com.amazon.lib.NearByStoreServiceImpl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazon.Datastore.GetStoreDataDDB;
import com.amazon.Datastore.GetStoreDataES;
import com.amazon.Datastore.StoresDetails;
import com.amazon.Datastore.GetStoreData;

@Configuration
@ComponentScan({
    "com.amazon.*"
})
public class GetMeStoreConfig {


//    @Bean
//    public NearByStoreService getimplt() {
//        return new NearByStoreServiceImpl();
//    }



    //	@Bean
    //	public GetStoreData getstoreimplt()
    //	{
    //		return new GetStoreDataDDB();
    //	}

//    @Bean
//    public GetStoreData getstoreimplt() {
//        return new GetStoreDataES();
//    }
//
//    @Bean
//    public GetStoreDataES getES() {
//        return new GetStoreDataES();
//    }
//
//    @Bean
//    public GetStoreDataDDB getDDB() {
//        return new GetStoreDataDDB();
//    }

	@Bean
	public RestHighLevelClient restHighLevelClient()
	{
		return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
	}
	
    @Bean
    public AWSCredentials aWSCredentials() {
        return new BasicAWSCredentials("AKIAXIEUBBVBKSFN7KHL", "MF2x/YWjdSpbrXZhhn9aiW75X7cPiZl8o6CqdEbp");
    }
    
    @Bean
    public AmazonDynamoDBClient amazonDynamoDBClient(AWSCredentials credentials) {
        return new AmazonDynamoDBClient(credentials);
    }

}