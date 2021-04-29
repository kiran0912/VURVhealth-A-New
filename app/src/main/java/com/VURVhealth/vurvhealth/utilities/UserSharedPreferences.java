package com.VURVhealth.vurvhealth.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSharedPreferences {
    private static UserSharedPreferences userSharedPreferences;
    private SharedPreferences sharedPreferences;

    public static UserSharedPreferences getInstance(Context context) {
        if (userSharedPreferences == null) {
            userSharedPreferences = new UserSharedPreferences(context);
        }
        return userSharedPreferences;
    }

    private UserSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences("USER_PREFERENCE", 0);
    }

    public void SaveData(String key, String value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void SaveBooleanData(String key, boolean value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getData(String key) {
        if (this.sharedPreferences != null) {
            return this.sharedPreferences.getString(key, "");
        }
        return "";
    }

    public Boolean getBooleanData(String key) {
        if (this.sharedPreferences != null) {
            return this.sharedPreferences.getBoolean(key, true);
        }
        return true;
    }
}
