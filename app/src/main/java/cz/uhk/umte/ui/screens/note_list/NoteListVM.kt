package cz.uhk.umte.ui.screens.note_list

import cz.uhk.umte.db.dao.NoteDao
import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.ui.base.BaseViewModel

class NoteListVM(
    private val noteDao: NoteDao,

    val todoDao: TodoDao,
) : BaseViewModel() {
    var notes = noteDao.selectAll()
    var todos = todoDao.selectToday()
}