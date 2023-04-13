package cz.uhk.umte.ui.todos_view

import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.ui.base.BaseViewModel


class TodosDateVM(
    private val todoDao: TodoDao,
) : BaseViewModel() {
    // Vytažení všech TODOS z databáze
//    val todos = todoDao.selectAllFollowing()
//    val todos = todoDao.selectAllWithoutDate()
    val todos = todoDao.selectAll()

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

//    fun handleNavigateDetail(todo: TodoEntity) {
//
//    }
}