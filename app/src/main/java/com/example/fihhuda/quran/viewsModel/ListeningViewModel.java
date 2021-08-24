package com.example.fihhuda.quran.viewsModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fihhuda.quran.fullQuranReadingModels.FullQuran;
import com.example.fihhuda.quran.listeningResponseModels.DataItem;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;


public class ListeningViewModel extends ViewModel {
    public MutableLiveData<List<DataItem>> listOfData = new MutableLiveData<>();
    public MutableLiveData<String> uriLink = new MutableLiveData<>("");
    public MutableLiveData<String> failedMassage = new MutableLiveData<>();
    ListeningRebosotry listeningRebosotry = new ListeningRebosotry();
   public static MutableLiveData<String> preparedDone = new MutableLiveData<>();
   public static MutableLiveData<String> completionDone = new MutableLiveData<>();

    public void setContext(Context context) {
        this.context = context;
    }

    // public static MutableLiveData<String> destroyedDone = new MutableLiveData<>();
  public  Context context ;

    public ListeningViewModel(String s) {
        uriLink = listeningRebosotry.uriLink;
    }


    public ListeningViewModel() {
        listOfData = listeningRebosotry.listOfDataItems;
        failedMassage = listeningRebosotry.message;
        uriLink = listeningRebosotry.uriLink;

    }




    public void getListeningDataBId_SuraName(int reader_id, String suraName) {
        listeningRebosotry.getListeningDataById_SuraName(reader_id, suraName);
    }

  /*  public void setDestroyedDone(String destroyedDone) {
        this.destroyedDone.setValue(destroyedDone);
    }
*/
    public void setCompletionDone(String completionDone) {
        this.completionDone.setValue(completionDone);
    }


    public String getSurahNameByPosition(int position){

        InputStream fileIn = null;
        String surahName= null;
        try {
            fileIn = context.getAssets().open("quran.json");
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            Reader reader = new InputStreamReader(bufferedIn, Charset.forName("UTF-8")) ;
            FullQuran full =new Gson().fromJson(reader, FullQuran.class);
            surahName = full.getData().getSurahs().get(position).getName();
        } catch (IOException e) {
        }

        return surahName;
    }
}