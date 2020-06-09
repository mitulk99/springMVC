package com.amazon.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class PincodeToCoords
{
	 private Double lat,lon;
	 private String pincode;
	 public void getCoordinates() throws Exception
	 {
			
				String url = "https://nominatim.openstreetmap.org/search?q=";
			     URL obj = new URL(url+pincode+"&format=json");
			     HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			     int responseCode = con.getResponseCode();

			     BufferedReader in = new BufferedReader(
			             new InputStreamReader(con.getInputStream()));
			     String inputLine;
			     StringBuffer response = new StringBuffer();
			     while ((inputLine = in.readLine()) != null) {
			     	response.append(inputLine);
			     }
			     in.close();
			     
			     JSONArray myresponse= new JSONArray(response.toString());
			     JSONObject object = myresponse.getJSONObject(0);
			     lat=Double.valueOf(object.getString("lat"));
			     lon=Double.valueOf(object.getString("lon"));
				}

	 }


