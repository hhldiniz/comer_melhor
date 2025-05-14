package com.hugo.comermelhor

import android.app.Application
import androidx.room.Room
import com.hugo.comermelhor.data.AppDatabase
import com.hugo.comermelhor.data.services.openfoodapi.OpenFoodApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    var db: AppDatabase? = null
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://world.openfoodfacts.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var openFoodApiService: OpenFoodApiService? = null

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "comermelhor").build()
        openFoodApiService = retrofit.create(OpenFoodApiService::class.java)
        instance = this
    }

    companion object {
        var instance: App? = null
    }
}