package com.amazon.lib;

import java.util.List;

import com.amazon.Controller.NearByStoreRequest;
import com.amazon.Datastore.StoresDetails;

/*
 * NearByStore.java
 * interface which has only method getmeStore.
 * 
 */
public interface NearByStoreService {

    /*
     * method getmeStore.
     * 
     * this method will convert ControllerTolibModel to libToDatastoreModel.
     * It should convert valid pincode to corresponding lat-long.
     * 
     */
    public List < StoresDetails > nearbystore(final NearByStoreRequest model) throws Exception;

}