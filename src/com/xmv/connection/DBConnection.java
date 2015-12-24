package com.xmv.connection;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xmv.data_access_model.ScreenHeaderInfo;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBConnection extends SQLiteOpenHelper{

	// Logcat tag
    private static final String LOG = DBConnection.class.getName();
    
	// Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    //private static final String DATABASE_NAME = "PowerSLEParameters";
    private static final String DATABASE_NAME = "xmv_db";
 
    // Common column names
    private static final String id = "id";
    
    // bEnumList table name
    private static final String TABLE_BENUMLIST = "bEnumList";
    // bEnumList Table Column names
    private static final String aName = "aName";
    private static final String cExEnum = "cExEnum";
    
    // bEnumItem table name
    private static final String TABLE_BENUMITEM = "bEnumItem";
    // bEnumItem Table Column names
    private static final String EnumList_id = "EnumList_id";
    private static final String aValue = "aValue";
    private static final String bSpanish = "bSpanish";
    private static final String cEnglish = "cEnglish";
    private static final String dGerman = "dGerman";
    
  //dScreenList table name
    private static final String TABLE_DSCREENLIST = "dScreenList";
    //dScreenList Table Columns names
    private static final String root_id = "root_id";
    private static final String screenRef_id = "screenRef_id";
    private static final String bProgModeHide = "bProgModeHide";
    private static final String cShortMenuhide = "cShortMenuhide";
    private static final String gConditionHide = "gConditionHide";
    private static final String eHide = "eHide";
    private static final String aSpanish = "aSpanish";
    private static final String bEnglish = "bEnglish";
    private static final String cGerman = "cGerman";
    
    private String _aName = "";
    private String _bProgModeHide = "";
    private String _cShortMenuhide = "";
    private String _eHide = "";
    private String _gConditionHide = "";
    private String _aSpanish = "";
    private String _bEnglish = "";
    private String _cGerman = "";
	
	//bVariableScreen Table name
	private static final String TABLE_BVARIABLESCREEN = "bVariableScreen";
	//bVariableScreen Table Columns names
    private static final String bModBusAddr = "bModBusAddr";
    private static final String cFixedUnit = "cFixedUnit";
    private static final String dVariableUnit = "dVariableUnit";
    private static final String eReferenceAddress = "eReferenceAddress";
    private static final String fMaxVal = "fMaxVal";
    private static final String gMinVal = "gMinVal";
    private static final String hDefault = "hDefault";
    private static final String iBase = "iBase";
    private static final String jProgModeRead = "jProgModeRead";
    private static final String kProgModeHide = "kProgModeHide";
    private static final String lShortMenuhide = "lShortMenuhide";
    private static final String mModifiable = "mModifiable";
    private static final String nOffToModifiy = "nOffToModifiy";
    private static final String oSign = "oSign";
    private static final String pMinRefAddr = "pMinRefAddr";
    private static final String qMaxRefAddr = "qMaxRefAddr";
    private static final String rDynamicUpdation = "rDynamicUpdation";
    private static final String tVariableType = "tVariableType";
    private static final String uNumberType = "uNumberType";
    private static final String vEnumType = "vEnumType";
    private static final String wWidth = "wWidth";
    private static final String xEEPROM = "xEEPROM";
    private static final String yHide = "yHide";
    private static final String zProtectModeReadOnly = "zProtectModeReadOnly";
    private static final String AScreenType32Bit = "AScreenType32Bit";
    private static final String BConditionalHide = "BConditionalHide";
    private static final String CVarDepUnit = "CVarDepUnit";
    private static final String DDefaultRefAddr = "DDefaultRefAddr";
	
    SQLiteDatabase db;
    
    ArrayList<Integer> ref_ids = new ArrayList<Integer>();
	JSONObject variableObject;
	JSONArray variableScreen;
	int rowcount = 1, ref_id = 0;
	
	public DBConnection(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = getWritableDatabase();
		//System.out.println("CONSTRUCTOR");
	}

	public void getWritableDBConnection()
	{
		db = getWritableDatabase();
	}
	
	public void getReadableDBConnection()
	{
		db = getReadableDatabase();
	}
	
	public void closeDBConnection()
	{
		db.close();
	}
	
	public static boolean doesDatabaseExist(Context context, String dbName) {
	    File dbFile = context.getDatabasePath(dbName);
	    return dbFile.exists();
	}
	
	public Cursor getScreenList(int groupLevel)
	{
		String selectQuery = "";
		if(ScreenHeaderInfo.groupLevelList.size() == 0)
		{
			selectQuery = "SELECT * FROM " + TABLE_DSCREENLIST + " where "+screenRef_id+ "=1";
		}else{
			selectQuery = "SELECT * FROM " + TABLE_DSCREENLIST + " where "+screenRef_id+" = "+groupLevel;
		}
		Log.e(LOG, selectQuery);
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    //cursor.close();
	    //closeDBConnection();
	    return cursor;
	}
	
	public Cursor getVariableScreenList(int groupLevel)
	{
		String selectQuery = "";
		selectQuery = "SELECT * FROM " + TABLE_BVARIABLESCREEN + " where parentId="+groupLevel;
		Log.e(LOG, selectQuery);
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    //cursor.close();
	    //closeDBConnection();
	    return cursor;
	}
	
	public void updatePowerSLEppScreenListInDatabase(JSONObject aMenuScreen)
	{
		try {
    		_aName = aMenuScreen.optString("aName");
	    	_bProgModeHide = aMenuScreen.getString("bProgModeHide");
			_cShortMenuhide = aMenuScreen.getString("cShortMenuhide");
			_eHide = aMenuScreen.getString("eHide");
			_gConditionHide = aMenuScreen.getString("gConditionHide");
			
			JSONObject dDescription = aMenuScreen.getJSONObject("dDescription");
			_aSpanish = dDescription.getString("aSpanish");
			_bEnglish = dDescription.getString("bEnglish");
			_cGerman = dDescription.getString("cGerman");
			System.out.println("updatePowerSLEppScreenListInDatabase-------------------");
			add_dScreenListContent(1, _aName, 0, _aSpanish, _bEnglish, _cGerman, _bProgModeHide, _cShortMenuhide, _eHide, _gConditionHide);
			
			recursiveMenuScreen(aMenuScreen);
			
			closeDBConnection();
			
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONObject recursiveMenuScreen(JSONObject aMenuScreen)
	{
		
			try {
				
				if(rowcount == 1)
				{
					ref_ids.add(0);
				}
				System.out.println("VARIALe LENGTH: ------------------------");
				JSONObject fChild = aMenuScreen.getJSONObject("fChild");
				JSONArray child_aMenuScreen = fChild.optJSONArray("aMenuScreen");
				if(child_aMenuScreen == null)
				{
					JSONObject variableObject = fChild.optJSONObject("aMenuScreen");
					if(variableObject == null)
					{
						JSONArray variableScreen = fChild.optJSONArray("bVariableScreen");
	        			if(variableScreen == null)
	        			{
	        				variableObject = fChild.optJSONObject("bVariableScreen");
	        				addScreenListVariableScreen(rowcount, variableObject);
	        			}else{
	        				System.out.println("VARIALe LENGTH: "+variableScreen.length());
	        				for(int i=0; i<variableScreen.length(); i++)
	            			{
	            				JSONObject prop_varScreen = variableScreen.optJSONObject(i);
	            				addScreenListVariableScreen(rowcount, prop_varScreen);
	            			}
	        			}
					}else{
						
						rowcount ++;
						ref_id ++;
						ref_ids.add(ref_id);
						
						_aName = variableObject.optString("aName");
						_bProgModeHide = variableObject.getString("bProgModeHide");
						_cShortMenuhide = variableObject.getString("cShortMenuhide");
						_eHide = variableObject.getString("eHide");
						_gConditionHide = variableObject.getString("gConditionHide");
						
						JSONObject dDescription = variableObject.getJSONObject("dDescription");
						_aSpanish = dDescription.getString("aSpanish");
						_bEnglish = dDescription.getString("bEnglish");
						_cGerman = dDescription.getString("cGerman");
						
						add_dScreenListContent(rowcount, _aName, ref_ids.get(ref_ids.size() -1), _aSpanish, _bEnglish, _cGerman, _bProgModeHide, _cShortMenuhide, _eHide, _gConditionHide);
						System.out.println("---"+(rowcount)+"--NAMEEE:"+aMenuScreen.getString("aName")+" ---- "+ ref_ids.get(ref_ids.size() - 1)); 
						
						ref_ids.remove(ref_ids.size()-1);
						System.out.println("---------------------");
						recursiveMenuScreen(variableObject);
					}
        			
				}else{
					
					for(int i=0; i<child_aMenuScreen.length(); i++)
					{ 
						aMenuScreen = child_aMenuScreen.getJSONObject(i);
						rowcount ++;
						ref_id ++;
						if(i == 0)
						{
							ref_ids.add(ref_id);
						}
						
						_aName = aMenuScreen.optString("aName");
						_bProgModeHide = aMenuScreen.getString("bProgModeHide");
						_cShortMenuhide = aMenuScreen.getString("cShortMenuhide");
						_eHide = aMenuScreen.getString("eHide");
						_gConditionHide = aMenuScreen.getString("gConditionHide");
						
						JSONObject dDescription = aMenuScreen.getJSONObject("dDescription");
						_aSpanish = dDescription.getString("aSpanish");
						_bEnglish = dDescription.getString("bEnglish");
						_cGerman = dDescription.getString("cGerman");
						
						add_dScreenListContent(rowcount, _aName, ref_ids.get(ref_ids.size() -1), _aSpanish, _bEnglish, _cGerman, _bProgModeHide, _cShortMenuhide, _eHide, _gConditionHide);
						
						System.out.println("---"+(rowcount)+"--NAMEEE:"+aMenuScreen.getString("aName")+" ---- "+ ref_ids.get(ref_ids.size() - 1)); 
						
						if(i == (child_aMenuScreen.length()-1))
						{
							ref_ids.remove(ref_ids.size()-1);
							System.out.println("---------------------");
						}
						
						recursiveMenuScreen(aMenuScreen);
					}
				}
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}
	
	public void addScreenListVariableScreen(int parentId, JSONObject variableScreen)
	{
		System.out.println("---VARIABLE SCREEEN---");
		
		String var_aName = "";
		String var_bModBusAddr = "";
		String var_aSpanish = "";
		String var_bEnglish = "";
		String var_cGerman = "";
		String var_cFixedUnit = "";
		String var_dVariableUnit = "";
		String var_eReferenceAddress = "";
		String var_fMaxVal = "";
		String var_gMinVal = "";
		String var_hDefault = "";
		String var_iBase = "";
		String var_jProgModeRead = "";
		String var_kProgModeHide = "";
		String var_lShortMenuhide = "";
		String var_mModifiable = "";
		String var_nOffToModifiy = "";
		String var_oSign = "";
		String var_pMinRefAddr = "";
		String var_qMaxRefAddr = "";
		String var_rDynamicUpdation = "";
		String var_tVariableType = "";
		String var_uNumberType = "";
		String var_vEnumType = "";
		String var_wWidth = "";
		String var_xEEPROM = "";
		String var_yHide = "";
		String var_zProtectModeReadOnly = "";
		String var_AScreenType32Bit = "";
		String var_BConditionalHide = "";
		String var_CVarDepUnit = "";
		String var_DDefaultRefAddr = "";
	    
		getWritableDBConnection();
    	ContentValues values = new ContentValues();
    	
		//values.put(id, _id); // EnumItem count

    	
    	try {
    		
    		JSONObject description = variableScreen.getJSONObject("sDescription");
    		var_aSpanish = ""+description.getString("aSpanish");
			var_bEnglish = ""+description.getString("bEnglish");
			var_cGerman = ""+description.getString("cGerman");
			
			var_aName = ""+variableScreen.getString("aName");
			var_bModBusAddr = ""+variableScreen.getString("bModBusAddr");
			var_cFixedUnit = ""+variableScreen.getString("cFixedUnit");
			var_dVariableUnit = ""+variableScreen.getString("dVariableUnit");
			var_eReferenceAddress = ""+variableScreen.getString("eReferenceAddress");
			var_fMaxVal = ""+variableScreen.getString("fMaxVal");
			var_gMinVal = ""+variableScreen.getString("gMinVal");
			var_hDefault = ""+variableScreen.getString("hDefault");
			var_iBase = ""+variableScreen.getString("iBase");
			var_jProgModeRead = ""+variableScreen.getString("jProgModeRead");
			var_kProgModeHide = ""+variableScreen.getString("kProgModeHide");
			var_lShortMenuhide = ""+variableScreen.getString("lShortMenuhide");
			var_mModifiable = ""+variableScreen.getString("mModifiable");
			var_nOffToModifiy = ""+variableScreen.getString("nOffToModifiy");
			var_oSign = ""+variableScreen.getString("oSign");
			var_pMinRefAddr = ""+variableScreen.getString("pMinRefAddr");
			var_qMaxRefAddr = ""+variableScreen.getString("qMaxRefAddr");
			var_rDynamicUpdation = ""+variableScreen.getString("rDynamicUpdation");
			var_tVariableType = ""+variableScreen.getString("tVariableType");
			var_uNumberType = ""+variableScreen.getString("uNumberType");
			var_vEnumType = ""+variableScreen.getString("vEnumType");
			var_wWidth = ""+variableScreen.getString("wWidth");
			var_xEEPROM = ""+variableScreen.getString("xEEPROM");
			var_yHide = ""+variableScreen.getString("yHide");
			var_zProtectModeReadOnly = ""+variableScreen.getString("zProtectModeReadOnly");
			var_AScreenType32Bit = ""+variableScreen.getString("AScreenType32Bit");
			var_BConditionalHide = ""+variableScreen.getString("BConditionalHide");
			var_CVarDepUnit = ""+variableScreen.getString("CVarDepUnit");
			var_DDefaultRefAddr = ""+variableScreen.getString("DDefaultRefAddr");
			
			values.put("parentId", parentId);
	    	values.put(aName, var_aName);
		    values.put(bModBusAddr, var_bModBusAddr);
		    values.put(aSpanish, var_aSpanish);
		    values.put(bEnglish, var_bEnglish);
		    values.put(cGerman, var_cGerman);
		    values.put(cFixedUnit, var_cFixedUnit);
		    values.put(dVariableUnit, var_dVariableUnit);
		    values.put(eReferenceAddress, var_eReferenceAddress);
		    values.put(fMaxVal, var_fMaxVal);
		    values.put(gMinVal, var_gMinVal);
		    values.put(hDefault, var_hDefault);
		    values.put(iBase, var_iBase);
		    values.put(jProgModeRead, var_jProgModeRead);
		    values.put(kProgModeHide, var_kProgModeHide);
		    values.put(lShortMenuhide, var_lShortMenuhide);
		    values.put(mModifiable, var_mModifiable);
		    values.put(nOffToModifiy, var_nOffToModifiy);
		    values.put(oSign, var_oSign);
		    values.put(pMinRefAddr, var_pMinRefAddr);
		    values.put(qMaxRefAddr, var_qMaxRefAddr);
		    values.put(rDynamicUpdation, var_rDynamicUpdation);
		    values.put(tVariableType, var_tVariableType);
		    values.put(uNumberType, var_uNumberType);
		    values.put(vEnumType, var_vEnumType);
		    values.put(wWidth, var_wWidth);
		    values.put(xEEPROM, var_xEEPROM);
		    values.put(yHide, var_yHide);
		    values.put(zProtectModeReadOnly, var_zProtectModeReadOnly);
		    values.put(AScreenType32Bit, var_AScreenType32Bit);
		    values.put(BConditionalHide, var_BConditionalHide);
		    values.put(CVarDepUnit, var_CVarDepUnit);
		    values.put(DDefaultRefAddr, var_DDefaultRefAddr);
		 
		    // Inserting Row
		    db.insert(TABLE_BVARIABLESCREEN, null, values);
		    //db.close(); // Closing database connection
		    
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
	}
	
	public void add_dScreenListContent(int _root_id, String _aName, int _screenRef_id, String _aSpanish, String _bEnglish, String _cGerman, String _bProgModeHide, String _cShortMenuhide, String _eHide, String _gConditionHide)
	{
		System.out.println("SCREEEEEEEEEEEEEEEEEN list");
		getWritableDBConnection();
    	ContentValues values = new ContentValues();
    	
		//values.put(id, _id); // EnumItem count
		//values.put(root_id, _root_id);
	    values.put(aName, _aName);
	    values.put(screenRef_id, _screenRef_id);
	    values.put(aSpanish, _aSpanish);
	    values.put(bEnglish, _bEnglish);
	    values.put(cGerman, _cGerman);
	    values.put(bProgModeHide, _bProgModeHide);
	    values.put(cShortMenuhide, _cShortMenuhide);
	    values.put(eHide, _eHide);
	    values.put(gConditionHide, _gConditionHide);
	    System.out.println("SCREEEEEEEEEEEEEEEEEN list1111");
	    // Inserting Row
	    db.insert(TABLE_DSCREENLIST, null, values);
	    //db.close(); // Closing database connection
	}
	
	public void addEnumListContent(int _id, String _aName, String _cExEnum)
	{
		//db = getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put(id, _id); // EnumList count
	    values.put(aName, _aName); // Enum name
	    values.put(cExEnum, _cExEnum); // ENum ExEnum
	 
	    // Inserting Row
	    db.insert(TABLE_BENUMLIST, null, values);
	    //db.close(); // Closing database connection
	}
	
	public void addEnumItemContent(int _enumList_id, String _aValue, String _bSpanish, String _cEnglish, String _dGerman)
	{
		//db = getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put(id, _id); // EnumItem count
		values.put(EnumList_id, _enumList_id); // EnumList id
	    values.put(aValue, _aValue);
	    values.put(bSpanish, _bSpanish);
	    values.put(cEnglish, _cEnglish);
	    values.put(dGerman, _dGerman);
	 
	    // Inserting Row
	    db.insert(TABLE_BENUMITEM, null, values);
	    //db.close(); // Closing database connection
	}
	
	public String getEnumValue(int _addrvalue, String _enumType)
	{
		String resultvalue = "";
		String selectQuery = "SELECT cEnglish FROM " + TABLE_BENUMITEM + " WHERE " + EnumList_id + "= (SELECT id FROM " + TABLE_BENUMLIST + " WHERE " + aName + "='"+ _enumType +"') AND aValue= "+_addrvalue;
		
		//Log.e(LOG, selectQuery);
		 
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.getCount() == 0)
	    {
	    	resultvalue = "";
	    }else{
	    	cursor.moveToFirst();
	    	resultvalue = cursor.getString(cursor.getColumnIndex(cEnglish));
	    }
	    cursor.close();
	    
	    return resultvalue;
	}
	
	public int  getEnumCount()
	{
		int resultvalue = 0;
		String selectQuery = "SELECT COUNT(*)value FROM " + TABLE_BENUMLIST + "";
		
		//Log.e(LOG, selectQuery);
		 
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.getCount() == 0)
	    {
	    	resultvalue = 0;
	    }else{
	    	cursor.moveToFirst();
	    	resultvalue = cursor.getInt(cursor.getColumnIndex("value"));
	    }
	    cursor.close();
	    return resultvalue;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//System.out.println("DATABASE");
		//Table Creation for dScreenList
		String CREATE_DSCREENLIST_TABLE = "CREATE TABLE " + TABLE_DSCREENLIST + "("
                + root_id + " INTEGER PRIMARY KEY,"
                + aName + " TEXT,"
				+ screenRef_id + " INTEGER,"
                + bProgModeHide + " TEXT,"
                + cShortMenuhide + " TEXT,"
                + aSpanish + " TEXT,"
                + bEnglish + " TEXT,"
                + cGerman + " TEXT,"
                + eHide + " TEXT,"
                + gConditionHide + " TEXT" + ")";
		
        db.execSQL(CREATE_DSCREENLIST_TABLE);
        
        //Table Creation for bVariableScreen
  		String CREATE_BVARIABLESCREEN_TABLE = "CREATE TABLE " + TABLE_BVARIABLESCREEN + "("
                  + "parentId INTEGER,"
                  + aName + " TEXT,"
  				  + bModBusAddr + " INTEGER,"
                  + aSpanish + " TEXT,"
                  + bEnglish + " TEXT,"
                  + cGerman + " TEXT,"
                  + cFixedUnit + " TEXT,"
                  + dVariableUnit + " TEXT,"
                  + eReferenceAddress + " TEXT,"
                  + fMaxVal + " INTEGER,"
                  + gMinVal + " INTEGER,"
                  + hDefault + " INTEGER,"
                  + iBase + " TEXT,"
                  + jProgModeRead + " TEXT,"
                  + kProgModeHide + " TEXT,"
                  + lShortMenuhide + " TEXT,"
                  + mModifiable + " TEXT,"
                  + nOffToModifiy + " TEXT,"
                  + oSign + " TEXT,"
                  + pMinRefAddr + " TEXT,"
                  + qMaxRefAddr + " TEXT,"
                  + rDynamicUpdation + " TEXT,"
                  + tVariableType + " TEXT,"
                  + uNumberType + " TEXT,"
                  + vEnumType + " TEXT,"
                  + wWidth + " TEXT,"
                  + xEEPROM + " TEXT,"
                  + yHide + " TEXT,"
                  + zProtectModeReadOnly + " TEXT,"
                  + AScreenType32Bit + " TEXT,"
                  + BConditionalHide + " TEXT,"
                  + CVarDepUnit + " TEXT,"
                  + DDefaultRefAddr + " TEXT" + ")";
  		
          db.execSQL(CREATE_BVARIABLESCREEN_TABLE);
          
		//Table creation for bEnumList tag
		String CREATE_BENUMLIST_TABLE = "CREATE TABLE " + TABLE_BENUMLIST + "("
                + id + " INTEGER PRIMARY KEY," 
				+ aName + " TEXT,"
                + cExEnum + " TEXT" + ")";
		
        db.execSQL(CREATE_BENUMLIST_TABLE);
        
        //Table creation for bEnumList tag
  		String CREATE_BENUMITEM_TABLE = "CREATE TABLE " + TABLE_BENUMITEM + "("
                  + id + " INTEGER PRIMARY KEY,"
                  + EnumList_id + " INTEGER,"
  				  + aValue + " INTEGER,"
                  + bSpanish + " TEXT,"
                  + cEnglish + " TEXT,"
                  + dGerman + " TEXT" + ")";
  		
          db.execSQL(CREATE_BENUMITEM_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BENUMLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BENUMITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DSCREENLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BVARIABLESCREEN);
 
        // Create tables again
        onCreate(db);
	}

	
}
