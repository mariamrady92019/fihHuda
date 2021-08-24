package com.example.fihhuda.quran.views;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fihhuda.Base.ApplicationClass;
import com.example.fihhuda.Base.BaseActivity;
import com.example.fihhuda.Base.ListeningService;
import com.example.fihhuda.ListenSrvicesManager;
import com.example.fihhuda.R;
import com.example.fihhuda.quran.viewsModel.ListeningViewModel;

public class ListenDetailsActivity extends BaseActivity implements View.OnClickListener  {

    protected TextView surahName;
    protected TextView currentPosition;
    protected TextView total;
    protected ImageView rewBtn;
    public static ImageView playBtn;
    protected ImageView forwrdBtn;
    public static ProgressBar progressCircular;
    protected TextView qareeName;
   public  SeekBar seekBar;
    private int position;
    private String surahnameIntented;
    int readerId;
    private ListeningViewModel viewModel;
    public  String audioUrl;
    private String failedMessage;
    boolean isPaused = false;
    boolean isplayingimage = false;
    private int eTime;
    private int oTime;
    private int sTime;
    private Runnable updateSongTime;
    private Handler hdlr=new Handler();
    private boolean start= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_listen_details);
        viewModel = new ViewModelProvider(this).get(ListeningViewModel.class);
           viewModel.context=ListenDetailsActivity.this;
           viewModel.uriLink.setValue("");
        initView();
        getDataIntented();
        surahName.setText(surahnameIntented);
        String[] splitStr = surahnameIntented.split("\\s+");
        String soraRenamed = reName(splitStr[1]);
        Log.e("name", soraRenamed);

        Log.e("splitted", splitStr[1]);

        getListeningData(readerId, soraRenamed);
        observeDataFromViewMOdel();
        isplayingimage=false;
          if(ListenSrvicesManager.getInstance()!=null||ListenSrvicesManager.getInstance().isPlaying()){

            stopService(new Intent(this,ListeningService.class));
             // ListenSrvicesManager.getInstance().stop();
             ListenSrvicesManager.getInstance().release();
              ListenSrvicesManager.mediaPlayer=null;

              sTime=0;
              eTime=0;
          }
        observePreparationDone();
       // observeComplitionDone();
        //observeDestroyedDone();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rew_btn) {
            position = position - 1;
            startNewSuraByButton(position);
        }
        else if (view.getId() == R.id.play_btn) {

            //مش شغال
            if (isplayingimage==false) {
                //play after pause
                       if(isPaused == true){

                           ListenSrvicesManager.getInstance().seekTo(sTime);
                           ListenSrvicesManager.getInstance().start();
                           isPaused=false;
                           isplayingimage=true;
                           playBtn.setImageResource(R.drawable.puase);
                       }else{
                 //play
                           createNotifications();
                  Intent intent = new Intent(this, ListeningService.class);
                  intent.putExtra("url",audioUrl);
                           intent.putExtra("position",position);
                startService(intent);
                isplayingimage = true;
               // progressCircular.setVisibility(View.VISIBLE);
                        //   playBtn.setImageResource(R.drawable.puase);

                           //updatSeekBarTimer();

            }}
            //\puas
            else if (isplayingimage==true){
                ListenSrvicesManager.getInstance().pause();
                playBtn.setImageResource(R.drawable.play);
                isplayingimage=false;
                isPaused= true;
            }



        } else if (view.getId() == R.id.forwrd_btn) {
            position++;

        startNewSuraByButton(position);}

    }



    private void getDataIntented() {
        position = getIntent().getIntExtra("position", -1);
        surahnameIntented = getIntent().getStringExtra("name");
        readerId = getIntent().getIntExtra("shekh_id", 1);
        qareeName.setText(QuranFragment.shikh_name);


    }


    //call function connected to api from view model .. in view model the function called from rebo
    private void getListeningData(int id, String surahName) {
        viewModel.getListeningDataBId_SuraName(id, surahName);

    }


  public  void  observePreparationDone(){
       viewModel.preparedDone.observe(ListenDetailsActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.contains("done")){

                    updatSeekBarTimer();
                    //in update fun
                   // progressCircular.setVisibility(View.GONE);
                   // playBtn.setImageResource(R.drawable.puase);


                }
            }
        });
    }

    private void initView() {
        surahName = (TextView) findViewById(R.id.surah_name);
        currentPosition = (TextView) findViewById(R.id.current_position);
        total = (TextView) findViewById(R.id.total);
        rewBtn = (ImageView) findViewById(R.id.rew_btn);
        rewBtn.setOnClickListener(ListenDetailsActivity.this);
        playBtn = (ImageView) findViewById(R.id.play_btn);
       // playBtn.setImageResource(R.drawable.play);
        playBtn.setOnClickListener(ListenDetailsActivity.this);
        playBtn.setVisibility(View.INVISIBLE);
        forwrdBtn = (ImageView) findViewById(R.id.forwrd_btn);
        forwrdBtn.setOnClickListener(ListenDetailsActivity.this);
        progressCircular = (ProgressBar) findViewById(R.id.progress_circular);
        qareeName = (TextView) findViewById(R.id.qareeName);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }


    int num = 0;
    boolean founded = false;

    //observe url link from view model
    private void observeDataFromViewMOdel() {
        Log.e("log", "observeDataFromViewMOdel");

        viewModel.uriLink.observe(ListenDetailsActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("point", "start");
                //get uri link and update mediaplaayer
                if (!s.contains("http")) {//num=1
                    audioUrl ="not found";
                    num++;
                    // Toast.makeText(SuraDetailsActivity.this,"first", Toast.LENGTH_LONG).show();


                }

                if (s.contains("http")) {//1  notfounded = false;
                    if (num == 1)
                        num = 0;
                    founded = true;

                    Log.e("point", "if contain");
                    audioUrl = s;
                    progressCircular.setVisibility(View.GONE);
                    playBtn.setVisibility(View.VISIBLE);
                    //seekBar.setVisibility(View.VISIBLE);
                    Toast.makeText(ListenDetailsActivity.this, audioUrl, Toast.LENGTH_LONG).show();

                    if(start==true){

                                progressCircular.setVisibility(View.GONE);
                                Intent intent = new Intent(ListenDetailsActivity.this,ListeningService.class);
                                intent.putExtra("url", audioUrl);
                                Log.e("log", "start service");

                                startService(intent);


                    }

                }


            }


        });


        viewModel.failedMassage.observe(ListenDetailsActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // progressBarCyclic.setVisibility(View.GONE);
                failedMessage = s;
                // Toast.makeText(SuraDetailsActivity.this,failedMessage+"", Toast.LENGTH_LONG).show();


            }
        });


    }

    //seekbar horozintal line timer
    public void updatSeekBarTimer(//final MediaPlayer mediaPlayer
                                 ) {
      // eTime = ListenSrvicesManager.getInstance().getDuration();
      //  progressCircular.setVisibility(View.GONE);
       // playBtn.setImageResource(R.drawable.puase);

        eTime=(ListenSrvicesManager.getInstance().getDuration()/1000);
        sTime =ListenSrvicesManager.getInstance().getCurrentPosition()/1000;
        total.setText((eTime+""));
        currentPosition.setText(sTime+"");
        if (oTime == 0) {
         //   seekBar.setMax(eTime);
            oTime = 1;
        }
        seekBar.setMax(eTime);
          seekBar.setProgress(sTime/1000);
        updateSongTime = new Runnable() {
            @Override
            public void run() {
                sTime = ListenSrvicesManager.getInstance().getCurrentPosition();
                seekBar.setProgress((sTime/1000));
                currentPosition.setText(sTime/1000+"/");

                hdlr.postDelayed(this, 100);
            }
        };
           hdlr.postDelayed(updateSongTime, 100);
    }


  /*  //observe destroyed donr
    private void observeDestroyedDone() {
        viewModel.destroyedDone.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                 playBtn.setImageResource(R.drawable.play);
                 progressCircular.setVisibility(View.GONE);
            }
        });
    }*/


   //observe completion donr
    private void observeComplitionDone() {
        Log.e("log", "onCreate: completion done");

        viewModel.completionDone.observe(ListenDetailsActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (isplayingimage==true){
                    playBtn.setImageResource(R.drawable.play);
                sTime = 0;
                currentPosition.setText(sTime + "");
                total.setText("0");
                ListenSrvicesManager.setMediaPlayerNull();
                Toast.makeText(ListenDetailsActivity.this, "done", Toast.LENGTH_LONG).show();
                Log.e("log", "onCreate: completion done");

                position++;
                if (position == 115)
                    return;
                startAfterComplition(position);


            }
            }
        });
    }

     public void startAfterComplition(int position){
         Log.e("log", "start after complition");

         String sorahName= viewModel.getSurahNameByPosition(position);
         String[] splitStr = sorahName.split("\\s+");
         String soraRenamed = reName(splitStr[1]);
         audioUrl= null;
         surahName.setText(sorahName);
         progressCircular.setVisibility(View.VISIBLE);
         viewModel.getListeningDataBId_SuraName(readerId,soraRenamed);
         start= true;
         isplayingimage=true;
     }

   public  void startNewSuraByButton(int position){
        if(position>1&&position<114){
            stopService(new Intent(this,ListeningService.class));
       // ListenSrvicesManager.getInstance().stop();
       ListenSrvicesManager.getInstance().release();
       ListenSrvicesManager.mediaPlayer=null;
       startAfterComplition(position);}else{
            return;
        }
   }

    @Override
    protected void onStop() {
        super.onStop();
        observeComplitionDone();
    }
    public  void createNotifications(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ApplicationClass.CHANNEL_ID)
                .setSmallIcon(R.drawable.quran)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());

    }
}

