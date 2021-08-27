package com.example.fihhuda.Base;

import android.content.Intent;
import android.os.IBinder;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.fihhuda.ListenSrvicesManager;
import com.example.fihhuda.R;
import com.example.fihhuda.quran.fullQuranReadingModels.FullQuran;
import com.example.fihhuda.quran.views.ListenDetailsActivity;
import com.example.fihhuda.quran.viewsModel.ListeningViewModel;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import static com.example.fihhuda.quran.views.ListenDetailsActivity.progressCircular;


public class ListeningService extends LifecycleService implements MediaPlayer.OnPreparedListener , MediaPlayer.OnCompletionListener {

    public static MediaPlayer listeningMediaPlayer;
   ListeningViewModel  listeningViewModel = new ListeningViewModel("");
    MediaPlayer.OnCompletionListener onCompletionListener = this;
    MediaPlayer.OnPreparedListener onPreparedListener = this;
    public static MutableLiveData<String> message;
    int position ;
    boolean startForFirstTime;

    public ListeningService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        super.onBind(intent);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("log", "onCreate: created");

       // listeningViewModel = new ViewModelProvider(this).get(ListeningViewModel.class);
       listeningViewModel.context=this;

        listeningMediaPlayer= ListenSrvicesManager.getInstance();
        listeningMediaPlayer.setOnCompletionListener(this);
        listeningMediaPlayer.setOnPreparedListener(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("log", "onCreate: destroyed");
       progressCircular.setVisibility(View.GONE);

      // listeningMediaPlayer.stop();
      ListenDetailsActivity.playBtn.setImageResource(R.drawable.play);

        listeningMediaPlayer.release();
        ListenSrvicesManager.setMediaPlayerNull();
       //ListeningViewModel.destroyedDone.setValue("destroyed done");

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        position = intent.getIntExtra("position",0);
        //startForFirstTime = intent.getBooleanExtra("startForFirstTime",true);
        startPlayingAudio(position,true);
        /*
         //!listeningMediaPlayer.isPlaying()&&
        // if(ListenSrvicesManager.getInstance()==null) {
        String message = intent.getExtras().getString("url");
       position = intent.getIntExtra("position",0);
        Log.e("log", position+"");

        listeningMediaPlayer = ListenSrvicesManager.getInstance();
        listeningMediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        try {
            listeningMediaPlayer.setDataSource(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listeningMediaPlayer.setOnPreparedListener(this);

        progressCircular.setVisibility(View.VISIBLE);

        listeningMediaPlayer.prepareAsync();
        Log.e("log", "onCreate: praper thread");
        */


        return START_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(!listeningMediaPlayer.isPlaying()&&listeningMediaPlayer!=null) {
         //   progressCircular.setVisibility(View.GONE);
           // ListenDetailsActivity.playBtn.setImageResource(R.drawable.puase);
            listeningMediaPlayer.start();
            //createNotifications();
            listeningMediaPlayer.setOnCompletionListener(this);
            ListeningViewModel.preparedDone.setValue("prepared done");

            // updatSeekBarTimer();
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //worked
        Log.e("log", "complete");

        ListeningViewModel.completionDone.setValue("completion done");

        Log.e("log", position+"");
        position= position+1;
        if(position>1&&position<115){
        startPlayingAudio(position,false);}

    }


    public void startPlayingAudio(int position , boolean isInMainThread){
        Log.e("log", "start after complition");
        //reset
          ListenSrvicesManager.getInstance().release();
          ListenSrvicesManager.setMediaPlayerNull();


        String sorahName=getSurahNameByPosition(position);
        String[] splitStr = sorahName.split("\\s+");
        String soraRenamed = reName(splitStr[1]);
        Log.e("log", soraRenamed);


       // progressCircular.setVisibility(View.VISIBLE);
       //get url in view model by retrofit
        listeningViewModel.getListeningDataBId_SuraName(9,soraRenamed);

       // listeningMediaPlayer.setOnPreparedListener(this);

        observeUrlFromListeningViewModel(isInMainThread);



    }

    private void observeUrlFromListeningViewModel(final boolean isInmainThred) {
        listeningViewModel.uriLink.observe(this, new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChanged(String s) {
                // ListenSrvicesManager.getInstance().release();
                // ListenSrvicesManager.setMediaPlayerNull();
                if(s.contains("htt")){
                    listeningMediaPlayer = ListenSrvicesManager.getInstance();

                    listeningMediaPlayer.setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build());
                    Log.e("log", s+"link");

                    try {
                        listeningMediaPlayer.reset();
                        listeningMediaPlayer.setDataSource(s);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                  //  progressCircular.setVisibility(View.VISIBLE);

                    try {
                        if(isInmainThred==false){
                        listeningMediaPlayer.prepare();
                        listeningMediaPlayer.start();}
                        else{
                            listeningMediaPlayer.prepareAsync();
                            listeningMediaPlayer.setOnPreparedListener(onPreparedListener);
                        }
                        listeningMediaPlayer.setOnCompletionListener(onCompletionListener);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // listeningMediaPlayer.start();

                }


            }
        });
    }
/*
    public void onCompletion(MediaPlayer mp) {
        ListeningViewModel.completionDone.setValue("completion done");
    }*/

    public String reName(String name){
        String rename = name;
        if(name.equals("هود")){
            rename="هٌود";
        }else if (name.equals("الشرح")){
            rename="الإنشراح";
        }else if(name.equals("العلق")){
            rename="العًلق";
        }else if(name.equals("ابراهيم")){
            rename="إبراهيم";
        }else if(name.equals("النبإ")){
            rename="النبأ";
        }else if(name.equals("الانسان")){
            rename="الإنسان";
        }
        return rename;
    }
    public String getSurahNameByPosition(int position){

        InputStream fileIn = null;
        String surahName= null;
        try {
            fileIn = this.getAssets().open("quran.json");
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            Reader reader = new InputStreamReader(bufferedIn, Charset.forName("UTF-8")) ;
            FullQuran full =new Gson().fromJson(reader, FullQuran.class);
            surahName = full.getData().getSurahs().get(position).getName();
        } catch (IOException e) {
        }

        return surahName;
    }

}
