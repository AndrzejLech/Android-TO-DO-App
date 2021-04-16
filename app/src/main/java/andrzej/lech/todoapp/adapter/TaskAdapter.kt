package andrzej.lech.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import andrzej.lech.todoapp.R
import andrzej.lech.todoapp.models.Task

class TaskAdapter(taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.AdapterViewHolder>() {

    var taskList: List<Task> = emptyList()
    lateinit var onTaskClickListener: OnTaskClickListener

    init {
        this.taskList = taskList
    }

    fun setItemClickListener(onTaskClickListener: OnTaskClickListener) {
        this.onTaskClickListener = onTaskClickListener
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return AdapterViewHolder(view, onTaskClickListener)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val singleTask: Task = taskList[position]
        holder.mTitle.text = singleTask.getTitle()
        holder.mState.isChecked = singleTask.getState()
    }

    fun getTaskAt(position: Int): Task {
        val task: Task = taskList[position]
        task.setUid(taskList[position].getUid())
        return task
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

   inner class AdapterViewHolder(@NonNull itemView: View, onTaskClickListener: OnTaskClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mTitle: TextView
        val mState: CheckBox
        val mCardView: CardView
        var onTaskClickListener: OnTaskClickListener

        init {
            this.onTaskClickListener = onTaskClickListener
            mTitle = itemView.findViewById(R.id.itemTaskTitle)
            mState = itemView.findViewById(R.id.itemTaskStateCheckBox)
            mCardView = itemView.findViewById(R.id.cardView)
            mCardView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position: Int = adapterPosition
            val currentTask = taskList[position]
            onTaskClickListener.onTaskClick(currentTask)
        }
    }

    interface OnTaskClickListener {
        fun onTaskClick(task: Task)
    }

}