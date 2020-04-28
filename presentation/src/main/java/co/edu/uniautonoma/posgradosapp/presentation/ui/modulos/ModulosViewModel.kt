package co.edu.uniautonoma.posgradosapp.presentation.ui.modulos

import androidx.lifecycle.*
import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.domain.usecase.ModulosUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class ModulosViewModel(private val modulosUseCase: ModulosUseCase) : ViewModel() {

    private val modulos = MediatorLiveData<List<Modulos>>()
    private val error = MediatorLiveData<String>()

    val modulosLiveData: LiveData<List<Modulos>> get() = modulos
    val errorLiveData: LiveData<String> get() = error

    fun getModulos(id_posgrado: String) {
        viewModelScope.launch {
            val result = modulosUseCase.getModulos(id_posgrado)
            when(result){
                is Result.Success -> modulos.postValue(result.data)
                is Result.Error -> error.postValue(result.exception.message)
            }
        }
    }
}