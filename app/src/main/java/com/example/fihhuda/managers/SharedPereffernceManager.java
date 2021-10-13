package com.example.fihhuda.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPereffernceManager {

 private static SharedPreferences  sharedPreferences ;


    public static SharedPreferences getSharedInstance(Context context){
        if(sharedPreferences==null){
            sharedPreferences= context.getSharedPreferences("Pref",Context.MODE_MULTI_PROCESS);
        }
        return sharedPreferences;
    }

}
