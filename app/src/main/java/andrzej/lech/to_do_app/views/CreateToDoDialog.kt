package andrzej.lech.to_do_app.views

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatDialogFragment
import andrzej.lech.to_do_app.R
import andrzej.lech.to_do_app.models.ToDo
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateToDoDialog : AppCompatDialogFragment() {
    private lateinit var mTitle: EditText
    private lateinit var mDescription: EditText
    private lateinit var mSaveButton: Button
    private lateinit var mListener: CreateToDoListener

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.main_dialog, null)

        builder.setView(view)
        builder.setCancelable(true)
        builder.setTitle(null)

        mTitle = view.findViewById(R.id.input_main_dialog_title)
        mDescription = view.findViewById(R.id.input_main_dialog_description)
        mSaveButton = view.findViewById(R.id.save_button_main_dialog)

        mSaveButton.setOnClickListener {

            val title: String = mTitle.text.toString()
            val description: String = mDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                mListener.saveNewToDo(ToDo(title, description))
                dismiss()
            }

        }
        return builder.create()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mListener = context as CreateToDoListener
    }

    interface CreateToDoListener {
        fun saveNewToDo(toDo: ToDo)
    }

}
