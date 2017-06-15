package com.mai.xmai_fast_lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * created by mai 2016/05/16
 */
public class SharedPreferencesHelper {
    private static final String SHARED_PATH = "mai_shared";

    private Map<String, Object> cache = new HashMap<>();

    private static SharedPreferencesHelper instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null && context != null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    private SharedPreferencesHelper(Context context) {
        sp = context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public long getLongValue(String key) {
        if (key != null && !key.equals("")) {
            if (cache.containsKey(key))
                return (long) cache.get(key);
            long value = sp.getLong(key, 0);
            cache.put(key, value);
            return value;
        }
        return 0;
    }

    public String getStringValue(String key) {
        if (key != null && !key.equals("")) {

            if (cache.containsKey(key))
                return (String) cache.get(key);
            return sp.getString(key, null);
        }
        return null;
    }

    public int getIntValue(String key) {
        if (key != null && !key.equals("")) {
            if (cache.containsKey(key))
                return (int) cache.get(key);
            int value = sp.getInt(key, 0);
            cache.put(key, value);
            return value;
        }
        return 0;
    }

    public int getIntValueByDefault(String key) {
        if (key != null && !key.equals("")) {
            if (cache.containsKey(key))
                return (int) cache.get(key);
            int value = sp.getInt(key, 0);
            cache.put(key, value);
            return value;
        }
        return 0;
    }

    public boolean getBooleanValue(String key) {
        if (key != null && !key.equals("")) {
            if (cache.containsKey(key))
                return (boolean) cache.get(key);
            boolean value = sp.getBoolean(key, false);
            cache.put(key, value);
            return value;
        }
        return true;
    }

    public float getFloatValue(String key) {
        if (key != null && !key.equals("")) {
            if (cache.containsKey(key))
                return (float) cache.get(key);
            float value = sp.getFloat(key, 0);
            cache.put(key, value);
            return value;
        }
        return 0;
    }

    public void putStringValue(String key, String value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putString(key, value);
            editor.commit();
            cache.put(key, value);
        }
    }

    public void putIntValue(String key, int value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putInt(key, value);
            editor.commit();
            cache.put(key, value);
        }
    }

    public void putBooleanValue(String key, boolean value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putBoolean(key, value);
            editor.commit();
            cache.put(key, value);
        }
    }

    public void putLongValue(String key, long value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putLong(key, value);
            editor.commit();
            cache.put(key, value);
        }
    }

    public void putFloatValue(String key, Float value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putFloat(key, value);
            editor.commit();

            cache.put(key, value);
        }
    }

}
