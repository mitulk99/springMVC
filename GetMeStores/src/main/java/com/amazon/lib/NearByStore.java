package com.amazon.lib;

import java.util.List;


import com.amazon.Controller.StoresDetails;

/*
 * NearByStore.java
 * interface which has only method getmeStore.
 * 
 */
public interface NearByStore {
	
	/*
	 * method getmeStore.
	 * 
	 * this method will convert ControllerTolibModel to libToDatastoreModel.
	 * It should convert valid pincode to corresponding lat-long.
	 * 
	 */
	public List<StoresDetails> getmeStore(NearByStoreM model) throws Exception;

}