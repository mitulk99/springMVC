package com.amazon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import com.amazon.lib.NearByStoreImplt;
import com.amazon.lib.NearByStoreInterface;
import com.amazon.Datastore.GetStoreDataESImplt;
import com.amazon.Datastore.GetStoreDataInterface;
import com.amazon.Controller.StoresDetails;

@Configuration
@ComponentScan({ "com.amazon.*" })
public class Myconfig {
	
	
	@Bean
	public NearByStoreInterface getimplt()
	{
		return new NearByStoreImplt();
	}
	
	@Bean
	public GetStoreDataInterface getstoreimplt()
	{
		return new GetStoreDataESImplt();
	}
}
