package com.example.fihhuda.quran.listeningResponseModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseModel{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("count")
	private int count;

	@SerializedName("message")
	private String message;

	public List<DataItem> getData(){
		return data;
	}

	public int getCount(){
		return count;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseModel{" + 
			"data = '" + data + '\'' + 
			",count = '" + count + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}