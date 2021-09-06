package com.example.fihhuda.tafseer.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fihhuda.R;
import com.example.fihhuda.quran.fullQuranReadingModels.Ayah;
import com.example.fihhuda.quran.fullQuranReadingModels.Surah;
import com.example.fihhuda.quran.viewsModel.QuranViewModel;
import com.example.fihhuda.tafseer.adapters.TafseerAllSuraAdapter;
import com.example.fihhuda.tafseer.adapters.TafseerFragmentAdapter;
import com.example.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity.AyahsItem;

import java.util.List;

public class TafseerDetailsForAllSura extends AppCompatActivity {

    protected EditText searchBar;
    protected RecyclerView allSurahTafseerRecycler;
    String suraName ;
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