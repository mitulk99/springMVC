package com.amazon.Datastore;

import java.util.List;

import com.amazon.lib.NearByStore;
/*
 * GetStoreData.java
 * 
 * this interface will help us to retrieve storesDetails from perticular database.
 * It has nearbystoredata method to do the same. 
 * 
 * this interface will have two implementation.
 * 1). with use of elasticsearch.
 * 2). with use of DynamoDB -- geo-library.
 * 
 */
public interface GetStoreData {

    /*
     * this method will work with API to get StoresDetails from the database.
     */
    public List < StoresDetails > getstoredata(final NearByStore User) throws Exception;

}