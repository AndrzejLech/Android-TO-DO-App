package andrzej.lech.to_do_app.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import andrzej.lech.to_do_app.R
import andrzej.lech.to_do_app.adapters.ToDoAdapter
import andrzej.lech.to_do_app.models.ToDo
import andrzej.lech.to_do_app.viewmodels.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), CreateToDoDialog.CreateToDoListener,
    ToDoAdapter.OnToDoClickListener {
    private val TAG = "MainActivity_TAG"

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val compositeDisposable = CompositeDisposable()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var toDoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        mRecyclerView = findViewById(R.id.toDoRecycler)
        val linearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.setHasFixedSize(true)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val disposable: Disposable? =
            mainActivityViewModel.getAllToDos()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { toDos ->
                    Log.d(
                        TAG,
                        "accept: Called"
                    )
                    setDataToRecyclerView(toDos)
                }


        if (disposable != null) {
            compositeDisposable.add(disposable)
        }

        mainActivityViewModel.getIsLoading()
            .observe(this, Observer<Boolean?> { aBoolean ->
                Log.d(
                    TAG,
                    "onChanged: $aBoolean"
                )
            })

        fab.setOnClickListener { openCreateToDoDialog() }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                toDoAdapter.getToDoAt(viewHolder.adapterPosition).let {
                    mainActivityViewModel.deleteToDo(it)
                }
            }
        }).attachToRecyclerView(mRecyclerView)
    }

    private fun setDataToRecyclerView(toDos: List<ToDo>) {
        toDoAdapter = ToDoAdapter(toDos)
        toDoAdapter.setItemClickListener(this)
        mRecyclerView.adapter = toDoAdapter
    }

    private fun openCreateToDoDialog() {
        val createPartyDialog = CreateToDoDialog()
        createPartyDialog.show(supportFragmentManager, "create dialog")
    }

    override fun saveNewToDo(toDo: ToDo) {
        Log.d(
            TAG,
            "saveNewToDo: " + toDo.getTitle()
        )
        mainActivityViewModel.insertToDo(toDo)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onToDoClick(toDo: ToDo) {
        Log.d(
            TAG,
            "onToDoClick: onItemClick"
        )
        moveToToDoActivity(toDo)
    }

    private fun moveToToDoActivity(toDo: ToDo) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra("title", toDo.getTitle())
        intent.putExtra("description", toDo.getDescription())
        intent.putExtra("timestamp", toDo.getTimestamp())
        intent.putExtra("state", toDo.getState())
        startActivity(intent)
    }

}