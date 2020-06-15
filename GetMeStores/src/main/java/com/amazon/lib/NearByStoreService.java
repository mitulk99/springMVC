package com.amazon.lib;

import java.util.List;

import com.amazon.Controller.NearByStoreRequest;
import com.amazon.Datastore.StoresDetails;

/*
 * NearByStoreService.java
 * interface which has only method nearbystore.
 * 
 */
public interface NearByStoreService {

    /*
     * method nearbystore.
     * 
     * this method will convert NearByStoreRequest to NearByStore.
     * It should convert valid pincode to corresponding lat-long.
     * 
     */
    public List < StoresDetails > nearbystore(final NearByStoreRequest model) throws Exception;

}