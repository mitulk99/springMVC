package com.amazon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.amazon.lib.NearByStore;
import com.amazon.lib.NearByStoreImplementation;
import com.amazon.Datastore.GetStoreDataDDB;
import com.amazon.Datastore.GetStoreDataES;
import com.amazon.Datastore.GetStoreData;

@Configuration
@ComponentScan({ "com.amazon.*" })
public class Myconfig {
	
	
	@Bean
	public NearByStore getimplt()
	{
		return new NearByStoreImplementation();
	}
	
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
}
