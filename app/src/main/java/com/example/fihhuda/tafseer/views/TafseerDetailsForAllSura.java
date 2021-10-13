package com.example.fihhuda.tafseer.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

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

    protected TextView searchBar;
    protected RecyclerView allSurahTafseerRecycler;
    String suraName ;
    int ayaNumber;
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
        Log.e("suraName", suraName);

        ayaNumber= Integer.parseInt(getIntent().getStringExtra("ayaNumber"));
         
        Surah ayat = quranViewModel.getSuaraAyatForTafseer(suraName);
        List<Ayah> ayatFortafseer = ayat.getAyahs();
        List<AyahsItem> tafseer =quranViewModel.getTafseerForSura(suraName).getAyahs();
        Log.e("tafseer",tafseer.toString());
        searchBar.setText(suraName+"");
        initRecyclerView(ayatFortafseer,tafseer , ayaNumber);

    }

    private void initRecyclerView( List<Ayah> ayatFortafseer,  List<AyahsItem> tafseer,int ayaNumber) {
        TafseerAllSuraAdapter adapter = new TafseerAllSuraAdapter(tafseer,ayatFortafseer);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        allSurahTafseerRecycler.setAdapter(adapter);
        allSurahTafseerRecycler.setLayoutManager(linearLayoutManager);
        linearLayoutManager.scrollToPositionWithOffset(ayaNumber-1,-1);
        adapter.toggleSelection(ayaNumber-1);
        adapter.notifyDataSetChanged();

    }

    private void initView() {
        searchBar = (TextView) findViewById(R.id.search_bar);
        allSurahTafseerRecycler = (RecyclerView) findViewById(R.id.allSurah_tafseer);
    }
}