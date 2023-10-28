package kndroidx.application

import android.annotation.SuppressLint
import android.app.Application
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kndroidx.application.BaseApplication.Companion.baseApplication

@SuppressLint("StaticFieldLeak")
class BaseApplication : Application() {

    companion object {
        lateinit var context: Context
        lateinit var baseApplication: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        baseApplication = this
    }

}

