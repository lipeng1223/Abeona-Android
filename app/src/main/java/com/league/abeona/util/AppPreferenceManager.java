package com.league.abeona.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AppPreferenceManager {

	private static SharedPreferences preferences;

	static Context mContext;

	public static void initializePreferenceManager(Context context) {

		mContext=context;
		preferences=context.getSharedPreferences("MyLockScreen", 0);
	}
	
	public static void clearAll()
	{
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}
	
	public static boolean getBoolean(String key, boolean defaultValue) {
		return preferences.getBoolean(key, defaultValue);
	}
	public static void setBoolean(String key, boolean value) {
		if(value == true)
		{
			Log.e(key, "setted");
		}
		else
		{
			Log.e(key, "not setted");
		}

		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static String getString(String key, String defaultValue) {
		return preferences.getString(key, defaultValue);
	}
	public static void setString(String key, String value) {

		Log.e(key, value);

		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static int getInt(String key, int defaultValue) {
		return preferences.getInt(key, defaultValue);
	}

	public static void setInt(String key, int value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void setArray(String key, ArrayList<String> mArray)
	{
		JSONArray array=new JSONArray(mArray);
		String json=array.toString();

		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, json);
		editor.commit();


	}
	public static ArrayList<String> getArray(String key, String tag)
	{
		ArrayList<String> array=new ArrayList<String>();

		SharedPreferences.Editor editor = preferences.edit();
		String json=preferences.getString(key, "");

		try {

			JSONArray jsonArray=new JSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++)
			{
				array.add(jsonArray.getString(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
}
