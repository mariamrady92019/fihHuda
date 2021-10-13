package com.example.fihhuda.managers;

import android.media.MediaPlayer;

import com.example.fihhuda.Base.ListeningService;
import com.example.fihhuda.quran.views.ListenDetailsActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListenSrvicesManager {
    public  static MediaPlayer mediaPlayer;

   public static MediaPlayer getInstance(){

        if (mediaPlayer==null){
       mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }


    public static void setMediaPlayerNull(){

      mediaPlayer=null ;
    }
}
