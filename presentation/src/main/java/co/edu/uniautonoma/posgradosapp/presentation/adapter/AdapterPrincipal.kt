package co.edu.uniautonoma.posgradosapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniautonoma.posgradosapp.domain.entity.Calificaciones
import co.edu.uniautonoma.posgradosapp.domain.entity.Informacion
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.adapter.AdapterPrincipal.ModulosViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cardview_principal.view.*
import kotlinx.android.synthetic.main.lista_docentes.view.*

class AdapterPrincipal : RecyclerView.Adapter<ModulosViewHolder>() {

    private var informacion: Informacion? = null
    private var count: Int = 0
    var onItemClick: ((Int, Int) -> Unit)? = null

    fun setInformacion(info: Informacion?){
        informacion = info
        count = info?.modulos?.size!!
    }

    inner class ModulosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        init {
            itemView.run {
                tvNombreDocente.setOnClickListener(this@ModulosViewHolder)
                ivFotoDocente.setOnClickListener(this@ModulosViewHolder)
                ivDetalleDocente.setOnClickListener(this@ModulosViewHolder)
                tvNombreModulo.setOnClickListener(this@ModulosViewHolder)
                tvDuracionModulo.setOnClickListener(this@ModulosViewHolder)
                ivContenido.setOnClickListener(this@ModulosViewHolder)
                ivHorario.setOnClickListener(this@ModulosViewHolder)
                ivAlerta.setOnClickListener(this@ModulosViewHolder)
            }
        }

        override fun onClick(it: View?) {
            onItemClick?.invoke(adapterPosition, it!!.id)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ModulosViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cardview_principal, viewGroup, false)
        return ModulosViewHolder(view)
    }

    override fun onBindViewHolder(modulosViewHolder: ModulosViewHolder, position: Int) {
        informacion?.run {
            val modulo = modulos[position]
            val docente = docente[position]
            val calificacion = calificaciones[position]
            val nombredocente = "${docente.nombre} ${docente.apellido}"
            val promedio = calificacion.promedio.toString()

            modulosViewHolder.itemView.run {
                tvNombreModulo.text = modulo.nombre
                tvDuracionModulo.text = modulo.duracion
                tvNombreDocente.text = nombredocente
                tvCalificacion.text = promedio
                rbCalificacion.rating = calificacion.calificacion
                //rbCalificacion.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser -> SetCalificacion(position, rating) }
                Glide.with(ivFotoDocente.context).load(docente.imagen).into(ivFotoDocente)
            }
        }
    }

    override fun getItemCount(): Int {
        return count
    }

    fun clear() {
        if(count > 0) {
            informacion = null
            notifyItemRangeRemoved(0, count)
        }
    }

    /*private fun SetCalificacion(position: Int, calificacion: Float) {
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.getSomeCalificacion(id_usuario, modulos!![position].id_docente)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Calificaciones?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(calificaciones: Calificaciones?) {}
                    fun onError(e: Throwable?) {
                        agregarCalificacion(calificacion, modulos!![position].id_docente)
                    }

                    fun onComplete() {
                        modificarCalificacion(calificacion, modulos!![position].id_docente)
                    }
                })
    }

    private fun modificarCalificacion(calificacion: Float, id_docente: String) {
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.updateCalificacion(calificacion, id_usuario, id_docente)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Calificaciones?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(calificaciones: Calificaciones?) {}
                    fun onError(e: Throwable?) {}
                    fun onComplete() {}
                })
    }

    private fun agregarCalificacion(calificacion: Float, id_docente: String) {
        val peticionesApi: PeticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi::class.java)
        peticionesApi.addCalificacion(calificacion, id_usuario, id_docente)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Calificaciones?>() {
                    fun onSubscribe(d: Disposable?) {}
                    fun onNext(calificaciones: Calificaciones?) {}
                    fun onError(e: Throwable?) {}
                    fun onComplete() {}
                })
    }*/
}