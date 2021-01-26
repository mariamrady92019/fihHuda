package com.example.fihhuda.quran.netWorking;

import com.example.fihhuda.quran.listeningResponseModels.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServices {

@FormUrlEncoded
@POST("QuranShared.php ")
public abstract Call<ResponseModel> getLinkByIdAndSuraName
        (@Field("reader_id") int id , @Field("sura") String suraName);




}
