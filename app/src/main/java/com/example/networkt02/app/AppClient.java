package com.example.networkt02.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.networkt02.beans.FaKuanInfoBean;
import com.example.networkt02.beans.FindInfoBean;

import java.util.ArrayList;
import java.util.List;

public class AppClient extends Application {
    public static RequestQueue mQueue;
    public static SharedPreferences mSharePreferences;
    public static List<FindInfoBean> lists;//罚款
    public static List<List<FaKuanInfoBean>> listInfos;//罚款

    @Override
    public void onCreate() {
        super.onCreate();
        mQueue = Volley.newRequestQueue(this);
        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(this);
        AppClient.lists = new ArrayList<>();
        AppClient.listInfos = new ArrayList<>();
    }

    public static RequestQueue getRequestmQueue() {
        return mQueue;
    }

    public static void setString(String key, String value) {

        mSharePreferences.edit().putString(key, value).commit();

    }

    public static String getString(String key) {
        return mSharePreferences.getString(key, "");
    }


    public static void setBoolean(String key, boolean value) {
        mSharePreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return mSharePreferences.getBoolean(key, false);
    }

    public static void setInt(String key, int value) {
        mSharePreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return mSharePreferences.getInt(key, 0);
    }


}
