package co.edu.uniautonoma.posgradosapp.presentation.ui.iniciosesion

import androidx.lifecycle.*
import co.edu.uniautonoma.posgradosapp.domain.entity.Usuario
import co.edu.uniautonoma.posgradosapp.domain.usecase.InicioSesionUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class InicioSesionViewModel(private val inicioSesionUseCase: InicioSesionUseCase) : ViewModel() {

    private val usuario = MediatorLiveData<Usuario?>()
    private val error = MediatorLiveData<String?>()

    val usuarioLiveData: LiveData<Usuario?> get() = usuario
    val errorLiveData: LiveData<String?> get() = error

    fun enviarPeticion(correo: String) {
        viewModelScope.launch {
            when(val result = inicioSesionUseCase.iniciarSesion(correo)){
                is Result.Success -> usuario.postValue(result.data)
                is Result.Error -> error.postValue(result.exception.message)
            }
        }
    }
}