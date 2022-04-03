package jp.co.vegeta.core.extentions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * Created by vegeta on 2022/04/01.
 */
suspend inline fun <reified T : Any> DataStore<Preferences>.save(key: String, value: T) {
    when (T::class) {
        Int::class,
        Double::class,
        String::class,
        Boolean::class,
        Float::class,
        Long::class -> {
            edit {
                it[getKey<T>(key)] = value
            }
        }
        // Objectの場合
        else -> {
            val stringJson = Gson().toJson(value)
            edit {
                it[getKey<String>(key)] = stringJson
            }
        }
    }
}

inline fun <reified T : Any> getKey(name: String): Preferences.Key<T> {
    val key = when (T::class) {
        Int::class -> {
            intPreferencesKey(name)
        }
        Double::class -> {
            doublePreferencesKey(name)
        }
        String::class -> {
            stringPreferencesKey(name)
        }
        Boolean::class -> {
            booleanPreferencesKey(name)
        }
        Float::class -> {
            floatPreferencesKey(name)
        }
        Long::class -> {
            longPreferencesKey(name)
        }
        // Objectの場合、StringJsonで保存
        else -> stringPreferencesKey(name)
    }
    @Suppress("UNCHECKED_CAST")
    return key as Preferences.Key<T>
}

suspend inline fun <reified T : Any> DataStore<Preferences>.get(
    key: String,
    defaultValue: T? = null
): T? {
    return data.map {
        when (T::class) {
            Int::class,
            Double::class,
            String::class,
            Boolean::class,
            Float::class,
            Long::class -> {
                it[getKey<T>(key)] ?: defaultValue
            }
            // Objectの場合
            else -> {
                val jsonString = (it[getKey<T>(key)] as? String) ?: ""
                Gson().fromJson(jsonString, T::class.java)
            }
        }
    }.firstOrNull()
}

suspend inline fun <reified T : Any> DataStore<Preferences>.delete(key: String) {
    edit {
        it.remove(getKey<T>(key))
    }
}

suspend fun DataStore<Preferences>.clear() {
    edit {
        it.clear()
    }
}

suspend inline fun <reified T : Any> DataStore<Preferences>.hasKey(key: String): Boolean {
    return data.map {
        it.contains(getKey<T>(key))
    }.firstOrNull() ?: false
}