@file:Suppress("UNCHECKED_CAST")

package kndroidx.setting

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.asLiveData
import kndroidx.KndroidX.context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KProperty

open class Setting(private val sp: SharedPreferences) {

    constructor(name: String, mode: Int = Context.MODE_PRIVATE) : this(
        context.getSharedPreferences(
            name, mode
        )
    )

    operator fun getValue(thisRef: Any?, property: KProperty<*>): SharedPreferences {
        return sp
    }

    fun int(name: String, defaultValue: Int) = SettingItem(sp, name, defaultValue)

    fun string(name: String, defaultValue: String) = SettingItem(sp, name, defaultValue)

    fun long(name: String, defaultValue: Long) = SettingItem(sp, name, defaultValue)

    fun float(name: String, defaultValue: Float) = SettingItem(sp, name, defaultValue)

    fun set(name: String, defaultValue: Set<String>) = SettingItem(sp, name, defaultValue)

    fun boolean(name: String, defaultValue: Boolean) = SettingItem(sp, name, defaultValue)

    fun <T> enum(
        name: String,
        defaultValue: T,
        encode: (T) -> String,
        decode: (String) -> T,
    ) = SettingEnumItem(sp, name, defaultValue, encode, decode)


}

inline fun <reified T> SettingItem<T>.onEach(noinline action: suspend (T) -> Unit) = this.state.onEach(action)

inline fun <reified T> SettingEnumItem<T>.onEach(noinline action: suspend (T) -> Unit) = this.state.onEach(action)

class SettingItem<T>(
    private val sp: SharedPreferences,
    private val name: String,
    private val defaultValue: T,
) {

    private val _state: MutableStateFlow<T> = MutableStateFlow(getValue())
    val state = _state.asStateFlow()
    val liveData = state.asLiveData()

    var value: T
        get() = _getValue()
        set(value) = _setValue(value)

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = _getValue()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = _setValue(value)

    private fun _getValue(): T {
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

    private fun _setValue(value: T) {
        sp.edit {
            when (value) {
                is Int -> putInt(name, value)
                is String -> putString(name, value)
                is Float -> putFloat(name, value)
                is Boolean -> putBoolean(name, value)
                is Long -> putLong(name, value)
                is Set<*> -> putStringSet(name, value as Set<String>)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            _state.value = value
        }
    }
}

class SettingEnumItem<T>(
    private val sp: SharedPreferences,
    private val name: String,
    private val defaultValue: T,
    private val encode: (T) -> String,
    private val decode: (String) -> T,
) {
    private val _state: MutableStateFlow<T> = MutableStateFlow(defaultValue)
    val state = _state.asStateFlow()
    val liveData = state.asLiveData()

    var value: T
        get() = _getValue()
        set(value) = _setValue(value)

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = _getValue()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = _setValue(value)

    private fun _getValue(): T {
        with(sp) {
            return decode(getString(name, encode(defaultValue))!!)
        }
    }

    private fun _setValue(value: T) {
        sp.edit {
            putString(name, encode(value))
            _state.value = value
        }
    }
}
