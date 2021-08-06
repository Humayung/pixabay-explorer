package com.example.pixabayimages.model

import java.io.Serializable

class PixabayResponse(
    var total: Int = 0,
    var totalHits: Int = 0,
    var hits: ArrayList<Photo> = arrayListOf()) : Serializable{
    class Photo {
        var id: String = ""
        var pageURL: String = ""
        var type : String = ""
        var tags : String = ""
        var previewURL = ""
        var previewWidth = ""
        var previewHeight = ""
        var webformatURL = ""
        var webformatWidth = ""
        var webformatHeight = ""
        var largeImageURL = ""
        var imageWidth = ""
        var imageHeight = ""
        var imageSize =  ""
        var views = ""
        var downloads = ""
        var collections = ""
        var likes = ""
        var comments =  ""
        var user_id = ""
        var user = ""
        var userImageURL = ""
    }
}

