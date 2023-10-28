package kndroidx.extension

import android.util.Log

var TAG = "TAG"

val Any.log: LogObject
    get() = LogObject.getObj(TAG, this.toString())

fun Any.log(tag: String = TAG) = LogObject.getObj(tag, this.toString())

class LogObject private constructor(private val tag: String, private val msg: String) {
    companion object {
        internal fun getObj(tag: String, msg: String) = LogObject(tag, msg)
    }

    fun d() {
        Log.d(tag, msg)
    }

    fun e() {
        Log.e(tag, msg)
    }

    fun w() {
        Log.w(tag, msg)
    }

    fun i() {
        Log.i(tag, msg)
    }

    fun v() {
        Log.v(tag, msg)
    }
}
