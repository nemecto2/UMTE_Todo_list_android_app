package cz.uhk.umte.ui.todo

import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.ui.base.BaseViewModel

class TodoAddVM (
    private val todoDao: TodoDao,
) : BaseViewModel() {


    fun addOrUpdateTodo(todo: TodoEntity) {
        launch {
            todoDao.insertOrUpdate(todo)
        }
    }
}