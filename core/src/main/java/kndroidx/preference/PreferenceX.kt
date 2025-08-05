@file:Suppress("UNCHECKED_CAST")

package kndroidx.preference

import android.content.Context
import android.content.SharedPreferences
import kndroidx.KndroidX.context
import kndroidx.kndroidx
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

@Deprecated("turn to use Setting")
open class PreferencesX(private val sp: SharedPreferences) {

    constructor(name: String, mode: Int = Context.MODE_PRIVATE) : this(
        context.getSharedPreferences(
            name, mode
        )
    )

    operator fun getValue(thisRef: Any?, property: KProperty<*>): SharedPreferences {
        return sp
    }

    fun int(name: String, defaultValue: Int) = Preference(sp, name, defaultValue)

    fun string(name: String, defaultValue: String) = Preference(sp, name, defaultValue)

    fun long(name: String, defaultValue: Long) = Preference(sp, name, defaultValue)

    fun float(name: String, defaultValue: Float) = Preference(sp, name, defaultValue)

    fun set(name: String, defaultValue: Set<String>) = Preference(sp, name, defaultValue)

    fun boolean(name: String, defaultValue: Boolean) = Preference(sp, name, defaultValue)

}

class Preference<T>(
    private val sp: SharedPreferences,
    private val name: String,
    private val defaultValue: T,
) {
    private val _state: MutableStateFlow<T> =  MutableStateFlow(getValue())
    val state = _state.asStateFlow()

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = getValue()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = setValue(value)

    fun getValue(): T {
        with(sp) {
            return when (defaultValue) {
                is Int -> getInt(name, defaultValue) as T
                is String -> getString(name, defaultValue) as T
                is Float -> getFloat(name, defaultValue) as T
                is Boolean -> getBoolean(name, defaultValue) as T
                is Long -> getLong(name, defaultValue) as T
                is Set<*> -> getStringSet(name, defaultValue as Set<String>) as T
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    fun setValue(value: T) {
        with(sp.edit()) {
            when (value) {
                is Int -> putInt(name, value)
                is String -> putString(name, value)
                is Float -> putFloat(name, value)
                is Boolean -> putBoolean(name, value)
                is Long -> putLong(name, value)
                is Set<*> -> putStringSet(name, value as Set<String>)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
            kndroidx {
                scope.launch {
                    _state.emit(value)
                }
            }
        }
    }

}


fun preferencesX(name: String, mode: Int = Context.MODE_PRIVATE) = PreferencesX(name, mode)