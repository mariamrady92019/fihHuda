package tool.mariam.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahsItem {

	@SerializedName("number")
	private int number;

	@SerializedName("englishName")
	private String englishName;

	@SerializedName("revelationType")
	private String revelationType;

	@SerializedName("name")
	private String name;

	@SerializedName("ayahs")
	private List<AyahsItem> ayahs;

	@SerializedName("englishNameTranslation")
	private String englishNameTranslation;

	public int getNumber(){
		return number;
	}

	public String getEnglishName(){
		return englishName;
	}

	public String getRevelationType(){
		return revelationType;
	}

	public String getName(){
		return name;
	}

	public List<AyahsItem> getAyahs(){
		return ayahs;
	}

	public String getEnglishNameTranslation(){
		return englishNameTranslation;
	}

	@Override
 	public String toString(){
		return 
			"SurahsItem{" + 
			"number = '" + number + '\'' + 
			",englishName = '" + englishName + '\'' + 
			",revelationType = '" + revelationType + '\'' + 
			",name = '" + name + '\'' + 
			",ayahs = '" + ayahs + '\'' + 
			",englishNameTranslation = '" + englishNameTranslation + '\'' + 
			"}";
		}
}