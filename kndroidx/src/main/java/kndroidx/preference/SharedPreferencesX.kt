package kndroidx.preference

import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class SharedPreferencesX(val sp: SharedPreferences) {

    inline fun edit(block: EditorX.() -> Unit) {
        EditorX(this).apply {
            block()
            apply()
        }
    }

    inner class EditorX(spx: SharedPreferencesX) {

        val editor: SharedPreferences.Editor = spx.sp.edit()

        fun apply() {
            editor.apply()
        }

        inline fun <reified T> put(name: String, value: T) {
            when (value) {
                is Boolean -> editor.putBoolean(name, value)

                is String -> editor.putString(name, value)

                is Int -> editor.putInt(name, value)

                is Long -> editor.putLong(name, value)

                is Float -> editor.putFloat(name, value)

                is Set<*> -> editor.putStringSet(name, value as Set<String>)
            }
        }
    }

    inline fun <reified T> put(name: String, value: T) {
        when (value) {
            is Boolean -> sp.edit().putBoolean(name, value).apply()

            is String -> sp.edit().putString(name, value).apply()

            is Int -> sp.edit().putInt(name, value).apply()

            is Long -> sp.edit().putLong(name, value).apply()

            is Float -> sp.edit().putFloat(name, value).apply()

            is Set<*> -> sp.edit().putStringSet(name, value as Set<String>).apply()
        }
    }

    inline fun <reified T> get(name: String, defaultValue: T): T {
        return when (T::class) {
            Boolean::class -> sp.getBoolean(name, defaultValue as? Boolean ?: false) as T

            String::class -> sp.getString(name, defaultValue as? String ?: "") as T

            Int::class -> sp.getInt(name, defaultValue as? Int ?: 0) as T

            Long::class -> sp.getLong(name, defaultValue as? Long ?: 0L) as T

            Float::class -> sp.getFloat(name, defaultValue as? Float ?: 0F) as T

            Set::class -> sp.getStringSet(name, defaultValue as? Set<String>) as T

            else -> throw IllegalArgumentException("This type of class is not supported.")
        }
    }


}