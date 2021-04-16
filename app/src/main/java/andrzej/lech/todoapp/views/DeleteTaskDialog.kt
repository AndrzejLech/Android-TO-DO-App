package andrzej.lech.todoapp.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDialogFragment
import andrzej.lech.todoapp.R
import andrzej.lech.todoapp.models.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteTaskDialog(var task: Task): AppCompatDialogFragment() {
    lateinit var mListener: DeleteTaskListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val inflater: LayoutInflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_delete_task, null)

        builder.setView(view)
        builder.setCancelable(true)
        builder.setTitle(null)

        val mConfirmButton: Button = view.findViewById(R.id.deleteDialogConfirmButton)
        val mDenyButton: Button = view.findViewById(R.id.deleteDialogDenyButton)

        mConfirmButton.setOnClickListener{
            mListener.deleteTask(task)
            dismiss()
        }

        mDenyButton.setOnClickListener{
            dismiss()
        }

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener = context as DeleteTaskListener
    }

    interface DeleteTaskListener{
        fun deleteTask(task: Task)
    }
}