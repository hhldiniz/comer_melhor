package com.hugo.comermelhor

import android.app.Application
import androidx.room.Room
import com.hugo.comermelhor.data.AppDatabase

class App : Application() {
    var db: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "comermelhor").build()
        instance = this
    }

    companion object {
        var instance: App? = null
    }
}