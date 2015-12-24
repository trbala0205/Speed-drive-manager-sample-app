package com.xmv.data_access_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ScreenHeaderInfo {

	public static ArrayList<String> scr_headerName = new ArrayList<String>();
	public static ArrayList<Integer> scr_ListPosition = new ArrayList<Integer>();
	public static String screenLevel = "FORWARD";
	public static String globalIPaddress = "192.168.2.10";
	
	public static HashMap<String, String> hashMap_addrList = new HashMap<String, String>();
	
	public static ArrayList<Integer> groupLevelList = new ArrayList<Integer>();
	
	public ScreenHeaderInfo(){}
	
}
