package com.amazon.Datastore;


import javax.inject.Inject;
import javax.inject.Named;

import com.amazon.Datastore.GetStoreData;
import com.amazon.Datastore.GetStoreDataDDB;
import com.amazon.Datastore.GetStoreDataES;

@Named
public class GetStoreDataFactory {

	private final GetStoreDataES getStoreDataES;
	private final GetStoreDataDDB getStoreDataDDB;

	@Inject
	GetStoreDataFactory(GetStoreDataDDB getStoreDataDDB, GetStoreDataES getStoreDataES){
		this.getStoreDataDDB = getStoreDataDDB;
		this.getStoreDataES = getStoreDataES;
	}
	
	public GetStoreData getInstance(String str)
	{
		if(str.equals("ES"))
			return getStoreDataES;
		else
			return getStoreDataDDB;
	}

}
