package co.edu.uniautonoma.posgradosapp.presentation.ui.posgrados

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados

class PosgradosViewModel(application: Application) : AndroidViewModel(application) {
    private var posgrados: MutableLiveData<List<Posgrados>?>? = null
    val estado = MutableLiveData<Boolean>()
    val allPosgrados: LiveData<List<Posgrados>?>?
        get() = posgrados

    private fun ObtenerPosgrados() {
        posgrados = MutableLiveData()
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.getAllPosgrados()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Posgrados?>?>() {
                    fun onSubscribe(d: Disposable?) {
                        estado.setValue(true)
                    }

                    fun onNext(response: List<Posgrados>?) {
                        posgrados!!.setValue(response)
                    }

                    fun onError(e: Throwable?) {
                        posgrados!!.setValue(null)
                        estado.setValue(false)
                    }

                    fun onComplete() {
                        estado.setValue(false)
                    }
                })
    }

    init {
        ObtenerPosgrados()
    }
}