package com.muibsols.iptracker.Sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
//
//import com.google.gson.Gson;
//import com.secapp.chattalk.model.User;

public class SharedPrefs {

    public static final String TAG = "SHAREDPREF";

    private static SharedPrefs sharedPrefs;
    protected Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public SharedPrefs(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static synchronized SharedPrefs getInstance(Context context) {

        if (sharedPrefs == null) {
            sharedPrefs = new SharedPrefs(context.getApplicationContext());
        }
        return sharedPrefs;
    }

    public void clearPrefrences() {
        mSharedPreferencesEditor.clear().commit();
    }

    public void loginUser(boolean value) {
        Log.i(TAG, "loginUser: " + value);
        mSharedPreferencesEditor.putBoolean("loginAsUser", value);
        mSharedPreferencesEditor.commit();
    }

//    public void saveUserData(User user) {
//        Log.i(TAG, "saveUserData: " + user.getEmail());
//        Gson gson = new Gson();
//        String json = gson.toJson(user);
//        mSharedPreferencesEditor.putString("User", json);
//        mSharedPreferencesEditor.commit();
//    }
//
//    public User getUser() {
//        if (isLoggedIn()) {
//            Gson gson = new Gson();
//            String json = mSharedPreferences.getString("User", "");
//            return gson.fromJson(json, User.class);
//        } else {
//            return null;
//        }
//
//    }

    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean("loginAsUser", false);
    }



    public void basicDataUploaded(boolean value) {
        mSharedPreferencesEditor.putBoolean("dataUploaded", value);
        mSharedPreferencesEditor.commit();
    }

    public void setUniqueId(String value) {
        mSharedPreferencesEditor.putString("uniqueId", value);
        mSharedPreferencesEditor.commit();
    }

    public String getUniqueId() {
        return mSharedPreferences.getString("uniqueId", "notFound");
    }


    public void contactUpload(boolean value) {
        mSharedPreferencesEditor.putBoolean("contactUploaded", value);
        mSharedPreferencesEditor.commit();
    }

    public boolean isContactUploaded() {
        return mSharedPreferences.getBoolean("contactUploaded", false);
    }

}
