package com.example.pixabayimages.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.pixabayimages.model.PixabayResponse
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject

class ApiReq : KoinComponent {
    private val repository: Repository by inject()
//    fun ping(random:Int): LiveData<Resource<PublicImage>> {
//        return liveData(Dispatchers.IO) {
//            emit(Resource.loading(null))
//            emit(repository.ping(random))
//        }
//    }

    fun getImages(page: Int, tags: String): LiveData<Resource<PixabayResponse>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(repository.getImages(page, tags))
        }
    }
}