package tool.mariam.fihhuda.quran.netWorking;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tool.mariam.fihhuda.quran.listeningResponseModels.ResponseModel;

public interface ApiServices {

    @FormUrlEncoded
    @POST("QuranShared.php ")
    Call<ResponseModel> getLinkByIdAndSuraName
            (@Field("reader_id") int id, @Field("sura") String suraName);


}
