package tool.mariam.fihhuda.tafseer.tafseerSearchModel.forSearchInFragment;

import com.google.gson.annotations.SerializedName;

public class AyahsSearchItem {

	@SerializedName("num")
	private String num;

	@SerializedName("text")
	private String text;

	public String getNum(){
		return num;
	}

	public String getText(){
		return text;
	}

	@Override
 	public String toString(){
		return 
			"AyahsItem{" + 
			"num = '" + num + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}