package com.amazon.lib;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.amazon.Controller.NearByStoreM;
import com.amazon.Datastore.GetStoreData;
import com.amazon.Datastore.StoresDetails;
import com.amazon.config.GetMeStoreConfig;
import com.amazon.util.DatabaseFactory;
import com.amazon.util.PincodeToCoords;

import lombok.NoArgsConstructor;
import lombok.NonNull;


/*
 * NearByStoreImplementation.java
 * 
 * This class implements NearByStore.java
 * It uses @param PincodeToCoords to convert pincode to coordinates
 */
@NoArgsConstructor
public class NearByStoreImplementation implements NearByStore {


    DatabaseFactory factory = new DatabaseFactory();
    //	
    //	// "ES" for Elasticsearch implementation, other than "ES" for DynamoDB implementation.
    GetStoreData getStore = factory.getInstance("ES");



    /*
     * As you can see here,
     * NearByStoreM model is being converted to GetStoreDataM model.
     * 
     * @return List<StoresDetails> - It will return list of all the stores.
     */
    public List < StoresDetails > nearbystore(NearByStoreM model) throws Exception {

        final PincodeToCoords pincodetocoords = PincodeToCoords.builder()
            .pincode(model.getPincode())
            .build();

        pincodetocoords.getCoordinates();

        GetStoreDataM object = GetStoreDataM.builder()
            .lat(pincodetocoords.getLat())
            .lon(pincodetocoords.getLon())
            .radius(model.getRadius())
            .category(model.getCategory())
            .build();

        List < StoresDetails > storedetails = getStore.getstoredata(object);

        return storedetails;
    }

}