package cz.uhk.umte.ui.screens.note_list

import cz.uhk.umte.db.dao.NoteDao
import cz.uhk.umte.ui.base.BaseViewModel

class NoteListVM(
    private val noteDao: NoteDao,
) : BaseViewModel() {
    var notes = noteDao.selectAll()
}