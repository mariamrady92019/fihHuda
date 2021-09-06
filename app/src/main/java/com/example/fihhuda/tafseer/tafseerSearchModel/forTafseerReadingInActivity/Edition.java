package com.example.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity;

import com.google.gson.annotations.SerializedName;

public class Edition{

	@SerializedName("identifier")
	private String identifier;

	@SerializedName("englishName")
	private String englishName;

	@SerializedName("name")
	private String name;

	@SerializedName("format")
	private String format;

	@SerializedName("language")
	private String language;

	@SerializedName("type")
	private String type;

	public String getIdentifier(){
		return identifier;
	}

	public String getEnglishName(){
		return englishName;
	}

	public String getName(){
		return name;
	}

	public String getFormat(){
		return format;
	}

	public String getLanguage(){
		return language;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"Edition{" + 
			"identifier = '" + identifier + '\'' + 
			",englishName = '" + englishName + '\'' + 
			",name = '" + name + '\'' + 
			",format = '" + format + '\'' + 
			",language = '" + language + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}