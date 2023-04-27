package cz.uhk.umte.ui

import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.ui.base.BaseViewModel

class LayoutVM (
    val todoDao: TodoDao
) : BaseViewModel()