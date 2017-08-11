package io.github.andhikayuana.mqttdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import io.github.andhikayuana.mqttdemo.App;
import io.github.andhikayuana.mqttdemo.BuildConfig;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/11/17
 */

public class SharedPref {

    public static SharedPreferences pref() {
        return App.getContext()
                .getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor edit() {
        return pref().edit();
    }

    public static void putString(String key, String val) {
        edit().putString(key, val).apply();
    }

    public static String getString(String key) {
        return pref().getString(key, null);
    }

    public static void putInt(String key, int val) {
        edit().putInt(key, val).apply();
    }

    public static int getInt(String key) {
        return pref().getInt(key, 0);
    }

    public static void putLong(String key, long val) {
        edit().putLong(key, val).apply();
    }

    public static long getLong(String key) {
        return pref().getLong(key, 0);
    }

    public static void putBoolean(String key, boolean val) {
        edit().putBoolean(key, val).apply();
    }

    public static boolean getBoolean(String key) {
        return pref().getBoolean(key, false);
    }

    public static void putFloat(String key, float val) {
        edit().putFloat(key, val).apply();
    }

    public static float getFloat(String key) {
        return pref().getFloat(key, 0);
    }
}
