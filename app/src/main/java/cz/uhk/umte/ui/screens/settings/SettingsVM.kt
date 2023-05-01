package cz.uhk.umte.ui.screens.settings

import DataStorage
import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.ui.base.BaseViewModel

class SettingsVM(
    val todoDao: TodoDao,
    private val dataStorage: DataStorage,
) : BaseViewModel() {
    var todos = todoDao.selectToday()
    var notificationTimeFlow = dataStorage.notificationTimeFlow

    fun setNotificationTime(time: String) {
        launch {
            dataStorage.setNotificationTime(time)
        }
    }
}