package tool.mariam.fihhuda.quran.views;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tool.mariam.fihhuda.Base.BaseActivity;
import tool.mariam.fihhuda.Helper;
import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.quran.adapters.SurahDetailsAdapter;
import tool.mariam.fihhuda.quran.fullQuranReadingModels.Ayah;
import tool.mariam.fihhuda.quran.viewsModel.ListeningViewModel;

public class SuraDetailsActivity extends BaseActivity implements View.OnClickListener {

    protected TextView pageNumberTextView;
    protected TextView guzaNumberTextView;
    protected RecyclerView ayasWithPagesRecycler;
    protected SeekBar seekBar;
    protected ImageView playSound;
    protected ProgressBar progressBarCyclic;
    protected TextView textView;
    SurahDetailsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ListeningViewModel viewModel;
    int position;
    int readerId;
    String surahName;
    //List<List<Ayah>> surahAyat = new ArrayList<>();
    List<Ayah>surahAyat = new ArrayList<>();

    int pages = 0;
    String failedMessage ;
    String audioUrl;
    private static int oTime = 0, sTime = 0, eTime = 0;
    MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler hdlr = new Handler();
    Runnable updateSongTime;
    int buttonPlayed = 0;
    Helper   helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_sura_details);
        viewModel = new ViewModelProvider(this).get(ListeningViewModel.class);


        initView();
        getDataIntented();

        getDataModeled();
        initRecycler(surahAyat);

        String[] splitStr = surahName.split("\\s+");
        String soraRenamed=reName(splitStr[1]);
        Log.e("name",soraRenamed);

        Log.e("splitted",splitStr[1]);

        //getListeningData(readerId, soraRenamed);
      //  progressBarCyclic.setVisibility(View.VISIBLE);
       // playSound.setVisibility(View.GONE);
       // seekBar.setVisibility(View.GONE);

        observeDataFromViewMOdel();
     /*   final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                if(founded==false){
                Toast.makeText(SuraDetailsActivity.this,"نأسف هذه السورة غير موجودة",Toast.LENGTH_LONG).show();
                Log.e("handler","got");
                progressBarCyclic.setVisibility(View.GONE);
            }}
        }, 2500);

**/
        /**   List<Ayah> sora = new ArrayList<>();
        sora = QuranFragment.viewModel.fullQuran.getValue().getData().getSurahs()
                .get(position).getAyahs();
        String s = "";
        for (int i = 0; i < sora.size(); i++) {
            s = sora.get(i).toString();

        }
        int firstPageinSurah = sora.get(0).getPage();
        showMessage(s, "ok");
        showMessage(firstPageinSurah + "", "no");
        for (List<Ayah> list : surahAyat) {
            for (Ayah ayah : list)
                Log.v("String", ayah.getText()+ayah.getNumberInSurah());
        }
      **/
    }






    int num = 0;
    boolean founded = false ;
    //observe url link from view model
    private void observeDataFromViewMOdel() {



        viewModel.uriLink.observe(SuraDetailsActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("point","start");
                   //get uri link and update mediaplaayer
                if (!s.contains("http")) {//num=1
                    audioUrl="notfound";
                      num++;
                   // Toast.makeText(SuraDetailsActivity.this,"first", Toast.LENGTH_LONG).show();


                }

                if(s.contains("http")){//1  notfounded = false;
                   if(num==1)
                        num=0;
                   founded=true;

                    Log.e("point","if contain");
                    audioUrl=s;
                    progressBarCyclic.setVisibility(View.GONE);
                    playSound.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    Toast.makeText(SuraDetailsActivity.this, audioUrl, Toast.LENGTH_LONG).show();

                }



                }


        });



        viewModel.failedMassage.observe(SuraDetailsActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // progressBarCyclic.setVisibility(View.GONE);
                failedMessage=s;
             // Toast.makeText(SuraDetailsActivity.this,failedMessage+"", Toast.LENGTH_LONG).show();


            } });



        }








    private void getDataIntented() {
        position = getIntent().getIntExtra("position", -1);
        surahName = getIntent().getStringExtra("name");
        readerId=getIntent().getIntExtra("shekh_id",1);

    }
     //set list of list of ayas
    private void getDataModeled() {
        //viewModel =  new ViewModelProvider(this).get(QuranViewModel.class);
        // viewModel.context=SuraDetailsActivity.this;

      //*surahAyat = QuranFragment.viewModel.getSurahAyahs(position);
        surahAyat = QuranFragment.viewModel.fullQuran.getValue().getData().getSurahs()
                .get(position).getAyahs();
        pages = surahAyat.size();

    }

    /**
     * public void cleanList(List<List<Ayah>>lists){
     * <p>
     * for (int i = 0; i <lists.size() ; i++) {
     * if(lists.get(i).get(0).getText().isEmpty() ||
     * lists.get(i).get(0)==null
     * ){
     * lists.remove(i);
     * }
     * <p>
     * }
     * }
     **/

    private void initView() {

        ayasWithPagesRecycler = (RecyclerView) findViewById(R.id.Ayas_WithPagesRecycler);



    }

    public void initRecycler(List<Ayah> suraAyat) {
        adapter = new SurahDetailsAdapter(//List<List<Ayah>>surahAyat
                surahAyat , surahName);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        ayasWithPagesRecycler.setAdapter(adapter);
        ayasWithPagesRecycler.setLayoutManager(linearLayoutManager);
        // where your Position In The Adapter is the position you want to scroll to
        // linearLayoutManager.scrollToPosition(youePositionInTheAdapter);
        linearLayoutManager.setReverseLayout(true);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(ayasWithPagesRecycler);

    }


    //i faced proplem her that was i cant play audio eigther the uri
    // not null evin if media player play local audios and remots other that get by api !!
    //but i found the api is http not https while when i want to make https request i had to add clear text trafic in manefist file !

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

       //String url = audioUrl.substring(0, 4) + "s" + audioUrl.substring(4, audioUrl.length());
       String url=audioUrl;
        //لو انا شغلت سورة وخرجت م الاكتفتفي وروحت اشغل واحده جديده لازم اقفل اللي شغاله الاول عشان
        // كده بشوف الزرار شغال ولا لا
        // لانه
        // لو شغال يبقي انا ف نفس الاكتفتي فاوقفه عادي انما لو مش فيها هعمل الاتنين كونديشن الاولانيين
        if(Helper.mediaPlayer!=null&&buttonPlayed==0){
            Helper.mediaPlayer.stop();
            Helper.mediaPlayer=null ;
        }
        if (buttonPlayed == 0) {
            this.buttonPlayed = 1;

            mediaPlayer = new MediaPlayer();
            helper = new Helper(mediaPlayer, seekBar, textView); ;
            helper.mediaPlayer=this.mediaPlayer;
          //  progressBarCyclic.setVisibility(View.VISIBLE);

            playSound.setImageResource(R.drawable.puase);
            Toast.makeText(SuraDetailsActivity.this, "if", Toast.LENGTH_LONG).show();
           // String url = audioUrl.substring(0, 4) + "s" + audioUrl.substring(4, audioUrl.length());

            helper.onClickButton(url);
           // progressBarCyclic.setVisibility(View.GONE);
        }else{
            buttonPlayed = 0;
            Toast.makeText(SuraDetailsActivity.this, "else", Toast.LENGTH_LONG).show();
            playSound.setImageResource(R.drawable.play);
            helper.clearMediaPlayer();
            seekBar.setProgress(0);

        }

            /**  if (view.getId() == R.id.playSound) {
             if (buttonPlayed == 0) {
             progressBarCyclic.setVisibility(View.VISIBLE);
             buttonPlayed = 1;
             playSound.setImageResource(R.drawable.puase);
             Toast.makeText(SuraDetailsActivity.this, "if", Toast.LENGTH_LONG).show();

             String url = audioUrl.substring(0,4)+"s"+audioUrl.substring(4,audioUrl.length());
             //"https://server8.mp3quran.net/frs_a/114.mp3";// your URL here
             mediaPlayer= new MediaPlayer();
             hdlr=new Handler();
             playAudioFromApi(url);


             } else {
             buttonPlayed = 0;
             Toast.makeText(SuraDetailsActivity.this, "else", Toast.LENGTH_LONG).show();
             playSound.setImageResource(R.drawable.play);
             mediaPlayer.stop();
             mediaPlayer.release();
             mediaPlayer=null;
             seekBar.setProgress(0);


             // hdlr=null;
             } } **/



    }
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playAudioFromApi(String url) {


        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
            // progressBarCyclic.setVisibility(View.GONE);
            //update  seekbar timer
            updatSeekBarTimer(mediaPlayer);


        } catch (IOException e) {
            Toast.makeText(SuraDetailsActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }


    //seekbar horozintal line timer
    public void updatSeekBarTimer(final MediaPlayer mediaPlayer) {
        eTime = mediaPlayer.getDuration();
        sTime = mediaPlayer.getCurrentPosition();
        if (oTime == 0) {
            seekBar.setMax(eTime);
            oTime = 1;
        }
        seekBar.setProgress(sTime);
        updateSongTime = new Runnable() {
            @Override
            public void run() {
                sTime = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(sTime);
                hdlr.postDelayed(this, 100);
            }
        };
        hdlr.postDelayed(updateSongTime, 100);
    }




}