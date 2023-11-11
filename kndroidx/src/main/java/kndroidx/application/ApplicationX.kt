package kndroidx.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
open class ApplicationX : Application() {

    companion object {
        lateinit var context: Context
        lateinit var application: ApplicationX
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        application = this
    }

}

