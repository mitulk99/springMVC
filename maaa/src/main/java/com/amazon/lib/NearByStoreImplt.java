package com.amazon.lib;

import java.util.List;

import javax.inject.Inject;

import com.amazon.Datastore.LibToDatastoreModel;
import com.amazon.lib.ControllerTolibModel;
import com.amazon.Datastore.GetStoreDataInterface;
import com.amazon.Controller.StoresDetails;
import com.amazon.util.PincodeToCoords;


/*
 * NearByStoreImplt.java
 * 
 * This class implements NearByStoreInterface
 * It uses @param PincodeToCoords to convert pincode to coordinates
 */
public class NearByStoreImplt implements NearByStoreInterface {
	
	private GetStoreDataInterface getStore;
	
	 public NearByStoreImplt() {}
	 
	 /*
	  * Constructor Injection used to make class requirements clear.
	  */
	@Inject
	public NearByStoreImplt(GetStoreDataInterface getStore)
	{
		this.getStore=getStore;
	}
	
	/*
	 * As you can see here,
	 * ControllerTolibModel is being converted to LibToDatastoreModel.
	 * 
	 * @return List<StoresDetails> - It will return list of all the stores.
	 */
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
