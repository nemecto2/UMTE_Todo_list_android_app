package cz.uhk.umte.db.dao

import androidx.room.*
import cz.uhk.umte.db.entities.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)

//    @Query("SELECT id, text, checked FROM NoteEntity WHERE id=:id")
//    fun selectById(id: Long): Flow<NoteEntity>
}