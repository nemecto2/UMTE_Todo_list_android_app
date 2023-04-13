package cz.uhk.umte.ui.todo_detail

import cz.uhk.umte.db.dao.NoteDao
import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class TodoDetailVM(
    private val todoDao: TodoDao,
    private val noteDao: NoteDao,
    private val todoId: Long,
): BaseViewModel() {
    var todo = todoDao.selectById(todoId)

    fun deleteTodo(todo: TodoEntity) {
        launch {
            todoDao.delete(todo)
        }
    }
}