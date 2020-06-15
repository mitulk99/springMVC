package com.amazon.Datastore;

import javax.inject.Inject;
import javax.inject.Named;

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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONArray;
import org.json.JSONObject;

import com.amazon.lib.NearByStore;
import com.amazon.util.Constants;

/*
 * GetStoreDataES.java
 * 
 * in this class, StoresDetails will be retrieved from ElasticSearch
 * with the use of geo-ditance query.
 * 
 */
@Named
public class GetStoreDataES implements GetStoreData {

	/*
     * Use of RestHighLevelClient to connect our java client with elasticsearch.
     * 
     * RestHighLevelClient is used instead of ES transportClient, since ES is planning to deprecate
     * transportClient in it's future version.
     * 
     */
    private RestHighLevelClient client;

    @Inject
    public GetStoreDataES(RestHighLevelClient restHighLevelClient) {
		this.client=restHighLevelClient;
	}
    
    public List < StoresDetails > getstoredata(final NearByStore User) throws Exception {


        List < StoresDetails > details = new ArrayList < StoresDetails > ();
        SearchRequest searchRequest = new SearchRequest(Constants.INDEX_NAME);

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
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder geoDistanceQueryBuilder=QueryBuilders
						        		 .geoDistanceQuery(Constants.GEO_POINT_FIELD)
						                 .point(User.getLat(), User.getLon())
						                 .distance(User.getRadius(), DistanceUnit.KILOMETERS)
						                 .geoDistance(GeoDistance.ARC);
        QueryBuilder queryTerm;
        if(User.getCategory().equals(Constants.ALL))
        {
        	 queryTerm=QueryBuilders
        			.termsQuery("category.keyword", "electronics", "QSR", "garments", "Dairy", "grocery");
        }
        else
        {
        	 queryTerm=QueryBuilders
        			.termQuery("category.keyword", User.getCategory());
        }
        
        QueryBuilder completeQuery=QueryBuilders
        		.boolQuery()
        		.must(queryTerm)
        		.filter(geoDistanceQueryBuilder);

        searchSourceBuilder.query(completeQuery);
           
        /*
         * This particular line of code is for sorting Stores based on distance from CenterPoint.
         */
        searchRequest.source(searchSourceBuilder.sort(SortBuilders.geoDistanceSort(Constants.GEO_POINT_FIELD, User.getLat(), User.getLon()).order(SortOrder.ASC).unit(DistanceUnit.KILOMETERS)));
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
        for (SearchHit hit: searchHits) {
            JSONObject obj1 = new JSONObject(hit.toString());
            JSONArray obj2 = obj1.getJSONArray("sort");
            obj1 = obj1.getJSONObject("_source");
         
                StoresDetails sd = StoresDetails.builder().build();
                sd.setMerchantname(obj1.get(Constants.MERCHANT_NAME).toString());
                sd.setOpentime(obj1.get(Constants.OPEN_TIME).toString());
                sd.setClosetime(obj1.get(Constants.CLOSE_TIME).toString());
                sd.setAddress(obj1.get(Constants.ADDRESS).toString());
                sd.setCategory(obj1.get(Constants.STORE_CATEGORY).toString());
                if (obj1.has(Constants.HOME_DELIVERY))
                    sd.setRangeofhomedelivery(obj1.get(Constants.RANGE_OF_DELIVERY).toString());
                sd.setDistance(obj2.getDouble(0));
                details.add(sd);
        }
        return details;
    }

}