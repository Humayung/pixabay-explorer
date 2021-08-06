package com.example.pixabayimages.persistence

import android.content.Context
import android.content.SharedPreferences
import com.example.pixabayimages.model.PixabayResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Preferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun setFavoriteList(data: PixabayResponse.Photo?) : Preferences{
        val savepreference = getFavoriteList()
        savepreference.add(data)
        preferences.edit().putString("favoriteList", Gson().toJson(savepreference)).apply()
        return this
    }

    fun getFavoriteList(): ArrayList<PixabayResponse.Photo?>{
        val string = preferences.getString("favoriteList", "").toString()
        string.ifEmpty { return arrayListOf() }
        val listType: Type = object : TypeToken<ArrayList<PixabayResponse.Photo?>?>() {}.type
        return Gson().fromJson(string, listType)
    }


    fun getQuery(): String {
        return preferences.getString("query", "beach").toString()
    }

    fun setQuery(data : String?): Preferences{
        preferences.edit().putString("query", data).apply()
        return this
    }

}