package co.edu.uniautonoma.posgradosapp.presentation.ui.ubicacion

import androidx.lifecycle.*
import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.domain.usecase.EscuelaUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class UbicacionViewModel(private val escuelaUseCase: EscuelaUseCase) : ViewModel() {

    private val escuela = MediatorLiveData<Escuela>()
    private val error = MediatorLiveData<String>()

    val escuelaLiveData: LiveData<Escuela> get() = escuela
    val errorLiveData: LiveData<String> get() = error

    private fun getUbicacion() {
        viewModelScope.launch {
            val result = escuelaUseCase.getEscuela()
            when(result){
                is Result.Success -> escuela.postValue(result.data)
                is Result.Error -> error.postValue(result.exception.message)
            }
        }
    }

    init {
        getUbicacion()
    }
}