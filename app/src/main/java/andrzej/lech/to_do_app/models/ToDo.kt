package andrzej.lech.to_do_app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    private var uid: Int
) {

    private var title: String = ""
    private var description: String = ""
    private var timestamp: String = (System.currentTimeMillis() / 1000).toString()
    private var state: Boolean = false

    constructor(title: String, description: String) : this(0) {
        this.title = title
        this.description = description
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

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getDescription(): String {
        return this.description
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

