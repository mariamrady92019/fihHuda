package com.example.fihhuda.azkar.viewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.fihhuda.azkar.parsingJson.AzkarDataItem;
import com.example.fihhuda.azkar.parsingJson.AzkarResponse;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class AzkarViewModel extends ViewModel {

       Context context;

    public AzkarViewModel() {
    }


    public void setContext(Context context) {
        this.context=context;
    }


    public  AzkarResponse getAllAzkarFromJson(){

        InputStream fileIn = null;
        AzkarResponse allListOfAzkar= null;
        try {
            fileIn = context.getAssets().open("azkar.json");
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            Reader reader = new InputStreamReader(bufferedIn, Charset.forName("UTF-8")) ;
             allListOfAzkar=new Gson().fromJson(reader, AzkarResponse.class);
            Log.e("azkar","parsed");
            Log.e("azkar",allListOfAzkar.getAzkarData().get(0).getZekr());

               //gitListOfAzkar(allListOfAzkar);
           //getCategories(allListOfAzkar);

        } catch (IOException e) {
            Log.e("azkar","error");
        }
        return allListOfAzkar;
    }

    public  List<List<AzkarDataItem>> gitListOfAzkar(AzkarResponse azkarResponse){
       List<AzkarDataItem> listOfAllAzkar= azkarResponse.getAzkarData();
       List<List<AzkarDataItem>> listOfcategories = new ArrayList<>();
       String category = listOfAllAzkar.get(0).getCategory();
        List<AzkarDataItem> newList = new ArrayList<>();

      for (int i = 0 ; i<listOfAllAzkar.size();i++){
               if(listOfAllAzkar.get(i).getCategory().equals(category)){
                   newList.add(listOfAllAzkar.get(i));
               }
               else{
                   listOfcategories.add(newList);
                   newList = new ArrayList<>();
                   category=listOfAllAzkar.get(i).getCategory();
               }

        }

      int k = 0 ;
        for ( int y = 0 ; y<listOfcategories.size()-1; y++) {
              y++;
            Log.e("print",k+"");

            for (int f=0 ; f<listOfcategories.get(y).size()-1;f++) {
                Log.e("print",f+"");

                Log.e("print",listOfcategories.get(y).get(f).getCategory());
            }
        }
        return listOfcategories;
    }



       public List<String> getCategories(){

           AzkarResponse azkarResponse = getAllAzkarFromJson();
        List<String> cat = new ArrayList<>();

        String category = azkarResponse.getAzkarData().get(0).getCategory();
        cat.add(category);
           for (AzkarDataItem item: azkarResponse.getAzkarData()) {
                  if(item.getCategory().equals(category)){
                      continue;
                  }else{
                      cat.add(item.getCategory());
                      category = item.getCategory();
                  }}

           return cat;

       }


     public List<AzkarDataItem> getAZkarByCategoryName(String categryName){
         AzkarResponse azkarResponse = getAllAzkarFromJson();

         List<AzkarDataItem> spesefiedAzkarBYCategry = new ArrayList<>();
        boolean hasFounded = false; // to
        for(int i = 0 ; i<azkarResponse.getAzkarData().size();i++){
            if(azkarResponse.getAzkarData().get(i).getCategory().equals(categryName)){
                spesefiedAzkarBYCategry.add((azkarResponse.getAzkarData().get(i)));
                hasFounded= true ;
            }else if(!azkarResponse.getAzkarData().get(i).getCategory().equals(categryName)&&hasFounded){
                break;
            }
        }


        return  spesefiedAzkarBYCategry;

     }

}
