package jp.co.vegeta.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by vegeta on 2022/04/01.
 */

@Singleton
class LocalDataStore @Inject constructor(@ApplicationContext context: Context) {
    private val Context.dataStorePreferences by preferencesDataStore("local_data_store_preferences")
    val dataStore: DataStore<Preferences> = context.dataStorePreferences
}