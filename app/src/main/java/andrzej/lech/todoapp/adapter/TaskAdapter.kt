package andrzej.lech.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import andrzej.lech.todoapp.R
import andrzej.lech.todoapp.models.Task

interface OnTaskClickListener {
    fun onTaskClick(task: Task)
}

class TaskAdapter(taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.AdapterViewHolder>() {
    var taskList: List<Task> = emptyList()
    lateinit var onTaskClickListener: OnTaskClickListener

    init {
        this.taskList = taskList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return AdapterViewHolder(view, onTaskClickListener)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val singleTask: Task = taskList[position]
        holder.setupTitle(singleTask.getTitle())
        holder.setupState(singleTask.getState())
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class AdapterViewHolder(
        @NonNull itemView: View,
        onTaskClickListener: OnTaskClickListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val title: TextView
        private val state: ImageView
        private val cardView: CardView
        var onTaskClickListener: OnTaskClickListener

        init {
            this.onTaskClickListener = onTaskClickListener
            title = itemView.findViewById(R.id.itemTaskTitle)
            state = itemView.findViewById(R.id.itemTaskState)
            cardView = itemView.findViewById(R.id.cardView)
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position: Int = layoutPosition
            val currentTask = taskList[position]
            onTaskClickListener.onTaskClick(currentTask)
        }

        internal fun setupTitle(title: String){
            this.title.text = title
        }

        internal fun setupState(boolean: Boolean) {
            if (boolean) {
                state.visibility = View.VISIBLE
            } else {
                state.visibility = View.INVISIBLE
            }
        }
    }

    fun getTaskAt(position: Int): Task {
        val task: Task = taskList[position]
        task.setUid(taskList[position].getUid())
        return task
    }

    fun setItemClickListener(onTaskClickListener: OnTaskClickListener) {
        this.onTaskClickListener = onTaskClickListener
    }
}