package com.example.pixabayimages

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.example.pixabayimages.model.ImageDetails
import com.example.pixabayimages.model.PixabayResponse
import com.example.pixabayimages.model.PixabayResponse.*
import org.koin.core.KoinComponent

class MemoryDb : KoinComponent {
    val imageData: MutableLiveData<Photo> by lazy {
        MutableLiveData<Photo>()
    }

    val previewImage: MutableLiveData<Drawable> by lazy {
        MutableLiveData<Drawable>()
    }
    val query: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val imageDetails: MutableLiveData<ImageDetails> by lazy {
        MutableLiveData<ImageDetails>()
    }

    val placeHolderImg: MutableLiveData<Drawable> by lazy {
        MutableLiveData<Drawable>()
    }


}