package co.edu.uniautonoma.posgradosapp.presentation.ui.iniciosesion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.edu.uniautonoma.posgradosapp.domain.entity.Usuarios

class InicioSesionViewModel(application: Application) : AndroidViewModel(application) {

    var usuario: MutableLiveData<Usuarios?>? = null

    fun EnviarPeticion(correo: String?) {

        usuario = MutableLiveData()

        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)

        peticionesApi.getUsuario(correo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Usuarios?> {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(usuarios: Usuarios?) {
                        usuario!!.setValue(usuarios)
                    }

                    fun onError(e: Throwable?) {
                        usuario!!.setValue(null)
                    }

                    fun onComplete() {}
                })
    }
}