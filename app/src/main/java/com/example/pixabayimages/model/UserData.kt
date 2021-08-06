package com.example.pixabayimages.model

class UserData (
    var username: String = "",
    var password: String = "",
    val favoriteList: ArrayList<PixabayResponse.Photo?> = arrayListOf()
)