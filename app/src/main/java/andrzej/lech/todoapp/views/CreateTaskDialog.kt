package andrzej.lech.todoapp.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatDialogFragment
import andrzej.lech.todoapp.R
import andrzej.lech.todoapp.models.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder

interface CreateTaskListener {
    fun saveNewTask(task: Task)
}

class CreateTaskDialog : AppCompatDialogFragment() {
    lateinit var mTitle: EditText
    lateinit var mDescription: EditText
    lateinit var mSaveButton: Button
    lateinit var mListener: CreateTaskListener

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val inflater: LayoutInflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_create_task, null)

        builder.setView(view)
        builder.setCancelable(true)
        builder.setTitle(null)

        mTitle = view.findViewById(R.id.dialogCreateTaskTitle)
        mDescription = view.findViewById(R.id.dialogCreateTaskDescription)
        mSaveButton = view.findViewById(R.id.dialogCreateTaskSaveButton)

        mSaveButton.setOnClickListener {
            val taskTitle = mTitle.text.toString()
            val taskDescription = mDescription.text.toString()

            if (taskDescription.isNotEmpty() && taskTitle.isNotEmpty()) {
                val task = Task(taskTitle, taskDescription)
                mListener.saveNewTask(task)
                dismiss()
            }
        }

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as CreateTaskListener
    }
}