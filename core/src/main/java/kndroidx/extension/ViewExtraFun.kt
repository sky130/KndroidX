package kndroidx.extension

import android.widget.TextView
import kndroidx.KndroidX.context

operator fun TextView.compareTo(any: Any): Int {
    text = any.toString()
    return 0
}

inline val Double.dp: Int
    get() = run {
        return toFloat().dp
    }
inline val Int.dp: Int
    get() = run {
        return toFloat().dp
    }

inline val Float.dp: Int
    get() = run {
        val scale: Float = context.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }