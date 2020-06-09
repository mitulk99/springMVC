package com.amazon.lib;

import java.util.List;

import javax.inject.Inject;

import com.amazon.Datastore.GetStoreData;
import com.amazon.Datastore.GetStoreDataM;
import com.amazon.Controller.StoresDetails;
import com.amazon.util.PincodeToCoords;


/*
 * NearByStoreImplementation.java
 * 
 * This class implements NearByStore.java
 * It uses @param PincodeToCoords to convert pincode to coordinates
 */
public class NearByStoreImplementation implements NearByStore {
	
	@Inject
	private GetStoreData getStore;
	
	/*
	 * As you can see here,
	 * NearByStoreM model is being converted to GetStoreDataM model.
	 * 
	 * @return List<StoresDetails> - It will return list of all the stores.
	 */
	public List<StoresDetails> getmeStore(NearByStoreM model) throws Exception {
		
		final PincodeToCoords pincodetocoords=PincodeToCoords.builder()
				 .pincode(model.getPincode())
				 .build();	
		
		pincodetocoords.getCoordinates();

		GetStoreDataM object=GetStoreDataM.builder()
													.lat(pincodetocoords.getLat())
													.lon(pincodetocoords.getLon())
													.radius(model.getRadius())
													.category(model.getCategory())
													.build();
		
		List<StoresDetails> storedetails=getStore.nearbystoredata(object);
		
		return storedetails;
	}

}