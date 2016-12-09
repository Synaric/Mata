package com.synaric.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressWarnings("unused")
public class SPUtils {

	private static final String SHARED_PATH = "CNCROWD_SHARE";
	private static SharedPreferences sharedPreferences;

	public static SharedPreferences getDefaultSharedPreferences(Context context) {
		if(sharedPreferences == null){
			sharedPreferences = context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
		}
		return sharedPreferences;
	}

	@SuppressWarnings("deprecation")
	public static SharedPreferences getMultiSharedPreferences(Context context) {
		return context.getSharedPreferences(SHARED_PATH, Context.MODE_MULTI_PROCESS);
	}

    public static SharedPreferences getVersionSharedPreferences(Context context, int mode) {
        return context.getSharedPreferences(SystemUtils.getVersionName(context), mode);
    }
	
	public static void putInt(Context context, String key, int value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putInt(key, value);
		edit.apply();
	}

	public static int getInt(Context context, String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(key, 0);
	}
	
	public static void putString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.apply();
	}

	public static String getString(Context context, String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		return sharedPreferences.getString(key,null);
	}
	
	public static void putBoolean(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.apply();
	}

	public static boolean getBoolean(Context context, String key, boolean defValue) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key,defValue);
	}

	public static void putLong(Context context, String key, long value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putLong(key, value);
		edit.apply();
	}

	public static long getLong(Context context, String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		return sharedPreferences.getLong(key, 0);
	}
	
	public static void remove(Context context, String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.remove(key);
		edit.apply();
	}

}
