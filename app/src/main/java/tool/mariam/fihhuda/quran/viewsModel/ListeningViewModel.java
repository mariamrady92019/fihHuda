package tool.mariam.fihhuda.quran.viewsModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import tool.mariam.fihhuda.quran.fullQuranReadingModels.FullQuran;
import tool.mariam.fihhuda.quran.listeningResponseModels.DataItem;


public class ListeningViewModel extends ViewModel {
    public static MutableLiveData<String> preparedDone = new MutableLiveData<>();
    public static MutableLiveData<String> completionDone = new MutableLiveData<>();
    public MutableLiveData<List<DataItem>> listOfData = new MutableLiveData<>();
    public MutableLiveData<String> uriLink = new MutableLiveData<>("");
    public MutableLiveData<String> failedMassage = new MutableLiveData<>();
    // public static MutableLiveData<String> destroyedDone = new MutableLiveData<>();
    public Context context;
    ListeningRebosotry listeningRebosotry = new ListeningRebosotry();

    public ListeningViewModel(String s) {
        uriLink = listeningRebosotry.uriLink;
    }

    public ListeningViewModel() {
        listOfData = listeningRebosotry.listOfDataItems;
        failedMassage = listeningRebosotry.message;
        uriLink = listeningRebosotry.uriLink;

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getListeningDataBId_SuraName(int reader_id, String suraName) {
        listeningRebosotry.getListeningDataById_SuraName(reader_id, suraName);
    }

  /*  public void setDestroyedDone(String destroyedDone) {
        this.destroyedDone.setValue(destroyedDone);
    }
*/


    public String getSurahNameByPosition(int position) {

        InputStream fileIn = null;
        String surahName = null;
        try {
            fileIn = context.getAssets().open("quran.json");
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            Reader reader = new InputStreamReader(bufferedIn, StandardCharsets.UTF_8);
            FullQuran full = new Gson().fromJson(reader, FullQuran.class);
            surahName = full.getData().getSurahs().get(position).getName();
        } catch (IOException e) {
        }

        return surahName;
    }
}