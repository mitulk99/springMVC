package com.amzon;

import java.util.Scanner;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Back {
	String s="";
	public Back(String pincode, String radius ,String category)
	{
               	try {
               Coordinates call = new Coordinates(pincode);
               Double lat= call.Lat();
               Double lon= call.Lon();
             RestClient restClient = RestClient.builder(
       	    new HttpHost("localhost", 9200, "http")).build();
       
       Request request = new Request(
       	    "GET",  
       	    "/my_locations/_search"); 
				Response response = restClient.performRequest(request);
				String responseBody = EntityUtils.toString(response.getEntity());
				//System.out.println(responseBody);
				JSONObject obj= new JSONObject(responseBody);
				obj=obj.getJSONObject("hits");
				JSONArray array =new JSONArray(obj.get("hits").toString());
				for(int i=0 ;i<array.length();i++)
				{
					s=s+array.getJSONObject(i).get("_source").toString();
					//System.out.println(array.getJSONObject(i).get("_source"));
							s=s+"\n";
				}
//				RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200, "http")));
//				SearchRequest searchRequest = new SearchRequest("my_locations"); 
//				SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//				searchSourceBuilder.query(QueryBuilders
//						  .geoDistanceQuery("location")
//						  .point(lat,lon)
//						  .distance(Double.valueOf(radius), DistanceUnit.KILOMETERS)
//						  .geoDistance(GeoDistance.ARC));
//				searchRequest.source(searchSourceBuilder);
//				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//				RestStatus status = searchResponse.status();
//				SearchHits hits = searchResponse.getHits();
//				SearchHit[] searchHits = hits.getHits();
//				
//				int i=1;
//				for (SearchHit hit : searchHits) {
//					JSONObject obj1= new JSONObject(hit.toString());
//					obj1=obj1.getJSONObject("_source");
//					if(obj1.get("category")==category || category=="all")
//					s=s+hit;
////					System.out.println("item : " + i);
////					if(obj1.has("merchant name")==true)
////					System.out.println("merchant name : " + obj1.get("merchant name") );
////					if(obj1.has("open time")==true)
////					System.out.println("open time : " + obj1.get("open time"));
////					if(obj1.has("close time")==true)
////					System.out.println("close time : " + obj1.get("close time"));
////					if(obj1.has("address")==true)
////					System.out.println("address : " + obj1.get("address"));
////					if(obj1.has("category")==true)
////					System.out.println("category : " + obj1.get("category"));
////					if(obj1.has("home delivery")==true)
////						System.out.println("range of home delivery : " + obj1.get("range of home delivery"));
////					i++;
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        	//System.out.println(" done ");
	}
	public String gets()
	{
		return s;
	}
	

}
