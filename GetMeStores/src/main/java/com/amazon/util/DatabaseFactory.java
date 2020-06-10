package com.amazon.util;


import com.amazon.Datastore.GetStoreData;
import com.amazon.Datastore.GetStoreDataDDB;
import com.amazon.Datastore.GetStoreDataES;


public class DatabaseFactory {
	
		
	
	public GetStoreData getInstance(String str)
	{
		if(str.equals("ES"))
			return new GetStoreDataES();
		else
			return new GetStoreDataDDB();
	}

}
