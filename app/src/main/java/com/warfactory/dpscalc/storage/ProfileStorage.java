package com.warfactory.dpscalc.storage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.warfactory.dpscalc.model.CharacterProfile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileStorage {

    private static final String STORE_KEY = "profileList";

    public static void storeProfileList(List<CharacterProfile> profileList, Activity activity) {
        Gson gson = new Gson();
        String json = gson.toJson(profileList);
        // save serialized data to shared pref
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString(STORE_KEY, json);
        prefsEditor.commit();
    }

    public static List<CharacterProfile> retrieveProfileList(Activity activity) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(STORE_KEY, "");
        List<CharacterProfile> profiles = null;

        Type collectionType = new TypeToken<List<CharacterProfile>>() {
        }.getType();

        try {
            profiles = gson.fromJson(json, collectionType);

        } catch (Exception e) {

        }
        if (profiles == null)
            profiles = new ArrayList<>();
        return profiles;
    }
}
