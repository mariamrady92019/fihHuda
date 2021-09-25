package tool.mariam.fihhuda.azkar.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import tool.mariam.fihhuda.azkar.parsingJson.AzkarDataItem
import tool.mariam.fihhuda.azkar.parsingJson.AzkarResponse
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset
import java.util.*

class AzkarViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories


    private val _azkarsLD = MutableLiveData<List<AzkarDataItem>>()
    val azkars: LiveData<List<AzkarDataItem>> = _azkarsLD

    fun allAzkarFromJson(ctx: Context): AzkarResponse? {
        var fileIn: InputStream? = null
        var allListOfAzkar: AzkarResponse? = null
        try {
            fileIn = ctx.assets.open("azkar.json")
            val bufferedIn = BufferedInputStream(fileIn)
            val reader: Reader = InputStreamReader(bufferedIn, Charset.forName("UTF-8"))
            allListOfAzkar = Gson().fromJson(reader, AzkarResponse::class.java)
            Log.e("azkar", "parsed")
            Log.e("azkar", allListOfAzkar.azkarData[0].zekr)

            //gitListOfAzkar(allListOfAzkar);
            //getCategories(allListOfAzkar);
        } catch (e: IOException) {
            Log.e("azkar", "error")
        }
        return allListOfAzkar
    }

    fun gitListOfAzkar(azkarResponse: AzkarResponse): List<List<AzkarDataItem>> {
        val listOfAllAzkar = azkarResponse.azkarData
        val listOfcategories: MutableList<List<AzkarDataItem>> = ArrayList()
        var category = listOfAllAzkar[0].category
        var newList: MutableList<AzkarDataItem> = ArrayList()
        for (i in listOfAllAzkar.indices) {
            if (listOfAllAzkar[i].category == category) {
                newList.add(listOfAllAzkar[i])
            } else {
                listOfcategories.add(newList)
                newList = ArrayList()
                category = listOfAllAzkar[i].category
            }
        }
        val k = 0
        var y = 0
        while (y < listOfcategories.size - 1) {
            y++
            Log.e("print", k.toString() + "")
            for (f in 0 until listOfcategories[y].size - 1) {
                Log.e("print", f.toString() + "")
                Log.e("print", listOfcategories[y][f].category)
            }
            y++
        }
        return listOfcategories
    }

    fun prepareCategories(ctx: Context) {
        val azkarResponse = allAzkarFromJson(ctx)
        val cat: MutableList<String> = ArrayList()
        var category = azkarResponse!!.azkarData[0].category
        cat.add(category)
        for (item in azkarResponse.azkarData) {
            category = if (item.category == category) {
                continue
            } else {
                cat.add(item.category)
                item.category
            }
        }
        return _categories.postValue(cat)
    }

    fun prepareAzkarByCategory(ctx: Context, categoryName: String?) {
        categoryName ?: return
        val azkarResponse = allAzkarFromJson(ctx)
        val spesefiedAzkarBYCategry: MutableList<AzkarDataItem> = ArrayList()
        var hasFounded = false // to
        for (i in azkarResponse!!.azkarData.indices) {
            if (azkarResponse.azkarData[i].category == categoryName) {
                spesefiedAzkarBYCategry.add(azkarResponse.azkarData[i])
                hasFounded = true
            } else if (azkarResponse.azkarData[i].category != categoryName && hasFounded) {
                break
            }
        }
        _azkarsLD.postValue(spesefiedAzkarBYCategry)
    }
}