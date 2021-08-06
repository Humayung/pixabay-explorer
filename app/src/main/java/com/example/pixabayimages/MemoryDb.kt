package com.example.pixabayimages

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.example.pixabayimages.model.ImageDetails
import com.example.pixabayimages.model.UserData
import com.example.pixabayimages.model.PixabayResponse.*
import org.koin.core.KoinComponent

class MemoryDb : KoinComponent {

    val query: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentUser: MutableLiveData<UserData> by lazy {
        MutableLiveData<UserData>()
    }

    val userList: MutableLiveData<ArrayList<UserData>> by lazy {
        MutableLiveData<ArrayList<UserData>>()
    }


}