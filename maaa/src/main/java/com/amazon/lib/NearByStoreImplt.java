package com.amazon.lib;

import java.util.List;

import javax.inject.Inject;

import com.amazon.Datastore.LibToDatastoreModel;
import com.amazon.lib.ControllerTolibModel;
import com.amazon.Datastore.GetStoreDataInterface;
import com.amazon.Controller.StoresDetails;
import com.amazon.util.PincodeToCoords;

public class NearByStoreImplt implements NearByStoreInterface {
	
	@Inject
	private GetStoreDataInterface getStore;
	
	public List<StoresDetails> getmeStore(ControllerTolibModel model) throws Exception {
		
		final PincodeToCoords pincodetocoords=PincodeToCoords.builder()
				 .pincode(model.getPincode())
				 .build();	
		
		pincodetocoords.getCoordinates();

		LibToDatastoreModel object=LibToDatastoreModel.builder()
													.lat(pincodetocoords.getLat())
													.lon(pincodetocoords.getLon())
													.radius(model.getRadius())
													.category(model.getCategory())
													.build();
		
		List<StoresDetails> storedetails=getStore.nearbystoredata(object);
		
		return storedetails;
	}

}
