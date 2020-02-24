package co.edu.uniautonoma.posgradosapp.presentation.ui.docentes

import androidx.lifecycle.*
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.domain.usecase.DocentesUseCase
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import kotlinx.coroutines.launch

class DocentesViewModel(private val docentesUseCase: DocentesUseCase) : ViewModel() {

    private val docentes = MediatorLiveData<List<Docentes>>()
    private val error = MediatorLiveData<String>()

    val docentesLiveData: LiveData<List<Docentes>> get() = docentes
    val errorLiveData: LiveData<String> get() = error

    fun getDocentes(id_posgrado: String){
        viewModelScope.launch {
            when(val result = docentesUseCase.getDocentes(id_posgrado)){
                is Result.Success -> docentes.postValue(result.data)
                is Result.Error -> error.postValue(result.exception.message)
            }
        }
    }
}