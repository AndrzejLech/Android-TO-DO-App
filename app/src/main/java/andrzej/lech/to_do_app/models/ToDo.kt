package andrzej.lech.to_do_app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
class ToDo(
    @PrimaryKey(autoGenerate = true)
    private val uid: Int
) {

    private var title: String = ""
    private var description: String = ""
    private var timeStamp: String = (System.currentTimeMillis()/1000).toString()
    private var state: Boolean = false

    fun getTitle(): String {
        return this.title
    }

    fun setTitle(title: String){
        this.title = title
    }

    fun setDescription(description: String){
        this.description = description
    }

    fun getDescription(): String {
        return this.description
    }
}

