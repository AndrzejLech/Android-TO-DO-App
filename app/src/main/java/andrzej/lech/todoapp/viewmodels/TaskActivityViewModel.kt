package andrzej.lech.todoapp.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import andrzej.lech.todoapp.models.Task
import andrzej.lech.todoapp.repository.TaskRepository
import io.reactivex.Flowable

class TaskActivityViewModel(@NonNull application: Application) : AndroidViewModel(application) {
    var taskRepository: TaskRepository

    init {
        taskRepository = TaskRepository(application)
    }

    fun getAllTask(): Flowable<List<Task>> {
        return taskRepository.getAllTasks()
    }

    fun insertTask(task: Task) {
        return taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task) {
        return taskRepository.deleteTask(task)
    }

    fun updateTask(task: Task) {
        return taskRepository.updateTask(task)
    }

}