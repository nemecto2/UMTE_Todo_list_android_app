package cz.uhk.umte.ui.screens.todo_detail

import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.ui.base.BaseViewModel

class TodoDetailVM(
    private val todoDao: TodoDao,
    private val todoId: Long,
): BaseViewModel() {
    var todo = todoDao.selectById(todoId)

    fun addOrUpdate(
        todoEntity: TodoEntity,
        todo: String, date: String?,
        note: String?,
        imageUri: String?
    ) {
        launch {
            todoDao.insertOrUpdate(
                todoEntity.copy(
                    text = todo,
                    date = date,
                    imageUri = imageUri,
                    note = if (note.isNullOrEmpty()) null else note
                )
            )
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        launch {
            todoDao.delete(todo)
        }
    }
}