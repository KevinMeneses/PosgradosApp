package co.edu.uniautonoma.posgradosapp.presentation.ui.pqrs

import androidx.lifecycle.*
import co.edu.uniautonoma.posgradosapp.domain.usecase.EscuelaUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class PqrsViewModel(private val escuelaUseCase: EscuelaUseCase) : ViewModel() {

    private val destinatario = MediatorLiveData<String>()
    private val error = MediatorLiveData<String>()

    val destinatarioLiveData: LiveData<String> get() = destinatario
    val errorLiveData: LiveData<String> get() = error

    fun getDestinatario() {
        viewModelScope.launch {
            when(val response = escuelaUseCase.getDestinatario()){
                is Result.Success -> destinatario.postValue(response.data)
                is Result.Error -> error.postValue(response.exception.message)
            }
        }
    }

    init {
        getDestinatario()
    }
}