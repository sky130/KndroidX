@file:Suppress("UNCHECKED_CAST")

package kndroidx.preference

import android.content.Context
import android.content.SharedPreferences
import kndroidx.KndroidX.context
import kndroidx.kndroidx
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

open class PreferencesX(private val sp: SharedPreferences) {

    constructor(name: String, mode: Int = Context.MODE_PRIVATE) : this(
        context.getSharedPreferences(
            name, mode
        )
    )

    operator fun getValue(thisRef: Any?, property: KProperty<*>): SharedPreferences {
        return sp
    }

    fun intPreference(name: String, defaultValue: Int) = Preference(sp, name, defaultValue)

    fun stringPreference(name: String, defaultValue: String) = Preference(sp, name, defaultValue)

    fun longPreference(name: String, defaultValue: Long) = Preference(sp, name, defaultValue)

    fun floatPreference(name: String, defaultValue: Float) = Preference(sp, name, defaultValue)

    fun setPreference(name: String, defaultValue: Set<String>) = Preference(sp, name, defaultValue)

    fun booleanPreference(name: String, defaultValue: Boolean) = Preference(sp, name, defaultValue)

}

class Preference<T>(
    private val sp: SharedPreferences,
    private val name: String,
    private val defaultValue: T,
) {
    val state: Flow<T> get() = _state
    private val _state: MutableStateFlow<T>

    init {
        _state = MutableStateFlow(getValue())
    }

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