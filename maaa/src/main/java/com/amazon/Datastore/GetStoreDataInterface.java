package com.amazon.Datastore;

import java.util.List;

import com.amazon.Controller.StoresDetails;
import com.amazon.Datastore.LibToDatastoreModel;
/*
 * GetStoreDataInterface.java
 * 
 * this interface will help us to retrieve storesDetails from perticular database.
 * It has nearbystoredata method to do the same. 
 * 
 * this interface will have two implementation.
 * 1). with use of elasticsearch.
 * 2). with use of DynamoDB -- geo-library.
 * 
 */
public interface GetStoreDataInterface {
	
	/*
	 * this method will work with API to get StoresDetails from the database.
	 */
	public List<StoresDetails> nearbystoredata(LibToDatastoreModel User) throws Exception;

}
