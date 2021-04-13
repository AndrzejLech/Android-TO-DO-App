package andrzej.lech.to_do_app.database.dao

import androidx.room.*

import io.reactivex.Flowable;
import andrzej.lech.to_do_app.models.ToDo

@Dao
interface ToDoDao {
    @Insert
    fun insert(toDo: ToDo)

    @Update
    fun update(toDo: ToDo)

    @Delete
    fun delete(toDo: ToDo)

    @Query("SELECT * FROM todo_table")
    fun getAllToDos(): Flowable<List<ToDo>>
}