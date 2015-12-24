package com.xmv.activity;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.xmv.connection.DBConnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity{

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.splash_screen);
        
        ScreenListAsyncTask asyncTask = new ScreenListAsyncTask();
        asyncTask.execute();
    }
    
    public class ScreenListAsyncTask extends AsyncTask<String, Void, String>
    {
		@Override
		protected String doInBackground(String... params) {

			//getParametersFromXMLFile();
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			startActivity(new Intent(getApplication(),MainActivity.class));
            SplashActivity.this.finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  
		}	
    }
    
    public void getParametersFromXMLFile()
    {
    	final AssetManager assertManager = getAssets();
	
		// To load text file
        InputStream input;
		  try {
			  //input = assertManager.open("xmvparameters.pcs");
			  input = assertManager.open("xmv600_adu.xml");

	          int size = input.available();
	          byte[] buffer = new byte[size];
	          input.read(buffer);
	          input.close();

	          // byte buffer into a string
	          String text = new String(buffer);
	          
	          JSONObject jsonObject = XML.toJSONObject(text);
	          
	            try {
	            	JSONObject PowerSLE = jsonObject.getJSONObject("PowerSLE");
	            	
	            	//dScreenList
	            	JSONObject dScreenList = PowerSLE.getJSONObject("dScreenList");
	            	JSONObject aMenuScreen = dScreenList.getJSONObject("aMenuScreen");
	            	
	            	DBConnection dbCon = new DBConnection(getApplicationContext());
	            	dbCon.updatePowerSLEppScreenListInDatabase(aMenuScreen);
	            	
	            	SharedPreferences sharedpreferences = getSharedPreferences("dScreenList", Context.MODE_PRIVATE);
	            	Editor editor = sharedpreferences.edit();
	            	editor.putString("aMenuScreen", dScreenList.toString());
	            	editor.commit();
	            	
	            	//DBConnection dbCon = new DBConnection(getApplicationContext());
	            	
		            if(DBConnection.doesDatabaseExist(getApplicationContext(), "xmv_db"))
		            {
		            	System.out.println("EXSISTSSSSS");
		            	dbCon.getReadableDBConnection();
		            	if(dbCon.getEnumCount() == 0)
		            	{
		            		dbCon.getWritableDatabase();
		            		
			            	//bEnumList
			            	JSONObject bEnumList = PowerSLE.getJSONObject("bEnumList");
			            	JSONArray aEnumInfo = bEnumList.getJSONArray("aEnumInfo");
			            	
			            	int enumListCount = 0;
			            	int temp_ArrayLength = aEnumInfo.length(); 
			            	for(int i=0; i<temp_ArrayLength;i++)
			            	{
			            		JSONObject aEnumDetails = aEnumInfo.getJSONObject(i); 
			            		String aName = aEnumDetails.getString("aName");
				            	String cExEnum = aEnumDetails.getString("cExEnum");
				            	
				            	enumListCount = i+1;
				            	dbCon.addEnumListContent(enumListCount, aName, cExEnum);
				            	
				            	JSONArray bEnumItem = aEnumDetails.optJSONArray("bEnumItem");
				            	if(bEnumItem != null)
				            	{
					            	int temp_EnumItemLength = bEnumItem.length();
					            	for(int j=0; j<temp_EnumItemLength ;j++)
					            	{
					            		JSONObject aEnumItemDetails = bEnumItem.getJSONObject(j); 
					            		String aValue = aEnumItemDetails.getString("aValue");
						            	String bSpanish = aEnumItemDetails.getString("bSpanish");
						            	String cEnglish = aEnumItemDetails.getString("cEnglish");
						            	String dGerman = aEnumItemDetails.getString("dGerman");
						            	
						            	dbCon.addEnumItemContent(enumListCount, aValue, bSpanish, cEnglish, dGerman);
					            	}
				            	}
			            	}
		            		
		            	}
		            	dbCon.closeDBConnection();
		            }else{
		            	
		            	dbCon.getWritableDatabase();
		            	System.out.println("NOT EXSISTSSSSS");
		            	//ScreenList
		            	dbCon.updatePowerSLEppScreenListInDatabase(aMenuScreen);
		            	
		            	//bEnumList
		            	JSONObject bEnumList = PowerSLE.getJSONObject("bEnumList");
		            	JSONArray aEnumInfo = bEnumList.getJSONArray("aEnumInfo");
		            	
		            	int enumListCount = 0;
		            	int temp_ArrayLength = aEnumInfo.length(); 
		            	for(int i=0; i<temp_ArrayLength;i++)
		            	{
		            		JSONObject aEnumDetails = aEnumInfo.getJSONObject(i); 
		            		String aName = aEnumDetails.getString("aName");
			            	String cExEnum = aEnumDetails.getString("cExEnum");
			            	
			            	enumListCount = i+1;
			            	dbCon.addEnumListContent(enumListCount, aName, cExEnum);
			            	
			            	JSONArray bEnumItem = aEnumDetails.optJSONArray("bEnumItem");
			            	if(bEnumItem != null)
			            	{
				            	int temp_EnumItemLength = bEnumItem.length();
				            	for(int j=0; j<temp_EnumItemLength ;j++)
				            	{
				            		JSONObject aEnumItemDetails = bEnumItem.getJSONObject(j); 
				            		String aValue = aEnumItemDetails.getString("aValue");
					            	String bSpanish = aEnumItemDetails.getString("bSpanish");
					            	String cEnglish = aEnumItemDetails.getString("cEnglish");
					            	String dGerman = aEnumItemDetails.getString("dGerman");
					            	
					            	dbCon.addEnumItemContent(enumListCount, aValue, bSpanish, cEnglish, dGerman);
				            	}
			            	}
		            	}
		            	
		            	dbCon.closeDBConnection();
		            }	
	            	
	            } catch (JSONException e) {
					e.printStackTrace();
				}

		  } catch (IOException e) {
		   e.printStackTrace();
		  } catch (JSONException e1) {
			e1.printStackTrace();
		}
    }
}
