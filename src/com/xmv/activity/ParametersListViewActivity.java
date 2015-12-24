package com.xmv.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xmv.connection.DBConnection;
import com.xmv.data_access_model.ParameterDataUnitInfo;
import com.xmv.data_access_model.ScreenHeaderInfo;
import com.xmv.data_access_model.VariableScreenInfo;
import com.xmv.widget.adapter.CustomListViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ParametersListViewActivity extends ActionBarActivity{

	String screenHeaderName = "";
	int listLevelCount, screenListIndex, paramLength, screenListRootId;
	String parameterMenu;
	String[] parameter_Array;
	int[] parameterAddr_Array;
	String[] parameterUnit_Array;
	String[] tVariableType;
	String[] uNumberType;
	String[] iBase;
	String[] vEnumType;
	public VariableScreenInfo[] varScrInfo;
	
	CustomListViewAdapter adapter;
	ArrayList<ParameterDataUnitInfo> paramDetails = new ArrayList<ParameterDataUnitInfo>();
	
	CardView cardView;
	ListView listView;
	SharedPreferences sharedpreferences;
	
	private ActionBar mActionBar;
    private Toolbar mToolbar;
    private DBConnection dbCon;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.group_listview_activity);
        
        screenHeaderName = getIntent().getExtras().getString("screenHeaderName");
        restoreActionBar();
        
        listLevelCount = getIntent().getExtras().getInt("listLevelCount");
        screenListIndex = getIntent().getExtras().getInt("screenListIndex");
        screenListRootId = getIntent().getExtras().getInt("screenListRootId");

        cardView = (CardView)findViewById(R.id.group_list_cardview);
        listView = (ListView)findViewById(R.id.group_listview);
        
        dbCon = new DBConnection(this);
        getSubMenuFromSharedPreferences();
        displaySubMenuList();

        //displayAddrValueInGUI();
	    //getParamDataFromDrive();
    }
    
    public void restoreActionBar() {
    	 mToolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(mToolbar);
         mActionBar = getSupportActionBar();
         mActionBar.setDisplayHomeAsUpEnabled(true);
         mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
         mActionBar.setDisplayShowTitleEnabled(true);
         mActionBar.setTitle(screenHeaderName);
    }
    
    private void getSubMenuFromSharedPreferences()
	{
    	Cursor cursor = null;
    	cursor = dbCon.getVariableScreenList(screenListRootId);
    	if(cursor != null && cursor.getCount() > 0)
        {
    		varScrInfo = new VariableScreenInfo[cursor.getCount()];
			
			parameter_Array = new String[cursor.getCount()];
			parameterAddr_Array = new int[cursor.getCount()];
			parameterUnit_Array = new String[cursor.getCount()];
			tVariableType = new String[cursor.getCount()];
			uNumberType = new String[cursor.getCount()];
			iBase = new String[cursor.getCount()];
			vEnumType = new String[cursor.getCount()];
			
    		cursor.moveToFirst();
    		int count  = 0;
	    	do{
	    		varScrInfo[count] = new VariableScreenInfo();
            	varScrInfo[count].setaSpanish("" + cursor.getString(cursor.getColumnIndex("aSpanish")));
				varScrInfo[count].setbEnglish(""+cursor.getString(cursor.getColumnIndex("bEnglish")));
				varScrInfo[count].setcGerman(""+cursor.getString(cursor.getColumnIndex("cGerman")));
				varScrInfo[count].setbModbusAddr(cursor.getInt(cursor.getColumnIndex("bModBusAddr")));
				varScrInfo[count].setcFixedUnit(""+cursor.getString(cursor.getColumnIndex("cFixedUnit")));
				varScrInfo[count].settVariableType(""+cursor.getString(cursor.getColumnIndex("tVariableType")));
				varScrInfo[count].setuNumberType(""+cursor.getString(cursor.getColumnIndex("uNumberType")));
				varScrInfo[count].setiBase(""+cursor.getString(cursor.getColumnIndex("iBase")));
				varScrInfo[count].setvEnumType(""+cursor.getString(cursor.getColumnIndex("vEnumType")));
				
				
            	parameter_Array[count] = cursor.getString(cursor.getColumnIndex("bEnglish"));
            	parameterAddr_Array[count] = cursor.getInt(cursor.getColumnIndex("bModBusAddr"));
            	
            	if(cursor.getString(cursor.getColumnIndex("cFixedUnit")).equals("None"))
            	{
            		parameterUnit_Array[count] = "";
            	}else{
            		parameterUnit_Array[count] = ""+cursor.getString(cursor.getColumnIndex("cFixedUnit"));
            	}
            	ScreenHeaderInfo.hashMap_addrList.put(""+cursor.getInt(cursor.getColumnIndex("bModBusAddr")), "0");
            	
            	tVariableType[count] = ""+cursor.getString(cursor.getColumnIndex("tVariableType"));
            	uNumberType[count] = ""+cursor.getString(cursor.getColumnIndex("uNumberType"));
            	iBase[count] = ""+cursor.getString(cursor.getColumnIndex("iBase"));
            	vEnumType[count] = ""+cursor.getString(cursor.getColumnIndex("vEnumType"));
            	
            	count++;
	    	}while(cursor.moveToNext());
	    	paramLength = parameter_Array.length;
        }
	}
    
    private void getSubMenuFromSharedPreferences1()
	{
		JSONObject fChild, singleObject = null;
		sharedpreferences = getSharedPreferences("dScreenList", Context.MODE_PRIVATE);
		
		if (sharedpreferences.contains("scrList_subMenu_"+ listLevelCount + "_" + screenListIndex))
        {
			parameterMenu = sharedpreferences.getString("scrList_subMenu_"+listLevelCount + "_" + screenListIndex, "");
        	
        	try {
        		
        		JSONObject jsonObject = new JSONObject(parameterMenu);
	            fChild = jsonObject.getJSONObject("fChild");
	            
	            JSONArray bVariableScreen = fChild.optJSONArray("bVariableScreen");
	            
    			if(bVariableScreen == null)
    			{
    				singleObject = fChild.optJSONObject("bVariableScreen");
    			}
    			
    			if(singleObject != null)
        		{ 
    				varScrInfo = new VariableScreenInfo[1];
    				varScrInfo[0] = new VariableScreenInfo();
    				varScrInfo[0].setaSpanish(""+singleObject.getJSONObject("sDescription").getString("aSpanish"));
    				varScrInfo[0].setbEnglish(""+singleObject.getJSONObject("sDescription").getString("bEnglish"));
    				varScrInfo[0].setcGerman(""+singleObject.getJSONObject("sDescription").getString("cGerman"));
    				varScrInfo[0].setbModbusAddr(singleObject.getInt("bModBusAddr"));
    				varScrInfo[0].setcFixedUnit(""+singleObject.getString("cFixedUnit"));
    				varScrInfo[0].settVariableType(""+singleObject.getString("tVariableType"));
    				varScrInfo[0].setuNumberType(""+singleObject.getString("uNumberType"));
    				varScrInfo[0].setiBase(""+singleObject.getString("iBase"));
    				varScrInfo[0].setvEnumType(""+singleObject.getString("vEnumType"));
    				
    				parameter_Array = new String[1];
    				parameterAddr_Array = new int[1];
    				parameterUnit_Array = new String[1];
    				tVariableType = new String[1];
    				uNumberType = new String[1];
    				iBase = new String [1];
    				vEnumType = new String[1];
    				
    				parameter_Array[0] = (singleObject.getJSONObject("sDescription").getString("bEnglish"));
    				parameterAddr_Array[0] = singleObject.getInt("bModBusAddr");
    				parameterUnit_Array[0] = singleObject.getString("cFixedUnit");
    				tVariableType[0] = singleObject.getString("tVariableType");
    				uNumberType[0] = singleObject.getString("uNumberType");
    				iBase[0] = singleObject.getString("iBase");
    				vEnumType[0] = singleObject.getString("vEnumType");
    				
        		}else{
        			varScrInfo = new VariableScreenInfo[bVariableScreen.length()];
        			
        			parameter_Array = new String[bVariableScreen.length()];
        			parameterAddr_Array = new int[bVariableScreen.length()];
        			parameterUnit_Array = new String[bVariableScreen.length()];
        			tVariableType = new String[bVariableScreen.length()];
        			uNumberType = new String[bVariableScreen.length()];
        			iBase = new String[bVariableScreen.length()];
        			vEnumType = new String[bVariableScreen.length()];
    	       
    	            for(int i=0;i<varScrInfo.length;i++)
    	            {
    	            	varScrInfo[i] = new VariableScreenInfo();
    	            	JSONObject mainMenuScreen = bVariableScreen.getJSONObject(i);
    	            	varScrInfo[i].setaSpanish("" + mainMenuScreen.getJSONObject("sDescription").getString("aSpanish"));
        				varScrInfo[i].setbEnglish(""+mainMenuScreen.getJSONObject("sDescription").getString("bEnglish"));
        				varScrInfo[i].setcGerman(""+mainMenuScreen.getJSONObject("sDescription").getString("cGerman"));
        				varScrInfo[i].setbModbusAddr(mainMenuScreen.getInt("bModBusAddr"));
        				varScrInfo[i].setcFixedUnit(""+mainMenuScreen.getString("cFixedUnit"));
        				varScrInfo[i].settVariableType(""+mainMenuScreen.getString("tVariableType"));
        				varScrInfo[i].setuNumberType(""+mainMenuScreen.getString("uNumberType"));
        				varScrInfo[i].setiBase(""+mainMenuScreen.getString("iBase"));
        				varScrInfo[i].setvEnumType(""+mainMenuScreen.getString("vEnumType"));
        				
        				
    	            	parameter_Array[i] = mainMenuScreen.getJSONObject("sDescription").getString("bEnglish");
    	            	parameterAddr_Array[i] = mainMenuScreen.getInt("bModBusAddr");
    	            	
    	            	if(mainMenuScreen.getString("cFixedUnit").equals("None"))
    	            	{
    	            		parameterUnit_Array[i] = "";
    	            	}else{
    	            		parameterUnit_Array[i] = ""+mainMenuScreen.getString("cFixedUnit");
    	            	}
    	            	ScreenHeaderInfo.hashMap_addrList.put(""+mainMenuScreen.getString("bModBusAddr"), "0");
    	            	
    	            	tVariableType[i] = ""+mainMenuScreen.getString("tVariableType");
    	            	uNumberType[i] = ""+mainMenuScreen.getString("uNumberType");
    	            	iBase[i] = ""+mainMenuScreen.getString("iBase");
    	            	vEnumType[i] = ""+mainMenuScreen.getString("vEnumType");
    	            	
    	            }
        		}
    			paramLength = parameter_Array.length;
	            
           }catch (JSONException e) {
				e.printStackTrace();
		   }

        }
	}
    
    private void displaySubMenuList()
	{
		
	    if(paramDetails.size() > 0)
	    {
	    	paramDetails.clear();
	    }
	    
		for(int i=0;i<paramLength;i++)
		{ 
			ParameterDataUnitInfo item = new ParameterDataUnitInfo(parameter_Array[i], ""+ScreenHeaderInfo.hashMap_addrList.get(""+parameterAddr_Array[i])+parameterUnit_Array[i]);
			paramDetails.add(item);
		}
		
		adapter = new CustomListViewAdapter(this, R.layout.parameter_dataunit, paramDetails);
		
		adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        startAnimation();
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// ListView Clicked item value
	               
	               Toast toast = Toast.makeText(getApplicationContext(), "Item " + (position + 1) + ": " + paramDetails.get(position),Toast.LENGTH_SHORT);
                   toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                   toast.show();
			}
		});
        
	}
    
    private boolean isUp;
    private void startAnimation()
    {
    	final float direction = (isUp) ? -1 : 1;
        final float yDelta = getScreenHeight() + 10; //(2 * cardView.getHeight());
        final int layoutTopOrBottomRule = (isUp) ? RelativeLayout.ALIGN_PARENT_TOP : RelativeLayout.ALIGN_PARENT_BOTTOM;
        final Animation animation = new TranslateAnimation(0,0,yDelta * direction,0);

        animation.setDuration(500);
        animation.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {

                // fix flicking
                // Source : http://stackoverflow.com/questions/9387711/android-animation-flicker
                TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                anim.setDuration(1);
                cardView.startAnimation(anim);

                //set new params
                /*LayoutParams params = new LayoutParams(cardView.getLayoutParams());
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(layoutTopOrBottomRule);
                cardView.setLayoutParams(params);*/
            }
        });

        cardView.startAnimation(animation);

        //reverse direction
        //isUp = !isUp;
    }
    
    private float getScreenHeight() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return (float) displaymetrics.heightPixels;

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
        	
        	// app icon in action bar clicked; go home
            
        	Intent intent=new Intent();
        	//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("listLevelCount",listLevelCount);
              
            setResult(1,intent);  
              
            finish();//finishing activity  
            
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 
	 public void onBackPressed()
	 {
		 Intent intent=new Intent();  
         intent.putExtra("listLevelCount",listLevelCount);
         setResult(1,intent);  
           
         finish();//finishing activity  
	 }
}
