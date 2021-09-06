package com.example.fihhuda.tafseer.tafseerSearchModel.forSearchInFragment;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SurahsSearchItem {

	@SerializedName("num")
	private String num;

	@SerializedName("name")
	private String name;

	@SerializedName("ayahs")
	private List<AyahsSearchItem> ayahs;

	public String getNum(){
		return num;
	}

	public String getName(){
		return name;
	}

	public List<AyahsSearchItem> getAyahs(){
		return ayahs;
	}

	@Override
 	public String toString(){
		return 
			"SurahsItem{" + 
			"num = '" + num + '\'' + 
			",name = '" + name + '\'' + 
			",ayahs = '" + ayahs + '\'' + 
			"}";
		}
}