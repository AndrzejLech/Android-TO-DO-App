package andrzej.lech.todoapp.database.dao

import androidx.room.*
import andrzej.lech.todoapp.models.Task
import io.reactivex.Flowable

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllToDos(): Flowable<List<Task>>
}