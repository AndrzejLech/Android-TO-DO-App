package andrzej.lech.to_do_app.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import andrzej.lech.to_do_app.database.ToDoDatabase
import andrzej.lech.to_do_app.database.dao.ToDoDao
import andrzej.lech.to_do_app.models.ToDo
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers


class ToDoRepository {
    private val TAG = "ToDoRepository"

    private var toDoDao: ToDoDao? = null
    private var allToDos: Flowable<List<ToDo>>? = null
    var isLoading = MutableLiveData<Boolean>()

    fun constructor(application: Application?) {
        val toDoDatabase: ToDoDatabase? = application?.let { ToDoDatabase.getInstance(it) }
        toDoDao = toDoDatabase?.toDoDao()
    }

    fun getAllToDos(): Flowable<List<ToDo>>? {
        return toDoDao?.getAllToDos()
    }

    fun insertToDo(toDo: ToDo?) {
        isLoading.value = true
        Completable.fromAction { Action(){
            toDo?.let { toDoDao?.insert(it) }
        } }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

    fun updateToDo(toDo: ToDo?) {
        Completable.fromAction { toDo?.let { toDoDao?.update(it) } }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: Called")
                }
            })
    }

    fun deleteToDo(toDo: ToDo) {
        isLoading.value = true
        Completable.fromAction { toDoDao?.delete(toDo) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

}