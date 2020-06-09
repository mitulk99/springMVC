package com.amazon.Datastore;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONArray;
import org.json.JSONObject;

import com.amazon.Controller.StoresDetails;
import com.amazon.Datastore.LibToDatastoreModel;

/*
 * GetStoreDataESImplt.java
 * 
 * in this class, StoresDetails will be retrieved from ElasticSearch
 * with the use of geo-ditance query.
 * 
 */
public class GetStoreDataESImplt implements GetStoreDataInterface  {

	public List<StoresDetails> nearbystoredata(LibToDatastoreModel User) throws Exception{
		
		List<StoresDetails> details= new ArrayList<StoresDetails>();            
			/*
			 * Use of RestHighLevelClient to connect our java client with elasticsearch.
			 * 
			 * RestHighLevelClient is used instead of ES java API, since ES is planning to deprecate
			 * ES java API in it's future version.
			 * 
			 */
			RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200, "http")));
			SearchRequest searchRequest = new SearchRequest("my_locations"); 
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			
			/*
			 * here I am building a geoDistance query.
			 * 
			 *  .geoDistanceQuery("location") - it will inject user's "location" in database.
			 *  								which would be of type geo-point.
			 *  
			 *  .point(User.getLat(),User.getLon()) - setting up geo-point --"location" field with user's Lat-Lon.
			 *  
			 *  .distance(User.getRadius(), DistanceUnit.KILOMETERS) - search for the Stores within radius given here in KM.
			 * 
			 */
			searchSourceBuilder.query(QueryBuilders
					  .geoDistanceQuery("location")
					  .point(User.getLat(),User.getLon())
					  .distance(User.getRadius(), DistanceUnit.KILOMETERS)
					  .geoDistance(GeoDistance.ARC));
			
			searchRequest.source(searchSourceBuilder.sort(SortBuilders.geoDistanceSort("location",User.getLat(),User.getLon()).order(SortOrder.ASC).unit(DistanceUnit.KILOMETERS)));
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			/*
			 * fetching all the hits
			 */
			RestStatus status = searchResponse.status();
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			
			/*
			 * parsing fetched Json data to make it compatible with StoresDetails Model.
			 */
			for (SearchHit hit : searchHits) {
				JSONObject obj1= new JSONObject(hit.toString());
				JSONArray obj2=obj1.getJSONArray("sort");
				obj1=obj1.getJSONObject("_source");
				if(obj1.get("category").equals(User.getCategory()) || User.getCategory().equals("all")==true)
				{
					StoresDetails sd=StoresDetails.builder().build();
						sd.setMerchantname(obj1.get("merchant name").toString());
						sd.setOpentime(obj1.get("open time").toString());
						sd.setClosetime(obj1.get("close time").toString());
						sd.setAddress(obj1.get("address").toString());
						sd.setCategory(obj1.get("category").toString());
						if(obj1.has("home delivery")==true)
						sd.setRangeofhomedelivery(obj1.get("range of home delivery").toString());
						sd.setDistance(obj2.getDouble(0));
						details.add(sd);
				}
			}
			return details;
	}

}
