package andrzej.lech.todoapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import andrzej.lech.todoapp.database.TaskDatabase
import andrzej.lech.todoapp.database.dao.TaskDao
import andrzej.lech.todoapp.models.Task
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TaskRepository(application: Application) {
    private val TAG = "TaskRepository"
    var taskDao: TaskDao

    init {
        val taskDatabase: TaskDatabase = TaskDatabase.getInstance(application)
        taskDao = taskDatabase.taskDao()
    }

    fun getAllTasks(): Flowable<List<Task>> {
        return taskDao.getAllToDos()
    }

    fun insertTask(task: Task) {
        Completable.fromAction {
            taskDao.insert(task)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

    fun updateTask(task: Task) {
        Completable.fromAction {
            taskDao.update(task)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }


    fun deleteTask(task: Task) {
        Completable.fromAction {
              taskDao.delete(task)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "Delete task onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "Delete task onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "Delete task onError: " + e.message)
                }
            })
    }
}