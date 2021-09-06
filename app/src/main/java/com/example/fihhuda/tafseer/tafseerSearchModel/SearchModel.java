package com.example.fihhuda.tafseer.tafseerSearchModel;

import com.example.fihhuda.tafseer.tafseerSearchModel.forSearchInFragment.AyahsSearchItem;

public class SearchModel {

    String surahName;
    AyahsSearchItem ayah ;

    public SearchModel(String surahName, AyahsSearchItem ayah) {
        this.surahName = surahName;
        this.ayah = ayah;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }

    public AyahsSearchItem getAyah() {
        return ayah;
    }

    public void setAyah(AyahsSearchItem ayah) {
        this.ayah = ayah;
    }
}
