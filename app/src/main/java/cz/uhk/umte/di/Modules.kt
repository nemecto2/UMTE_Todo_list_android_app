package cz.uhk.umte.di

import DataStorage
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import cz.uhk.umte.db.AppDatabase
import cz.uhk.umte.ui.LayoutVM
import cz.uhk.umte.ui.screens.settings.SettingsVM
import cz.uhk.umte.ui.screens.todo_add.TodoAddVM
import cz.uhk.umte.ui.screens.todo_detail.TodoDetailVM
import cz.uhk.umte.ui.screens.todo_list.TodoListVM
import cz.uhk.umte.ui.screens.todo_list_date.TodoListDateVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModules by lazy { listOf(dataModule, uiModule) }

val dataModule = module {
    db()
    dataStorage()
}

val uiModule = module {
    viewModel { TodoListDateVM(get()) }
    viewModel { TodoListVM(get()) }
    viewModel { TodoAddVM(get()) }
    viewModel { (todoId: Long) ->  TodoDetailVM(todoDao = get(), todoId = todoId) }
    viewModel { SettingsVM(get(), get()) }
    viewModel { LayoutVM(get()) }
}

// ROOM
private fun Module.db() {
    // DB
    single {
        Room
            .databaseBuilder(
                context = androidApplication(),
                klass = AppDatabase::class.java,
                name = AppDatabase.Name,
            )
            .build()
    }
    // Dao
//    single { get<AppDatabase>().noteDao() }
    single { get<AppDatabase>().todoDao() }
}

// DATASTORE
private fun Module.dataStorage() {
    single { DataStorage(androidApplication().dataStore) }
}

private const val DataStoreName = "Settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DataStoreName)




