package com.example.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("edition")
	private Edition edition;

	@SerializedName("surahs")
	private List<SurahsItem> surahs;

	public Edition getEdition(){
		return edition;
	}

	public List<SurahsItem> getSurahs(){
		return surahs;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"edition = '" + edition + '\'' + 
			",surahs = '" + surahs + '\'' + 
			"}";
		}
}