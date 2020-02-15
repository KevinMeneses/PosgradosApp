package co.edu.uniautonoma.posgradosapp.presentation.ui.escuela

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.domain.usecase.EscuelaUseCase

class EscuelaViewModel(private val escuelaUseCase: EscuelaUseCase) : ViewModel() {

    var escuela: MutableLiveData<Escuela?>? = null
    val estado = MutableLiveData<Boolean>()

    fun getEscuela(): LiveData<Escuela?>? {
        EnviarPeticion()
        return escuela
    }

    fun getEstado(): LiveData<Boolean> {
        return estado
    }

    private fun EnviarPeticion() {
        escuela = MutableLiveData()
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.getEscuela(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Escuela?>() {
                    fun onSubscribe(s: Subscription?) {
                        estado.setValue(true)
                    }

                    fun onNext(response: Escuela?) {
                        escuela!!.setValue(response)
                    }

                    fun onError(e: Throwable?) {
                        escuela!!.setValue(null)
                        estado.setValue(false)
                    }

                    fun onComplete() {
                        estado.setValue(false)
                    }
                })
    }
}