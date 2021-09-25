package tool.mariam.fihhuda.azkar.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import tool.mariam.fihhuda.Base.App
import tool.mariam.fihhuda.azkar.parsingJson.AzkarDataItem
import tool.mariam.fihhuda.azkar.parsingJson.AzkarResponse
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset
import java.util.*

class AzkarViewModel(application: Application) : AndroidViewModel(application) {


    val allAzkarFromJson: AzkarResponse?
        get() {
            var fileIn: InputStream? = null
            var allListOfAzkar: AzkarResponse? = null
            try {
                fileIn = getApplication<App>().assets.open("azkar.json")
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

    val categories: List<String>
        get() {
            val azkarResponse = allAzkarFromJson
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
            return cat
        }

    fun getAZkarByCategoryName(categryName: String): List<AzkarDataItem> {
        val azkarResponse = allAzkarFromJson
        val spesefiedAzkarBYCategry: MutableList<AzkarDataItem> = ArrayList()
        var hasFounded = false // to
        for (i in azkarResponse!!.azkarData.indices) {
            if (azkarResponse.azkarData[i].category == categryName) {
                spesefiedAzkarBYCategry.add(azkarResponse.azkarData[i])
                hasFounded = true
            } else if (azkarResponse.azkarData[i].category != categryName && hasFounded) {
                break
            }
        }
        return spesefiedAzkarBYCategry
    }
}