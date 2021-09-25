package tool.mariam.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity;

import com.google.gson.annotations.SerializedName;

public class Sajda{

	@SerializedName("obligatory")
	private boolean obligatory;

	@SerializedName("id")
	private int id;

	@SerializedName("recommended")
	private boolean recommended;

	public boolean isObligatory(){
		return obligatory;
	}

	public int getId(){
		return id;
	}

	public boolean isRecommended(){
		return recommended;
	}

	@Override
 	public String toString(){
		return 
			"Sajda{" + 
			"obligatory = '" + obligatory + '\'' + 
			",id = '" + id + '\'' + 
			",recommended = '" + recommended + '\'' + 
			"}";
		}
}