package com.com;

import java.util.Scanner;

public class Call {

	public static void main(String[] args) {
		Double Lat,Lon;
		Scanner in =new Scanner(System.in);
		String pincode;
		pincode=in.nextLine();
		Calling API =new Calling();
		Lat=API.CallerLat(pincode);
		Lon=API.CallerLong(pincode);
		System.out.println(Lat);
		System.out.println(Lon);
		in.close();
	}
}
