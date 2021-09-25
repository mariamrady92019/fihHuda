package tool.mariam.fihhuda.quran.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import tool.mariam.fihhuda.Base.BaseActivity;
import tool.mariam.fihhuda.Base.Constants;
import tool.mariam.fihhuda.Base.ListeningService;
import tool.mariam.fihhuda.Base.NotificationCreator_Helper;
import tool.mariam.fihhuda.ListenSrvicesManager;
import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.quran.viewsModel.ListeningViewModel;

public class ListenDetailsActivity extends BaseActivity implements View.OnClickListener , SeekBar.OnSeekBarChangeListener {

    protected TextView surahName;
    protected TextView currentPosition;
    protected TextView total;
    protected ImageView rewBtn;
    public static ImageView playBtn;
    protected ImageView forwrdBtn;
    public static ProgressBar progressCircular;
    protected TextView qareeName;
   public  SeekBar seekBar;
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
    boolean firstTimeToDone;
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

       // getListeningData(readerId, soraRenamed);
      //  observeDataFromViewMOdel();
        isplayingimage=false;
        isPaused=false;
        firstTimeToDone = true;
          if(ListenSrvicesManager.getInstance()!=null||ListenSrvicesManager.getInstance().isPlaying()){

            stopService(new Intent(this,ListeningService.class));
             // ListenSrvicesManager.getInstance().stop();
             ListenSrvicesManager.getInstance().release();
             ListenSrvicesManager.setMediaPlayerNull();
              sTime=0;
              eTime=0;
          }
          ListeningViewModel.preparedDone.setValue("");
          ListeningViewModel.completionDone.setValue("");
       observePreparationDone();
       observeComplitionDone();
        //observeDestroyedDone();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rew_btn) {
            playBtn.setImageResource(R.drawable.play);
            progressCircular.setVisibility(View.VISIBLE);
           Constants.position--;
            String suraname = viewModel.getSurahNameByPosition(Constants.position);
            surahName.setText(suraname);
            NotificationCreator_Helper.updateNotification(suraname);
            Intent intent = new Intent(this, ListeningService.class);
           // intent.putExtra("url",audioUrl);
            //even if now i had name of sura so its not nessaccery to pass pos but after complition it will need it
            intent.putExtra("position",Constants.position);
            startService(intent); 
            //startNewSuraByButton(position);
        }

        else if (view.getId() == R.id.play_btn)   {

                  //مش شغال
                  if (isplayingimage==false) {
                      //play after pause
                      if(isPaused == true){
                           Constants.isPlaying=true;
                          ListenSrvicesManager.getInstance().seekTo(sTime);
                          ListenSrvicesManager.getInstance().start();
                          isPaused=false;
                          isplayingimage=true;
                          playBtn.setImageResource(R.drawable.puase);
                      }else{
                          //play
                          Constants.isPlaying=true;

                          progressCircular.setVisibility(View.VISIBLE);

                        NotificationCreator_Helper.createNotifications(ListenDetailsActivity.this,surahnameIntented,getPackageName());
                          Intent intent = new Intent(this, ListeningService.class);
                          //intent.putExtra("url",audioUrl);
                          intent.putExtra("position",Constants.position);
                          startService(intent);
                          isplayingimage = true;
                         // isPaused=false;
                          // progressCircular.setVisibility(View.VISIBLE);
                          // playBtn.setImageResource(R.drawable.puase);

                          //updatSeekBarTimer();

                      }}
                  //\puas
                  else if (isplayingimage==true){
                      Constants.isPlaying=false;

                      ListenSrvicesManager.getInstance().pause();
                      playBtn.setImageResource(R.drawable.play);
                      isplayingimage=false;
                      isPaused= true;
                  }

              }
        else if (view.getId() == R.id.forwrd_btn) {

            playBtn.setImageResource(R.drawable.play);
            progressCircular.setVisibility(View.VISIBLE);
            Constants.position++;
            String suraname = viewModel.getSurahNameByPosition(Constants.position);
            surahName.setText(suraname);
            NotificationCreator_Helper.updateNotification(suraname);
        //startNewSuraByButton(position);
            Intent intent = new Intent(this, ListeningService.class);
            // intent.putExtra("url",audioUrl);
            intent.putExtra("position",Constants.position);
            startService(intent);

        }

    }



    private void getDataIntented() {
        Constants.position = getIntent().getIntExtra("position", -1);
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
                     playBtn.setImageResource(R.drawable.puase);
                     progressCircular.setVisibility(View.GONE);
                     isplayingimage=true;


                     surahName.setText(viewModel.getSurahNameByPosition(Constants.position));


                    // viewModel.preparedDone.setValue("");
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
        playBtn.setImageResource(R.drawable.play);
        playBtn.setOnClickListener(ListenDetailsActivity.this);
      //  playBtn.setVisibility(View.INVISIBLE);
        forwrdBtn = (ImageView) findViewById(R.id.forwrd_btn);
        forwrdBtn.setOnClickListener(ListenDetailsActivity.this);
        progressCircular = (ProgressBar) findViewById(R.id.progress_circular);
        progressCircular.setVisibility(View.GONE);
        qareeName = (TextView) findViewById(R.id.qareeName);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

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
        total.setText((getTimeString(eTime*1000)+""));
        currentPosition.setText(getTimeString(sTime*1000)+"");
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
                    currentPosition.setText(getTimeString(sTime)+"/");
                    // total.setText(eTime/1000);
                    hdlr.postDelayed(this, 100);


            }
        };
           hdlr.postDelayed(updateSongTime, 100);
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);
        if(hours==0){
            buf.append(String.format("%02d", minutes))
                    .append(":")
                    .append(String.format("%02d", seconds));

        }else {
            buf
                    .append(String.format("%02d", hours))
                    .append(":")
                    .append(String.format("%02d", minutes))
                    .append(":")
                    .append(String.format("%02d", seconds));
        }
        return buf.toString();
    }

   //observe completion donr
    private void observeComplitionDone() {
       ListeningViewModel.completionDone.observe(ListenDetailsActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s.contains("done")){
                    playBtn.setImageResource(R.drawable.play);
                    progressCircular.setVisibility(View.VISIBLE);
                    //get data from shared preference
                   // String name=  SharedPereffernceManager.getSharedInstance(ListenDetailsActivity.this).getString("suraNmae","");
                    //  Log.e("com",name+"nn");
                    //position=position+1;
                    String name=viewModel.getSurahNameByPosition(Constants.position);
                    surahName.setText(name);
                    NotificationCreator_Helper.updateNotification(name);
                   // position= SharedPereffernceManager.getSharedInstance(ListenDetailsActivity.this).getInt("position",1);


                }


           /*     if (isplayingimage==true){
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


            }*/
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




    @Override
    protected void onResume() {
        super.onResume();
       // updatSeekBarTimer();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
            ListenSrvicesManager.getInstance().seekTo(progress);
            seekBar.setProgress(progress);}

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}


