package com.xmv.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xmv.connection.DBConnection;
import com.xmv.data_access_model.ScreenHeaderInfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class GroupListViewActivity extends ActionBarActivity{

	ListView listView;
	CardView cardView;
	SharedPreferences sharedpreferences;
    String aMenuScreen="";
    
    ArrayAdapter<String> adapter;
    ArrayList<String> aMenuScreen_ArrayList = new ArrayList<String>();//String[] aMenuScreen_Array;
    ArrayList<Integer> aScreenList_rootId = new ArrayList<Integer>();
    //FloatingActionButton floatButton;
    
    public static int listLevelCount = 0;
    String screenLevelHeaderName = "";
    static int currentListIndex;
    private String screenMove = "FORWARD"; 
    
    ActionBar mActionBar;
    Toolbar mToolbar;
    private DBConnection dbCon;
    
	@Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static Intent getStartIntent(Context context) {
        Intent starter = new Intent(context, GroupListViewActivity.class);
        //starter.putExtra(Category.TAG, category.getId());
        return starter;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_listview_activity);
        
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Group");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        
        cardView = (CardView)findViewById(R.id.group_list_cardview);
        listView = (ListView)findViewById(R.id.group_listview);
        
        dbCon = new DBConnection(this);
        
        if(savedInstanceState != null)
        {
        	currentListIndex = savedInstanceState.getInt("currentListIndex");
        	screenMove = savedInstanceState.getString("screenMove");
        	aMenuScreen_ArrayList  = savedInstanceState.getStringArrayList("screenList_name");
         	aScreenList_rootId  = savedInstanceState.getIntegerArrayList("screenList_id");
        	displayGroupView();
        }else
        	getMenuListFromPreferences();
    }
    
    public void getMenuListFromPreferences()
    {
    	
    	 /*JSONObject fChild;
    	 sharedpreferences = getSharedPreferences("dScreenList", Context.MODE_PRIVATE);

         if (sharedpreferences.contains("aMenuScreen"))
         {
         	aMenuScreen = sharedpreferences.getString("aMenuScreen", "");
         	try {
         		JSONObject jsonObject = new JSONObject(aMenuScreen);
 	            JSONObject aMenuScreen = jsonObject.getJSONObject("aMenuScreen");
 	            fChild = aMenuScreen.getJSONObject("fChild");
 	            JSONArray top_aMenuScreen = fChild.getJSONArray("aMenuScreen");
 	            //aMenuScreen_Array = new String[top_aMenuScreen.length() - (top_aMenuScreen.length() - 2)];
 	           //aMenuScreen_Array = new String[top_aMenuScreen.length()];
 	            
 	            Editor editor = sharedpreferences.edit();
 	            
 	            for(int i=0;i < 2;i++)
 	            {
 	            	JSONObject mainMenuScreen = top_aMenuScreen.getJSONObject(i);
 	            	aMenuScreen_ArrayList.add(mainMenuScreen.getJSONObject("dDescription").getString("bEnglish"));
 	            	editor.putString("scrList_subMenu_" + listLevelCount + "_" + (i), mainMenuScreen.toString());
 	            	
 	            }
 	            editor.commit();
 	            
            }catch (JSONException e) {
 				e.printStackTrace();
 		   }
         	
         	displayGroupView();

         }*/
    	Cursor cursor = null;
    	if(ScreenHeaderInfo.groupLevelList.size() == 0)
    	{
    		if(screenMove.equals("FORWARD"))
    		{
	    		ScreenHeaderInfo.groupLevelList.add(1);
	    		cursor = dbCon.getScreenList(1);
    		}else
    			cursor = dbCon.getScreenList(1);
    	}else{
    		if(screenMove.equals("FORWARD"))
    		{
    			ScreenHeaderInfo.groupLevelList.add(aScreenList_rootId.get(currentListIndex));
    			cursor = dbCon.getScreenList(aScreenList_rootId.get(currentListIndex));
    		}else
    			cursor = dbCon.getScreenList(ScreenHeaderInfo.groupLevelList.get(ScreenHeaderInfo.groupLevelList.size()-1));
    	}
    	
    	if(cursor.getCount() == 0)
    	{
    		ScreenHeaderInfo.groupLevelList.remove(ScreenHeaderInfo.groupLevelList.size()-1); // It will remove the parameter root id
    		Intent intent = new Intent(this, ParametersListViewActivity.class);
    		intent.putExtra("listLevelCount", listLevelCount);
    		intent.putExtra("screenHeaderName", ""+screenLevelHeaderName);
    		intent.putExtra("screenListIndex", currentListIndex);
    		intent.putExtra("screenListRootId", aScreenList_rootId.get(currentListIndex));
    		startActivityForResult(intent, 1);
    		
    		/*cursor = dbCon.getVariableScreenList(ScreenHeaderInfo.groupLevelList.get(ScreenHeaderInfo.groupLevelList.size()-1));
    		aMenuScreen_ArrayList.clear();
	    	aScreenList_rootId.clear();
	    	if(cursor != null && cursor.getCount() > 0)
	        {
	    		cursor.moveToFirst();
		    	do{
		    		aMenuScreen_ArrayList.add(cursor.getString(cursor.getColumnIndex("aName")));
		    		aScreenList_rootId.add(cursor.getInt(cursor.getColumnIndex("parentId")));
		    	}while(cursor.moveToNext());
	        }*/
    	}else{
	    	aMenuScreen_ArrayList.clear();
	    	aScreenList_rootId.clear();
	    	if(cursor != null && cursor.getCount() > 0)
	        {
	    		cursor.moveToFirst();
		    	do{
		    		aMenuScreen_ArrayList.add(cursor.getString(cursor.getColumnIndex("aName")));
		    		aScreenList_rootId.add(cursor.getInt(cursor.getColumnIndex("root_id")));
		    	}while(cursor.moveToNext());
	        }
	    	
    	}
    	cursor.close();
    	//aMenuScreen_ArrayList = dbCon.getScreenList();
    	displayGroupView();
    }
    
    public void displayGroupView()
    {
    	//listLevelCount = 0;
    	
    	//getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    	if(ScreenHeaderInfo.groupLevelList.size() > 1)
    	{
    		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, aMenuScreen_ArrayList);
	        //Assign adapter to ListView
	        listView.setAdapter(adapter); 
    		adapter.notifyDataSetChanged();
    	}
    	else{
    		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, aMenuScreen_ArrayList);
	        //Assign adapter to ListView
	        listView.setAdapter(adapter); 
	        adapter.notifyDataSetChanged();
	        
	        cardView.setVisibility(View.VISIBLE);
    	}
        startAnimation();
        //floatButton.attachToListView(listView);
        //floatButton.listenTo(listView);
        //floatButton.hide(true);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// ListView Clicked item value
                String  itemValue = (String) listView.getItemAtPosition(position);
                
                screenLevelHeaderName = itemValue;
                getSupportActionBar().setTitle(""+screenLevelHeaderName);
                //floatButton.setVisibility(View.VISIBLE);
                
                //Editor editor = sharedpreferences.edit();
                //editor.putInt("scrList_subMenuPosition_" + listLevelCount , position);
                //editor.putString("scrList_subMenuName_" + listLevelCount , screenLevelHeaderName);
                //editor.commit();

                screenLevelIncrement();
                currentListIndex = position;
                screenMove = "FORWARD";
                //getSubMenuFromSharedPreferences();
                getMenuListFromPreferences();
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
    
    public void screenLevelIncrement()
    {
    	listLevelCount ++;
    }
    
    public void screenLevelDecrement()
    {
    	listLevelCount --;
    }
    
    @SuppressWarnings("null")
	public void getSubMenuFromSharedPreferences()
	{
    	//listLevelCount = 1;
    	/*String screenType = "MENU"; 
    	
		JSONObject fChild, singleObject = null;
		JSONArray top_aMenuScreen = null;
		String descriptionlabel = "dDescription";
		mActionBar.setTitle(""+sharedpreferences.getString("scrList_subMenuName_" + (listLevelCount - 1), ""));
		
		currentListIndex = sharedpreferences.getInt("scrList_subMenuPosition_" + (listLevelCount - 1), 0);
		
		if (sharedpreferences.contains("scrList_subMenu_" + (listLevelCount-1) + "_" +currentListIndex))
        {
        	aMenuScreen = sharedpreferences.getString("scrList_subMenu_" + (listLevelCount-1) + "_" +currentListIndex, "");

        	try {
        		JSONObject jsonObject = new JSONObject(aMenuScreen);
        		
	            fChild = jsonObject.getJSONObject("fChild");
	          
            	top_aMenuScreen = fChild.optJSONArray("aMenuScreen");
            	
            	if(top_aMenuScreen == null)
            	{
        			top_aMenuScreen = fChild.optJSONArray("bVariableScreen");
        			if(top_aMenuScreen == null)
        			{
        				singleObject = fChild.optJSONObject("bVariableScreen");
        			}

	            	descriptionlabel = "sDescription";
	            	screenType = "VARIABLE";
	            
	            	screenLevelDecrement();
	            	if(!ScreenHeaderInfo.hashMap_addrList.isEmpty())
	            	{	
	            		ScreenHeaderInfo.hashMap_addrList.clear();
	            	}
	            	
	        		Intent intent = new Intent(this, ParametersListViewActivity.class);
	        		intent.putExtra("listLevelCount", listLevelCount);
	        		intent.putExtra("screenHeaderName", ""+screenLevelHeaderName);
	        		intent.putExtra("screenListIndex", currentListIndex);
	        		startActivityForResult(intent, 1);
	        		
	        		return;
            	}
	           
	            Editor editor = sharedpreferences.edit();
	           
	            
	            adapter.notifyDataSetChanged();
	            aMenuScreen_ArrayList.clear();
	           
        			if(screenType.equals("MENU"))
	            	{
        				int tempListCount = top_aMenuScreen.length();
			            for(int i=0;i<tempListCount;i++)
			            {
			            	JSONObject subMenuScreen = top_aMenuScreen.getJSONObject(i);
			            	aMenuScreen_ArrayList.add(subMenuScreen.getJSONObject(descriptionlabel).getString("bEnglish"));
			            	
			            	//if(!sharedpreferences.contains("scrList_subMenu_" + listLevelCount + "_" +(i)))
			            	//{
			            		editor.putString("scrList_subMenu_" + listLevelCount + "_" +(i), subMenuScreen.toString());
			            	//}
			            	
			            }
	            	}
        		
	            editor.commit();

           }catch (JSONException e) {
				e.printStackTrace();
		   }
        	
        	if(screenType.equals("MENU"))
        	{
        		displayGroupSublist();
        	}else if(screenType.equals("VARIABLE")){}

        }*/
	}
    
    public void displayGroupSublist()
    {
    	
    	//floatButton.hide(false);
 
    	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, aMenuScreen_ArrayList);

        // Assign adapter to ListView
           listView.setAdapter(adapter); 
           startAnimation();
           listView.setOnItemClickListener(new OnItemClickListener() {

   			@Override
   			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
   				
   				// ListView Clicked item value
                   String  itemValue = (String) listView.getItemAtPosition(position);
                   screenLevelHeaderName = itemValue;
                   mActionBar.setTitle(""+screenLevelHeaderName);
                   
                   Editor editor = sharedpreferences.edit();
                   editor.putInt("scrList_subMenuPosition_" + listLevelCount , position);
                   editor.putString("scrList_subMenuName_" + listLevelCount , screenLevelHeaderName);
                   editor.commit();
                   
                   screenLevelIncrement();
                   currentListIndex = position;
                   getSubMenuFromSharedPreferences();
   				
   			}
   		});
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentListIndex", currentListIndex);
        outState.putString("screenMove", screenMove);
        outState.putStringArrayList("screenList_name", aMenuScreen_ArrayList);
        outState.putIntegerArrayList("screenList_id", aScreenList_rootId);
       
        super.onSaveInstanceState(outState);
    }

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	currentListIndex = savedInstanceState.getInt("currentListIndex");
    	screenMove = savedInstanceState.getString("screenMove");
    	aMenuScreen_ArrayList  = savedInstanceState.getStringArrayList("screenList_name");
     	aScreenList_rootId  = savedInstanceState.getIntegerArrayList("screenList_id");
        //showPhoto(mCurrentPhotoIndex);
     	displayGroupView();
        super.onRestoreInstanceState(savedInstanceState);
    }*/
    
    // Check screen orientation or screen rotate event here
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
    }
    
    public void onBackPressed() {
    	
        listLevelCount --;
        
        /*if(listLevelCount == 0)
        {
        	adapter.notifyDataSetChanged();
        	aMenuScreen_ArrayList.clear();
        	getMenuListFromPreferences();
        }
        else if(listLevelCount > 0)
        {
        	getSubMenuFromSharedPreferences();
        }else{
        	finish();
        }*/
        screenMove = "BACKWARD";
        if(ScreenHeaderInfo.groupLevelList.size() > 1)
        {
        	ScreenHeaderInfo.groupLevelList.remove(ScreenHeaderInfo.groupLevelList.size()-1);
        	adapter.notifyDataSetChanged();
        	//aMenuScreen_ArrayList.clear();
        	getMenuListFromPreferences();
        }else{
        	ScreenHeaderInfo.groupLevelList.clear();
        	finish();
        }
    }
}
