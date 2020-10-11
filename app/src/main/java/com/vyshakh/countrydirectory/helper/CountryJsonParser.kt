package com.vyshakh.countrydirectory.helper

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

class CountryJsonParser {

    companion object {
        fun inputStreamToString(inputStream: InputStream): String {
            return try {
                val bytes = ByteArray(inputStream.available())
                inputStream.read(bytes, 0, bytes.size)
                String(bytes)
            } catch (e: IOException) {
                ""
            }
        }
    }
}

inline fun <reified T> getObjectFromJson(jsonString: String): T {
    return Gson().fromJson(jsonString, T::class.java)
}

fun getJsonString(context:Context,jsonFileName: String): String {
    return CountryJsonParser.inputStreamToString(
       context.assets.open(jsonFileName)
    )
}