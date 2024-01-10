package kndroidx.extension

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Build
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.getSystemService
import kndroidx.KndroidX.context

internal val clipboard = lazy {
    context.getSystemService<ClipboardManager>()
}

@Suppress("DEPRECATION")
fun Any.toast(int: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this.toString(), int).apply {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
            val toastView = this.view // 获取 Toast 的视图
            val textView = toastView?.findViewById<TextView>(android.R.id.message)
            textView?.setTextColor(Color.WHITE)
        }
        show()
    }
}

@get:SuppressLint("SupportAnnotationUsage")
@get:StringRes
val Int.string get() = context.getString(this)

@SuppressLint("SupportAnnotationUsage")
@Suppress("DEPRECATION")
@StringRes
fun Int.toast(int: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, int).apply {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
            val toastView = this.view // 获取 Toast 的视图
            val textView = toastView?.findViewById<TextView>(android.R.id.message)
            textView?.setTextColor(Color.WHITE)
        }
        show()
    }
}


fun Any.copy(): Boolean {
    return try {
        clipboard.value?.setPrimaryClip(ClipData.newPlainText(null, this.toString()))
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}