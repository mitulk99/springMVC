package com.amazon.util;

import javax.inject.Named;

@Named
public class DistanceCalc {

	public Double distance(Double lat1,Double lat2,Double lon1,Double lon2)
	{
		lon1 = Math.toRadians(lon1); 
		lon2 = Math.toRadians(lon2); 
		lat1 = Math.toRadians(lat1); 
		lat2 = Math.toRadians(lat2); 
		double dlon = lon2 - lon1; 
		double dlat = lat2 - lat1; 
		double a = Math.pow(Math.sin(dlat / 2), 2) 
				+ Math.cos(lat1) * Math.cos(lat2) 
				* Math.pow(Math.sin(dlon / 2),2); 
			
		double c = 2 * Math.asin(Math.sqrt(a)); 
		double r = 6371;
		return(c * r); 
	}
}
