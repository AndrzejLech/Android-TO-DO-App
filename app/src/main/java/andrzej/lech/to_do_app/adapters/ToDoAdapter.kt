package andrzej.lech.to_do_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import andrzej.lech.to_do_app.R
import andrzej.lech.to_do_app.models.ToDo
import com.google.android.material.card.MaterialCardView
import org.jetbrains.annotations.NotNull

class ToDoAdapter(toDoList: List<ToDo>) :
    RecyclerView.Adapter<ToDoAdapter.AdapterViewHolder>() {
    lateinit var toDoList: List<ToDo>
    private lateinit var onToDoClickListener: OnToDoClickListener

    init {
        this.toDoList = toDoList
    }

    fun setItemClickListener(onToDoClickListener: OnToDoClickListener) {
        this.onToDoClickListener = onToDoClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, null)
        return AdapterViewHolder(view, onToDoClickListener)
    }

    override fun onBindViewHolder(
        @NotNull holder: AdapterViewHolder,
        position: Int
    ) {
        val singleToDo: ToDo = toDoList[position]
        holder.mTitle.text = singleToDo.getTitle()
        holder.mCheckBoxState.isChecked = singleToDo.getState()
    }

    fun getToDoAt(position: Int): ToDo {
        val toDo: ToDo = toDoList[position]
        toDo.setUid(toDoList[position].getUid())
        return toDo
    }



    override fun getItemCount(): Int {
        return toDoList.size
    }

    inner class AdapterViewHolder(@NonNull itemView: View, onToDoClickListener: OnToDoClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mTitle: TextView
        val mCheckBoxState: CheckBox
        private val mCardView: MaterialCardView
        private var onToDoClickListener: OnToDoClickListener

        init {
            this.onToDoClickListener = onToDoClickListener
            mTitle = itemView.findViewById(R.id.toDoTitle)
            mCheckBoxState = itemView.findViewById(R.id.checkBoxState)
            mCardView = itemView.findViewById(R.id.cardView)
            mCardView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position: Int = adapterPosition
            val currentToDo: ToDo = toDoList[position]
            onToDoClickListener.onToDoClick(currentToDo)
        }


    }

    interface OnToDoClickListener {
        fun onToDoClick(toDo: ToDo)
    }
}