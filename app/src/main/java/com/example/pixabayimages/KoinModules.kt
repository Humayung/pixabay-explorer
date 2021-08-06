package com.example.pixabayimages

import com.example.pixabayimages.fragment.FragmentDetails
import com.example.pixabayimages.fragment.FragmentAccount
import com.example.pixabayimages.fragment.FragmentExplore
import com.example.pixabayimages.fragment.FragmentFavorite
import com.example.pixabayimages.networking.*
import com.example.pixabayimages.persistence.Preferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val koinModules = module {
    single { Repository(get(), get()) }
    single { ApiReq() }
    factory { provideOkHttpClient(get()) }
    factory { provideForecastApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
    factory { ResponseHandler() }
    single { FragmentAccount() }
    single { FragmentExplore() }
    single { FragmentFavorite() }
    single { MemoryDb() }
    single { Preferences(androidApplication()) }
    single { FragmentDetails() }
}