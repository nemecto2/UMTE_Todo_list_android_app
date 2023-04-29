package cz.uhk.umte.ui.screens.note_list

import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.ui.base.BaseViewModel

class NoteListVM(
    val todoDao: TodoDao,
) : BaseViewModel() {
    var todos = todoDao.selectToday()
}