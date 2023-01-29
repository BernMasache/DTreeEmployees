package com.example.dtreeemployees.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiInstance {
    companion object{
        const val BASE_URL= "https://exercise-b342.restdb.io/rest/"
        fun getRetrofitApiInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
