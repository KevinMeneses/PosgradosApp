package co.edu.uniautonoma.posgradosapp.presentation.ui.principal

import androidx.lifecycle.*
import co.edu.uniautonoma.posgradosapp.domain.entity.*
import co.edu.uniautonoma.posgradosapp.domain.usecase.PrincipalUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class PrincipalViewModel(private val principalUseCase: PrincipalUseCase) : ViewModel() {

    private val informacion = MediatorLiveData<Informacion>()
    private val error = MediatorLiveData<String>()

    val informacionLiveData: LiveData<Informacion> get() = informacion
    val errorLiveData: LiveData<String> get() = error

    fun getPrincipal(id_posgrado: String, semestre: Int, id_usuario: String){
        viewModelScope.launch {
            val result = principalUseCase.getInformacion(id_posgrado, semestre, id_usuario)
            when(result){
                is Result.Success -> informacion.postValue(result.data)
                is Result.Error -> error.postValue(result.exception.message)
            }
        }
    }
}