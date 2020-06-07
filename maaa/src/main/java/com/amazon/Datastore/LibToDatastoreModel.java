package com.amazon.Datastore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibToDatastoreModel {

	private Double lat;
	
	private Double lon;
	
	private Double radius;
	
	private String category;
	
}
