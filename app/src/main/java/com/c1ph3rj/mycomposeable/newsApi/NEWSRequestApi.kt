package com.c1ph3rj.mycomposeable.newsApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NEWSRequestApi {
    @GET ("top-headlines")
    fun getDashboardNews(@Query("country") country :String, @Query("language") language:String, @Query ("apiKey") apiKey: String) : Call<NEWSResponse?>

    @GET("top-headlines")
    fun getTopTechNews(@Query("category") category: String, @Query("language") language:String, @Query ("apiKey") apiKey: String) : Call<NEWSResponse?>

    @GET("everything")
    fun getAllArticlesAbout(@Query("q") q: String, @Query("sortBy") sortBy:String, @Query("language") language:String, @Query ("apiKey") apiKey: String)

}