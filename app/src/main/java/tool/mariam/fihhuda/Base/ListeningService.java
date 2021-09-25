package tool.mariam.fihhuda.Base;

import static tool.mariam.fihhuda.quran.views.ListenDetailsActivity.progressCircular;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.io.IOException;

import tool.mariam.fihhuda.ListenServicesManager;
import tool.mariam.fihhuda.quran.viewsModel.ListeningViewModel;


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

        listeningMediaPlayer = ListenServicesManager.getInstance();
        listeningMediaPlayer.setOnCompletionListener(this);
        listeningMediaPlayer.setOnPreparedListener(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("log", "onCreate: destroyed");
       progressCircular.setVisibility(View.GONE);

      // listeningMediaPlayer.stop();
    //  ListenDetailsActivity.playBtn.setImageResource(R.drawable.play);

        listeningMediaPlayer.release();
       // ListenSrvicesManager.setMediaPlayerNull();
       //ListeningViewModel.destroyedDone.setValue("destroyed done");

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(listeningMediaPlayer.isPlaying()){
           listeningMediaPlayer.stop();
        }
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
      //  if(!listeningMediaPlayer.isPlaying()&&listeningMediaPlayer!=null) {
         //   progressCircular.setVisibility(View.GONE);
           // ListenDetailsActivity.playBtn.setImageResource(R.drawable.puase);
            listeningMediaPlayer.start();
            //createNotifications();
            listeningMediaPlayer.setOnCompletionListener(this);
            ListeningViewModel.preparedDone.setValue("prepared done");

            // updatSeekBarTimer();
       //
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //worked
        Log.e("log", "complete");

        ListeningViewModel.completionDone.postValue("completion done");

        Log.e("log", position+"");
        Constants.position++;
        if(Constants.position>=1&&position<115){
        startPlayingAudio(Constants.position,false);}

    }


    public void startPlayingAudio(int position , boolean isInMainThread) {
        Log.e("log", "start after complition");
        //reset
        ListenServicesManager.getInstance().release();
        ListenServicesManager.setMediaPlayerNull();


        String sorahName = listeningViewModel.getSurahNameByPosition(position);
        String[] splitStr = sorahName.split("\\s+");
        String soraRenamed = reName(splitStr[1]);
        Log.e("log", soraRenamed);


        // progressCircular.setVisibility(View.VISIBLE);
        //get url in view model by retrofit
        listeningViewModel.getListeningDataBId_SuraName(9,soraRenamed);

       // listeningMediaPlayer.setOnPreparedListener(this);
           updateSharedPreferences(sorahName,position);
        observeUrlFromListeningViewModel(isInMainThread);



    }

    private void updateSharedPreferences(String sorahName, int position) {
        SharedPereffernceManager.getSharedInstance(this).edit().putString("suraName", sorahName);
        SharedPereffernceManager.getSharedInstance(this).edit().putInt("position",position);
        SharedPereffernceManager.getSharedInstance(this).edit().apply();


    }

    private void observeUrlFromListeningViewModel(final boolean isInmainThred) {
        listeningViewModel.uriLink.observe(this, new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChanged(String s) {
                // ListenSrvicesManager.getInstance().release();
                // ListenSrvicesManager.setMediaPlayerNull();
                if(s.contains("htt")){
                    listeningMediaPlayer = ListenServicesManager.getInstance();

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
                            ListeningViewModel.preparedDone.postValue("done");
                            listeningMediaPlayer.prepare();
                             listeningMediaPlayer.start();
                        }
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

}
