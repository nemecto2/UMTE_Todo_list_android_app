package cz.uhk.umte.ui.screens.todo_detail

import cz.uhk.umte.db.dao.NoteDao
import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.NoteEntity
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.ui.base.BaseViewModel

class TodoDetailVM(
    private val todoDao: TodoDao,
    private val noteDao: NoteDao,
    private val todoId: Long,
): BaseViewModel() {
    var todo = todoDao.selectById(todoId)
    var note = noteDao.selectByTodoId(todoId)

    fun addOrUpdate(todoEntity: TodoEntity, noteEntity: NoteEntity, todo: String, date: String?, note: String, imageUri: String?) {
        launch {
            todoDao.insertOrUpdate(
                todoEntity.copy(
                    text = todo,
                    date = date,
                    imageUri = imageUri,
                )
            )

            // Delete note
            if (note == "") {
                noteDao.delete(noteEntity)
            // Add or update note
            } else {
                noteDao.insertOrUpdate(
                    noteEntity.copy(
                        text = note
                    )
                )
            }
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        launch {
            todoDao.delete(todo)
        }
    }
}