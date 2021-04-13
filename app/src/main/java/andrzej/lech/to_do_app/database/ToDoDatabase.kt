package andrzej.lech.to_do_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import andrzej.lech.to_do_app.database.dao.ToDoDao
import andrzej.lech.to_do_app.models.ToDo


@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var instance: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase? {
            if (instance == null) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java, "todo_database"
                ).build()
                this.instance = instance
            }
            return instance
        }
    }
}
