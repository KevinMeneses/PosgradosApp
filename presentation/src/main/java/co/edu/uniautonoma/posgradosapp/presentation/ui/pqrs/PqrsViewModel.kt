package co.edu.uniautonoma.posgradosapp.presentation.ui.pqrs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela

class PqrsViewModel(application: Application) : AndroidViewModel(application) {
    var escuela: MutableLiveData<Escuela?>? = null

    fun getEscuela(): LiveData<Escuela?>? {
        EnviarPeticion()
        return escuela
    }

    private fun EnviarPeticion() {
        escuela = MutableLiveData()
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.getEscuela(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Escuela?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(response: Escuela?) {
                        escuela!!.setValue(response)
                    }

                    fun onError(e: Throwable?) {
                        escuela!!.setValue(null)
                    }

                    fun onComplete() {}
                })
    }
}