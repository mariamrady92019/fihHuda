package com.example.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity;

import com.google.gson.annotations.SerializedName;

public class AyahsItem{

	@SerializedName("number")
	private int number;

	@SerializedName("hizbQuarter")
	private int hizbQuarter;

	@SerializedName("ruku")
	private int ruku;

	@SerializedName("manzil")
	private int manzil;

	@SerializedName("text")
	private String text;

	@SerializedName("page")
	private int page;

	@SerializedName("sajda")
	private Object sajda;

	@SerializedName("numberInSurah")
	private int numberInSurah;

	@SerializedName("juz")
	private int juz;

	public int getNumber(){
		return number;
	}

	public int getHizbQuarter(){
		return hizbQuarter;
	}

	public int getRuku(){
		return ruku;
	}

	public int getManzil(){
		return manzil;
	}

	public String getText(){
		return text;
	}

	public int getPage(){
		return page;
	}

	public Object getSajda() {
		return sajda;
	}

	public void setSajda(Sajda sajda) {
		this.sajda = sajda;
	}

	public int getNumberInSurah(){
		return numberInSurah;
	}

	public int getJuz(){
		return juz;
	}

	@Override
 	public String toString(){
		return 
			"AyahsItem{" + 
			"number = '" + number + '\'' + 
			",hizbQuarter = '" + hizbQuarter + '\'' + 
			",ruku = '" + ruku + '\'' + 
			",manzil = '" + manzil + '\'' + 
			",text = '" + text + '\'' + 
			",page = '" + page + '\'' + 
			",sajda = '" + sajda + '\'' + 
			",numberInSurah = '" + numberInSurah + '\'' + 
			",juz = '" + juz + '\'' + 
			"}";
		}
}