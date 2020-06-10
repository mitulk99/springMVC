package com.amazon.config;



import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.amazon.lib.NearByStore;
import com.amazon.lib.NearByStoreImplementation;
import com.amazon.util.DatabaseFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazon.Datastore.GetStoreDataDDB;
import com.amazon.Datastore.GetStoreDataES;
import com.amazon.Datastore.GetStoreData;

@Configuration
@ComponentScan({ "com.amazon.*" })
public class GetMeStoreConfig {
	
	
	@Bean
	public NearByStore getimplt()
	{
		return new NearByStoreImplementation();
	}
	
//	@Bean
//	public NearByStore getinjected(DatabaseFactory factory, GetStoreData getStore)
//	{
//		return new NearByStoreImplementation(factory,getStore);
//	}
	
//	@Bean
//	public GetStoreData getstoreimplt()
//	{
//		return new GetStoreDataDDB();
//	}
	
	@Bean
	public GetStoreData getstoreimplt()
	{
		return new GetStoreDataES();
	}
	
	@Bean
	public RestHighLevelClient getmeClient()
	{
		return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200, "http")));
	}
	
	@Bean
	public SearchSourceBuilder getsearchBuilder()
	{
		return new SearchSourceBuilder();
	}
	
	@Bean
	public AWSCredentials getCredentials()
	{
		return new BasicAWSCredentials("SECRET_KEY", "ACCESS_KEY");
	}
	
	@Bean
	public AmazonDynamoDBClient getClient(AWSCredentials credentials)
	{
		return new AmazonDynamoDBClient(credentials);
	}
//	@Bean
//	@Scope(value= "prorotype" )
//	public DatabaseFactory getdatabse()
//	{
//		return new DatabaseFactory();
//	}
}
