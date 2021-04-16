package andrzej.lech.todoapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.util.concurrent.TimeUnit

@Entity(tableName = "task_table")
class Task(title: String, description: String) {

    @PrimaryKey(autoGenerate = true)
    private var uid: Int = 0
    private var title: String
    private var description: String
    private var state: Boolean = false
    private var timestamp: String

    init {
        this.title = title
        this.description = description
        this.state = false
        this.timestamp = Calendar.getInstance().time.toString()
    }

    fun setUid(uid: Int) {
        this.uid = uid
    }

    fun getUid(): Int {
        return this.uid
    }

    fun getTitle(): String {
        return this.title
    }

    fun getDescription(): String {
        return this.description
    }

    fun setTimestamp(timestamp: String) {
        this.timestamp = timestamp
    }

    fun getTimestamp(): String {
        return timestamp
    }

    fun setState(state: Boolean) {
        this.state = state
    }

    fun getState(): Boolean {
        return state
    }
}