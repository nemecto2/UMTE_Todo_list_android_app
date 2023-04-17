package cz.uhk.umte.ui.screens.todo_list

import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.ui.base.BaseViewModel

class TodoListVM(
    private val todoDao: TodoDao,
) : BaseViewModel() {
    // Vytažení všech TODOS z databáze
    val todos = todoDao.selectAllWithoutDate()


    // Funkce pro screen
    fun handleTodoCheck(todo: TodoEntity) {
        launch {
            todoDao.insertOrUpdate(
                todo.copy(
                    checked = todo.checked.not()
                )
            )
        }
    }
}