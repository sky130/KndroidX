package kndroidx.extension

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kndroidx.application.BaseApplication

inline fun Context.startIntent(block: Intent.() -> Unit) : Intent {
    val intent = Intent()
    intent.block()
    startActivity(intent)
    return intent
}

inline fun <reified T : Activity> Context.start() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : Activity> Context.start(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    startActivity(intent)
}

inline fun <reified T : Service> start(
    flags: Int = Application.BIND_AUTO_CREATE,
    context: Context = BaseApplication.baseApplication,
    crossinline onServiceConnected: (ComponentName, IBinder) -> Unit = { _, _ -> },
    crossinline onServiceDisconnected: (ComponentName) -> Unit = { _ -> },
    intent: Intent.() -> Unit = {},
) : Intent {
    val service = Intent(context, T::class.java)
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            onServiceConnected(name, binder)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            onServiceDisconnected(name)
        }
    }
    service.intent()
    context.bindService(service, serviceConnection, flags)
    return service
}