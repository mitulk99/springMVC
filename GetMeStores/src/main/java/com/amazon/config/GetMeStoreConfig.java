package com.amazon.config;



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
    public AWSCredentials getCredentials() {
        return new BasicAWSCredentials("ACCESS_KEY", "SECRET_KEY");
    }

    @Bean
    public AmazonDynamoDBClient getClient(AWSCredentials credentials) {
        return new AmazonDynamoDBClient(credentials);
    }

}