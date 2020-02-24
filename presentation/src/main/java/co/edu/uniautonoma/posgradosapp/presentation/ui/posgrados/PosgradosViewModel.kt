package co.edu.uniautonoma.posgradosapp.presentation.ui.posgrados

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados
import co.edu.uniautonoma.posgradosapp.domain.usecase.PosgradosUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class PosgradosViewModel(private val posgradosUseCase: PosgradosUseCase) : ViewModel() {

    private val posgrados = MediatorLiveData<List<Posgrados>>()
    private val error = MediatorLiveData<String>()

    val posgradosLiveData: LiveData<List<Posgrados>> get() = posgrados
    val errorLiveData: LiveData<String> get() = error

    private fun getPosgrados() {
        viewModelScope.launch {
            val result = posgradosUseCase.getPosgrados()
            when(result){
                is Result.Success -> posgrados.postValue(result.data)
                is Result.Error -> error.postValue(result.exception.message)
            }
        }
    }

    init {
        getPosgrados()
    }
}