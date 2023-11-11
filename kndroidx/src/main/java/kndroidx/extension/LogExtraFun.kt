package kndroidx.extension

import android.content.pm.ApplicationInfo
import android.util.Log
import kndroidx.application.ApplicationX.Companion.context

var TAG = "TAG"

val Any.log: LogObject
    get() = LogObject.getObj(TAG, this.toString())

fun Any.log(tag: String = TAG) = LogObject.getObj(tag, this.toString())

fun isDebug(): Boolean {
    return context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
}

class LogObject private constructor(private val tag: String, private val msg: String) {
    companion object {
        internal fun getObj(tag: String, msg: String) = LogObject(tag, msg)
    }

    fun d() {
        if (isDebug())
            Log.d(tag, msg)
    }

    fun e() {
        Log.e(tag, msg)
    }

    fun w() {
        Log.w(tag, msg)
    }

    fun i() {
        if (isDebug())
            Log.i(tag, msg)
    }

    fun v() {
        if (isDebug())
            Log.v(tag, msg)
    }

    fun f() {
        Log.wtf(tag, msg)
    }

    fun f(tr: Throwable) {
        Log.wtf(tag, msg, tr)
    }
}
