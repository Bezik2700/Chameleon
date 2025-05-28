package igor.second.clickapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(private val context: Context) {

    suspend fun saveSettings(settingData: SettingData) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("userSpeedValue")] = settingData.userSpeedValue
            pref[floatPreferencesKey("userCoinValue")] = settingData.userCoinValue
            pref[stringPreferencesKey("userRealCoins")] = settingData.userRealCoins
        }
    }

    fun getSettings() = context.dataStore.data.map { pref ->
        return@map SettingData(
            pref[intPreferencesKey("userSpeedValue")] ?: 10,
            pref[floatPreferencesKey("userCoinValue")] ?: 0f,
            pref[stringPreferencesKey("userRealCoins")] ?: "0"
        )
    }
}

data class SettingData(
    val userSpeedValue: Int,
    val userCoinValue: Float,
    val userRealCoins: String
)