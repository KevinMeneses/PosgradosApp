package co.edu.uniautonoma.posgradosapp.presentation.ui.modulos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos

class ModulosViewModel(application: Application) : AndroidViewModel(application) {

    var modulos: MutableLiveData<List<Modulos>?>? = null
    val estado = MutableLiveData<Boolean>()

    fun ObtenerModulos(id_posgrado: String?) {
        modulos = MutableLiveData()
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.getAllModulos(id_posgrado)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Modulos?>?>() {
                    fun onSubscribe(d: Disposable?) {
                        estado.setValue(true)
                    }

                    fun onNext(response: List<Modulos>?) {
                        modulos!!.setValue(response)
                    }

                    fun onError(e: Throwable?) {
                        modulos!!.setValue(null)
                        estado.setValue(false)
                    }

                    fun onComplete() {
                        estado.setValue(false)
                    }
                })
    }
}