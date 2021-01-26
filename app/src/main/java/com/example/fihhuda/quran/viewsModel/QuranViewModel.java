package com.example.fihhuda.quran.viewsModel;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fihhuda.R;
import com.example.fihhuda.quran.adapters.SurahDetailsAdapter;
import com.example.fihhuda.quran.fullQuranReadingModels.Ayah;
import com.example.fihhuda.quran.fullQuranReadingModels.FullQuran;
import com.example.fihhuda.quran.fullQuranReadingModels.Surah;
import com.example.fihhuda.quran.views.QuranFragment;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class QuranViewModel extends ViewModel {
   public static Context context ;

    public  MutableLiveData<FullQuran> fullQuran= new MutableLiveData<>();
    public  MutableLiveData<String> message= new MutableLiveData<>();
    FullQuran full = new FullQuran();

    public QuranViewModel() {
    }

    public QuranViewModel(Context context) {
        this.context=context;
    }



    //this function too get all quran from jason as aclass called fullquraan
    //  have a class called data .. thia data class have list of suras
    public  void getAllQuranFromJson(){

        InputStream fileIn = null;
        try {
            fileIn = context.getAssets().open("quran.json");
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            Reader reader = new InputStreamReader(bufferedIn, Charset.forName("UTF-8")) ;
           full =new Gson().fromJson(reader, FullQuran.class);
            fullQuran.setValue(full);
            message.setValue("سور القران كاملاً");

        } catch (IOException e) {
         message.setValue(e.getLocalizedMessage());
        }
       }

//return surah as pages each page as list of ayah
     public  List<List<Ayah>> getSurahAyahs(int position){
         List<Surah> allSouar = full.getData().getSurahs();
        if(position==0){
            //ayat surah
            List<Ayah> currentSurah = new ArrayList<Ayah>();
            currentSurah=allSouar.get(position).getAyahs();
            List<List<Ayah>> fatiha = new ArrayList<>();
            fatiha.add(currentSurah);
            return fatiha;
        }

         //ayat surah
         List<Ayah> currentSurah = new ArrayList<Ayah>();
          currentSurah=allSouar.get(position).getAyahs();

          // pages
         int firstPageinSurah= currentSurah.get(0).getPage();//page=2  baqra
         int lastPageInSurah= currentSurah.get(currentSurah.size()-1).getPage();//49 baqara
          List<List<Ayah>> listOfPages = new ArrayList<>();
          int lastayah= 0 ;
          int index = 0 ;

          for (int i = firstPageinSurah; i <=lastPageInSurah; i=i+1) {
             List<Ayah> pageNumber_i = new ArrayList<>();
             for (int j = lastayah  ; j < currentSurah.size() ;j=j+1) {
                 index=j;
                 if(currentSurah.get(j).getPage()==i){
                     String s = currentSurah.get(j).getText();
                     pageNumber_i.add(currentSurah.get(j));

                  }else{
                     listOfPages.add(pageNumber_i);
                    lastayah= index; //lastaya
                     break;
                 }
             }
         }
       // return  clean( listOfPages );
          List<List<Ayah>> listOfPages2=  listOfPages;
        listOfPages2.removeAll(Collections.singleton(""));
        listOfPages2.removeAll(Collections.singleton(null));

         return listOfPages2 ;




     }






}






