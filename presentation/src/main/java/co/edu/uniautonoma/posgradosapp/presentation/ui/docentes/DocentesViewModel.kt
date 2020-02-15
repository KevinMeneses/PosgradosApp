package co.edu.uniautonoma.posgradosapp.presentation.ui.docentes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes

class DocentesViewModel(application: Application) : AndroidViewModel(application) {

    var docentes: MutableLiveData<List<Docentes>?>? = null
    val estado = MutableLiveData<Boolean>()

    fun getDocentes(): LiveData<List<Docentes>?>? {
        return docentes
    }

    fun getEstado(): LiveData<Boolean> {
        return estado
    }

    fun EnviarPeticion(id_posgrado: String?) {
        docentes = MutableLiveData()
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.getAllDocentes(id_posgrado)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Docentes?>?>() {
                    fun onSubscribe(d: Disposable?) {
                        estado.setValue(true)
                    }

                    fun onNext(response: List<Docentes>?) {
                        docentes!!.setValue(response)
                    }

                    fun onError(e: Throwable?) {
                        docentes!!.setValue(null)
                        estado.setValue(false)
                    }

                    fun onComplete() {
                        estado.setValue(false)
                    }
                })
    }
}