package co.edu.uniautonoma.posgradosapp.presentation.ui.escuela

import androidx.lifecycle.*
import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.domain.usecase.EscuelaUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class EscuelaViewModel(private val escuelaUseCase: EscuelaUseCase) : ViewModel() {

    private val escuela = MediatorLiveData<Escuela>()
    private val error = MediatorLiveData<String>()

    val escuelaLiveData: LiveData<Escuela> get() = escuela
    val errorLiveData: LiveData<String> get() = error

    fun getEscuela() {
        viewModelScope.launch {
            when(val result = escuelaUseCase.getEscuela()){
                is Result.Success -> escuela.postValue(result.data)
                is Result.Error -> error.postValue(result.exception.message)
            }
        }
    }

    init {
        getEscuela()
    }
}