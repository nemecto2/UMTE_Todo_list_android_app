package cz.uhk.umte.di

import androidx.room.Room
import cz.uhk.umte.db.AppDatabase
import cz.uhk.umte.ui.todo.TodoAddVM
import cz.uhk.umte.ui.todos_view.TodosAllVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModules by lazy { listOf(dataModule, uiModule) }

val dataModule = module {
    db()
}

val uiModule = module {
    viewModel { TodosAllVM(/*get()*/) } // TODO Zde je chyba?
    viewModel { TodoAddVM(get()) }
}

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
    single { get<AppDatabase>().noteDao() }
}

