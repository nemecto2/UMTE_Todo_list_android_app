import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorage(
    private val dataStore: DataStore<Preferences>,
) {

    // Gettery - Flow a suspend
    val notificationTimeFlow: Flow<String?> = dataStore.data
        .map { preferences -> preferences[NotificationTimeKey] }


    suspend fun setNotificationTime(value: String) = dataStore.edit { preferences ->
        preferences[NotificationTimeKey] = value
    }

    companion object {
        private val NotificationTimeKey = stringPreferencesKey("NotificationTime")
    }
}
