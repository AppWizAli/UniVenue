package com.enfotrix.unibooking.Ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SharedPrefManager {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPref = context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void saveUser(ModelUser user) {
        editor.putString("user", new Gson().toJson(user));
        editor.commit();
    }


    public ModelUser getUser() {
        String json = sharedPref.getString("user", "") != null ? sharedPref.getString("user", "") : "";
        if (json.isEmpty()) {
            return new ModelUser();
        }

        try {
            return new Gson().fromJson(json, ModelUser.class);
        } catch (JsonSyntaxException e) {
            return new ModelUser();

        }
    }


    public void putDate(String date) {
        editor.putString("date", date);
        editor.commit();
    }
    public void putTime(String time) {
        editor.putString("time", time);
        editor.commit();
    }

    public String getDate() {
        return sharedPref.getString("date", "");
    }
    public String getTime() {
        return sharedPref.getString("time", "");
    }





    public void putReason(String reason) {
        editor.putString("reason", reason);
        editor.commit();
    }
    public void putParticipant(String part) {
        editor.putString("participant", part);
        editor.commit();
    }

    public String getReason() {
        return sharedPref.getString("reason", "");
    }
    public String getParticipent() {
        return sharedPref.getString("participant", "");
    }












}
