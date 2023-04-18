package cz.uhk.umte.di

import androidx.room.Room
import cz.uhk.umte.db.AppDatabase
import cz.uhk.umte.ui.screens.note_list.NoteListVM
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
}

val uiModule = module {
    viewModel { TodoListDateVM(get()) }
    viewModel { TodoListVM(get()) }
    viewModel { TodoAddVM(get()) }
    viewModel { (todoId: Long) ->  TodoDetailVM(todoDao = get(), noteDao = get(), todoId = todoId) }
    viewModel { NoteListVM(get(), get()) }  // TODO druhý get smazat
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
    single { get<AppDatabase>().todoDao() }
}

