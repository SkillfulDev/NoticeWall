package ua.chernonog.noticewall.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException

object JsonFileParser {

    fun getAllCountries(context: Context): ArrayList<String> {
        val countriesNames = ArrayList<String>()
        try {
            val inputStream = context.assets.open("countries.json")
            val size = inputStream.available()
            val bytesArray = ByteArray(size)
            inputStream.read(bytesArray)
            val jsonFile = String(bytesArray)
            val jsonObject = JSONObject(jsonFile)
            val receivedCountriesNames = jsonObject.names()

            if (receivedCountriesNames != null) {
                for (i in 0 until receivedCountriesNames.length()) {
                    countriesNames.add(receivedCountriesNames.getString(i))
                }
            }

        } catch (e: IOException) {
            TODO("IMPLEMENT EXCEPTION HANDLER")
        }
        return countriesNames
    }
}
