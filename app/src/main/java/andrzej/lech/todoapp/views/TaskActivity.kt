package andrzej.lech.todoapp.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import andrzej.lech.todoapp.R
import andrzej.lech.todoapp.adapter.TaskAdapter
import andrzej.lech.todoapp.models.Task
import andrzej.lech.todoapp.viewmodels.TaskActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TaskActivity : AppCompatActivity(),
    CreateTaskDialog.CreateTaskListener,
    DeleteTaskDialog.DeleteTaskListener,
    TaskAdapter.OnTaskClickListener {

    private val tag: String = "MainActivity_TAG"

    lateinit var taskActivityViewModel: TaskActivityViewModel
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var mRecyclerView: RecyclerView
    lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_task)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        mRecyclerView = findViewById(R.id.taskRecycler)
        val linearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.setHasFixedSize(true)

        taskActivityViewModel = ViewModelProvider(this).get(TaskActivityViewModel::class.java)

        val disposable: Disposable = taskActivityViewModel.getAllTask().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tasks ->
                run {
                    Log.d(tag, "accept: Called")
                    setDataToRecyclerView(tasks)
                }
            }

        compositeDisposable.add(disposable)

        fab.setOnClickListener {
            openCreateTaskDialog()
        }


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                @NonNull recyclerView: RecyclerView,
                @NonNull viewHolder: RecyclerView.ViewHolder,
                @NonNull target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                taskAdapter.getTaskAt(viewHolder.layoutPosition).let {
                    if (!it.getState()) {
                        openDeleteTaskDialog(it)
                        taskAdapter.notifyDataSetChanged()
                    } else {
                        taskActivityViewModel.deleteTask(it)
                    }
                }
            }
        }).attachToRecyclerView(mRecyclerView)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                @NonNull recyclerView: RecyclerView,
                @NonNull viewHolder: RecyclerView.ViewHolder,
                @NonNull target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                taskAdapter.getTaskAt(viewHolder.layoutPosition).let {
                    it.setState(!it.getState())
                    taskActivityViewModel.updateTask(it)
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }).attachToRecyclerView(mRecyclerView)
    }

    private fun setDataToRecyclerView(tasks: List<Task>) {
        taskAdapter = TaskAdapter(tasks)
        taskAdapter.setItemClickListener(this)
        mRecyclerView.adapter = taskAdapter
    }

    private fun openCreateTaskDialog() {
        val createPartyDialog = CreateTaskDialog()
        createPartyDialog.show(supportFragmentManager, "create dialog")
    }

    private fun openDeleteTaskDialog(task: Task) {
        val deleteTaskDialog = DeleteTaskDialog(task)
        deleteTaskDialog.show(supportFragmentManager, "delete dialog")
    }

    override fun saveNewTask(task: Task) {
        Log.d(tag, "saveNewTask: " + task.getTitle())
        taskActivityViewModel.insertTask(task)
    }

    override fun deleteTask(task: Task) {
        Log.d(tag, "DeleteTask: " + task.getTitle())
        taskActivityViewModel.deleteTask(task)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onTaskClick(task: Task) {
        Log.d(tag, "onTaskClick: onItemClick")
        moveToDetailsActivity(task)
    }

    private fun moveToDetailsActivity(task: Task) {
        val intent = Intent(this@TaskActivity, DetailActivity::class.java)

        intent.putExtra("title", task.getTitle())
        intent.putExtra("description", task.getDescription())
        intent.putExtra("timestamp", task.getTimestamp())
        intent.putExtra("state", task.getState())

        startActivity(intent)
    }
}