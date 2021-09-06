package com.example.fihhuda.tafseer.tafseerSearchModel.forSearchInFragment;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SearchForAyah{

	@SerializedName("surahs")
	private List<SurahsSearchItem> surahs;

	public List<SurahsSearchItem> getSurahs(){
		return surahs;
	}

	@Override
 	public String toString(){
		return 
			"SearchForAyah{" + 
			"surahs = '" + surahs + '\'' + 
			"}";
		}
}