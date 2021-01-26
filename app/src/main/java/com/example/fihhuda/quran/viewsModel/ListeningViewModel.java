package com.example.fihhuda.quran.viewsModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fihhuda.quran.listeningResponseModels.DataItem;

import java.util.List;

public class ListeningViewModel extends ViewModel {
    public MutableLiveData<List<DataItem>> listOfData = new MutableLiveData<>();
    public MutableLiveData<String> uriLink = new MutableLiveData<>("");
   public MutableLiveData<String> failedMassage = new MutableLiveData<>();
    ListeningRebosotry listeningRebosotry = new ListeningRebosotry();

    public ListeningViewModel() {
    listOfData=listeningRebosotry.listOfDataItems;
    failedMassage=listeningRebosotry.message;
    uriLink = listeningRebosotry.uriLink;

    }


    public void getListeningDataBId_SuraName(int reader_id , String suraName){
        listeningRebosotry.getListeningDataById_SuraName(reader_id,suraName);
     }


}
