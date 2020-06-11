package com.amazon.lib;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

import com.amazon.Controller.NearByStoreRequest;
import com.amazon.Datastore.GetStoreData;
import com.amazon.Datastore.StoresDetails;
import com.amazon.Datastore.GetStoreDataFactory;
import com.amazon.util.PincodeToCoords;

import lombok.NoArgsConstructor;


/*
 * NearByStoreImplementation.java
 * 
 * This class implements NearByStore.java
 * It uses @param PincodeToCoords to convert pincode to coordinates
 */
@NoArgsConstructor
@Named
public class NearByStoreServiceImpl implements NearByStoreService {


//    GetStoreDataFactory factory = new GetStoreDataFactory();
//    //
//    //	// "ES" for Elasticsearch implementation, other than "ES" for DynamoDB implementation.
//    GetStoreData getStore = factory.getInstance("ES");

    GetStoreDataFactory getStoreDataFactory;

    @Inject
    NearByStoreServiceImpl(GetStoreDataFactory getStoreDataFactory){
        this.getStoreDataFactory = getStoreDataFactory;
    }


    /*
     * As you can see here,
     * NearByStoreM model is being converted to GetStoreDataM model.
     * 
     * @return List<StoresDetails> - It will return list of all the stores.
     */
    public List < StoresDetails > nearbystore(NearByStoreRequest model) throws Exception {

        final PincodeToCoords pincodetocoords = PincodeToCoords.builder()
            .pincode(model.getPincode())
            .build();

        pincodetocoords.getCoordinates();

        NearByStore object = NearByStore.builder()
            .lat(pincodetocoords.getLat())
            .lon(pincodetocoords.getLon())
            .radius(model.getRadius())
            .category(model.getCategory())
            .build();

        List < StoresDetails > storedetails = getStoreDataFactory.getInstance(model.getFactory()).getstoredata(object);

        return storedetails;
    }

}