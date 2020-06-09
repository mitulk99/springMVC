package com.maa.Es;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.mapper.ObjectMapper;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
//        RestClient restClient = RestClient.builder(
//        	    new HttpHost("localhost", 9200, "http")).build();
//        
//        Request request = new Request(
//        	    "GET",  
//        	    "/my_locations/_search"); 
               	try {
//				Response response = restClient.performRequest(request);
//				String responseBody = EntityUtils.toString(response.getEntity());
//				//System.out.println(responseBody);
//				JSONObject obj= new JSONObject(responseBody);
//				obj=obj.getJSONObject("hits");
//				JSONArray array =new JSONArray(obj.get("hits").toString());
//				for(int i=0 ;i<array.length();i++)
//				{
//					System.out.println(array.getJSONObject(i).get("_source"));
//				}
               		
               System.out.println("Enter pincode : ");
               Scanner in = new Scanner(System.in);
               String pincode =in.next();
               Coordinates call = new Coordinates();
               Double lat= call.Lat(pincode);
               Double lon= call.Long(pincode);
               System.out.println("Enter radius in KM : ");
               Double radius =in.nextDouble();
               System.out.println("Enter the category of store : ");
               String category =in.next();
//               String jsonString ="{" +
//            	   "\"merchant name\": \"Dhruv\"," +
//            	   "\"category\": \"garments\"," +
//            	   "\"open time\": \"10:00 AM\"," +
//            	   "\"close time\": \"7:00 PM\"," +
//            	   "\"address\": \"patan,gujarat\"," +
//            	   "\"location\": {" +
//            	     "\"lat\": 22.853258," +
//            	     "\"lon\": 72.1213029" +
//            	   "}" +
//            	 "}";

				RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200, "http")));
//				IndexRequest request = new IndexRequest("my_locations");
//				request.id("10");
//				request.source(jsonString, XContentType.JSON);
//				IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
//				System.out.println("response id: "+indexResponse.getId());
				
				SearchRequest searchRequest = new SearchRequest("my_locations"); 
				SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
				searchSourceBuilder.query(QueryBuilders
						  .geoDistanceQuery("location")
						  .point(lat,lon)
						  .distance(radius, DistanceUnit.KILOMETERS)
						  .geoDistance(GeoDistance.ARC));
				searchRequest.source(searchSourceBuilder.sort(SortBuilders.geoDistanceSort("location",lat,lon).order(SortOrder.ASC).unit(DistanceUnit.KILOMETERS)));
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				RestStatus status = searchResponse.status();
				SearchHits hits = searchResponse.getHits();
				SearchHit[] searchHits = hits.getHits();
				int i=1;
				for (SearchHit hit : searchHits) {
					JSONObject obj1= new JSONObject(hit.toString());
				//System.out.println(hit);
					JSONArray obj2=obj1.getJSONArray("sort"); 
					obj1=obj1.getJSONObject("_source");
					//System.out.println(obj1.get("category") + category);
					String flag=(String) obj1.get("category");
					//System.out.println(flag.matches(category));
						if((obj1.has("category")==false || flag.matches(category)==false) && category.equals("All")==false)
							continue;
					
					System.out.println("item : " + i);
					if(obj1.has("merchant name")==true)
					System.out.println("merchant name : " + obj1.get("merchant name") );
					if(obj1.has("open time")==true)
					System.out.println("open time : " + obj1.get("open time"));
					if(obj1.has("close time")==true)
					System.out.println("close time : " + obj1.get("close time"));
					if(obj1.has("address")==true)
					System.out.println("address : " + obj1.get("address"));
					if(obj1.has("category")==true)
					System.out.println("category : " + obj1.get("category"));
					if(obj1.has("home delivery")==true)
						System.out.println("range of home delivery : " + obj1.get("range of home delivery"));
					System.out.println("Distance : " + obj2.getDouble(0) + " KM");
					i++;
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        	System.out.println(" done ");
    }
}