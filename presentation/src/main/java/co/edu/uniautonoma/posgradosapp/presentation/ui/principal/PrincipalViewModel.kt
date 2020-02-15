package co.edu.uniautonoma.posgradosapp.presentation.ui.principal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.uniautonoma.posgradosapp.domain.entity.*
import co.edu.uniautonoma.posgradosapp.presentation.models.*

class PrincipalViewModel(application: Application) : AndroidViewModel(application) {

    inner class Informacion constructor(
            val posgrados: Posgrados,
            val modulos: List<Modulos>,
            val horario: List<Horarios>,
            val docente: List<Docentes>,
            val calificaciones: List<Calificaciones>
    )

    var informacion: MutableLiveData<Informacion>? = null
    val estado = MutableLiveData<Boolean>()
    private var posgrado: Posgrados? = null
    private var calificaciones: List<Calificaciones>? = null
    private var modulos: List<Modulos>? = null
    private var horarios: List<Horarios>? = null
    private var docentes: List<Docentes>? = null
    private var id_usuario: String? = null
    private var id_posgrado: String? = null
    private var semestre = 0
    private val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)

    fun getInformacion(): LiveData<Informacion>? {
        return informacion
    }

    fun getEstado(): LiveData<Boolean> {
        return estado
    }

    fun EnviarPeticion(id_posgrado: String?, semestre: Int, id_usuario: String?) {
        this.id_usuario = id_usuario
        this.id_posgrado = id_posgrado
        this.semestre = semestre
        estado.value = true
        Empezar()
    }

    private fun Empezar() {
        ObtenerPosgrado()
    }

    private fun ObtenerPosgrado() {
        peticionesApi.getPosgrado(id_posgrado)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Posgrados?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(posgrados: Posgrados?) {
                        posgrado = posgrados
                    }

                    fun onError(e: Throwable?) {
                        estado.value = false
                    }

                    fun onComplete() {
                        ObtenerModulos()
                    }
                })
    }

    private fun ObtenerModulos() {
        peticionesApi.getSomeModulos(id_posgrado, semestre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Modulos?>?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(response: List<Modulos>?) {
                        modulos = response
                    }

                    fun onError(e: Throwable?) {
                        estado.value = false
                    }

                    fun onComplete() {
                        ObtenerDocentes()
                    }
                })
    }

    private fun ObtenerDocentes() {
        peticionesApi.getSomeDocentes(id_posgrado, semestre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Docentes?>?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(response: List<Docentes>?) {
                        docentes = response
                    }

                    fun onError(e: Throwable?) {
                        estado.value = false
                    }

                    fun onComplete() {
                        ObtenerHorario()
                    }
                })
    }

    private fun ObtenerHorario() {
        peticionesApi.getHorario(id_posgrado, semestre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Horarios?>?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(response: List<Horarios>?) {
                        horarios = response
                    }

                    fun onError(e: Throwable?) {
                        estado.value = false
                    }

                    fun onComplete() {
                        ObtenerCalificaciones()
                    }
                })
    }

    private fun ObtenerCalificaciones() {
        peticionesApi.getSomeCalificaciones(id_posgrado, semestre, id_usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Calificaciones?>?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(response: List<Calificaciones>?) {
                        calificaciones = response
                    }

                    fun onError(e: Throwable?) {
                        estado.value = false
                    }

                    fun onComplete() {
                        EnviarDatos()
                    }
                })
    }

    private fun EnviarDatos() {
        if (posgrado != null && modulos != null && horarios != null && docentes != null && calificaciones != null) {
            val info = Informacion(posgrado!!, modulos!!, horarios!!, docentes!!, calificaciones!!)
            informacion = MutableLiveData()
            informacion!!.setValue(info)
            estado.setValue(false)
        }
    }
}