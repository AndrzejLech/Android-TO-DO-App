package andrzej.lech.to_do_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import andrzej.lech.to_do_app.models.ToDo
import andrzej.lech.to_do_app.repository.ToDoRepository
import io.reactivex.Flowable


class MainActivityViewModel(application: Application) : AndroidViewModel(application){

    private val toDoRepository = ToDoRepository()

    //Get all Party
    fun getAllToDos(): Flowable<List<ToDo>>? {
        return toDoRepository.getAllToDos()
    }

    //Get Loading State
    fun getIsLoading(): MutableLiveData<Boolean?>? {
        return toDoRepository.isLoading.value as MutableLiveData<Boolean?>?
    }

    //Insert Party
    fun insert(party: Party?) {
        partyRepository.insertParty(party)
    }

    //Update Party
    fun updateParty(party: Party?) {
        partyRepository.updateParty(party)
    }

    //Delete Party
    fun deleteParty(party: Party?) {
        partyRepository.deleteParty(party)
    }

}