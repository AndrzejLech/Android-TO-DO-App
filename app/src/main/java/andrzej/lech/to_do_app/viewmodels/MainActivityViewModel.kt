package andrzej.lech.to_do_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import andrzej.lech.to_do_app.models.ToDo
import andrzej.lech.to_do_app.repository.ToDoRepository
import io.reactivex.Flowable


class MainActivityViewModel(application: Application) : AndroidViewModel(application){

    private val toDoRepository = ToDoRepository()

    fun getAllToDos(): Flowable<List<ToDo>>? {
        return toDoRepository.getAllToDos()
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return toDoRepository.getIsLoading()
    }

    fun insertToDo(toDo: ToDo) {
        toDoRepository.insertToDo(toDo)
    }

    fun updateToDo(toDo: ToDo) {
    toDoRepository.updateToDo(toDo)
    }

    fun deleteToDo(toDo: ToDo) {
    toDoRepository.deleteToDo(toDo)
    }

}