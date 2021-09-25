package tool.mariam.fihhuda.tafseer.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.quran.fullQuranReadingModels.Ayah;
import tool.mariam.fihhuda.quran.fullQuranReadingModels.Surah;
import tool.mariam.fihhuda.quran.viewsModel.QuranViewModel;
import tool.mariam.fihhuda.tafseer.adapters.TafseerAllSuraAdapter;
import tool.mariam.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity.AyahsItem;

public class TafseerDetailsForAllSura extends AppCompatActivity {

    protected EditText searchBar;
    protected RecyclerView allSurahTafseerRecycler;
    String suraName;
    QuranViewModel quranViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_tafseer_details_for_all_sura);
        quranViewModel= new QuranViewModel();
        quranViewModel.context= TafseerDetailsForAllSura.this;
        initView();
        getIntentedData();


    }

    private void getIntentedData() {
        suraName = getIntent().getStringExtra("suraName");
        Surah ayat = quranViewModel.getSuaraAyatForTafseer(suraName);
        List<Ayah> ayatFortafseer = ayat.getAyahs();
        List<AyahsItem> tafseer =quranViewModel.getTafseerForSura(suraName).getAyahs();
        Log.e("tafseer",tafseer.toString());
        initRecyclerView(ayatFortafseer,tafseer);

    }

    private void initRecyclerView( List<Ayah> ayatFortafseer,  List<AyahsItem> tafseer) {
        TafseerAllSuraAdapter adapter = new TafseerAllSuraAdapter(tafseer,ayatFortafseer);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        allSurahTafseerRecycler.setAdapter(adapter);
        allSurahTafseerRecycler.setLayoutManager(linearLayoutManager);

    }

    private void initView() {
        searchBar = (EditText) findViewById(R.id.search_bar);
        allSurahTafseerRecycler = (RecyclerView) findViewById(R.id.allSurah_tafseer);
    }
}