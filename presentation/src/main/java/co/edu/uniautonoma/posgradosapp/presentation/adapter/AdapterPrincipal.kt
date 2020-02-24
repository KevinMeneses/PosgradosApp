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

class AdapterPrincipal : RecyclerView.Adapter<ModulosViewHolder>() {

    private var informacion: Informacion? = null

    fun setInformacion(info: Informacion?){
        informacion = info
    }

    private var clickListener: ClickListener? = null

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClicked(position: Int, tag: String?)
    }

    inner class ModulosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View) {
            if (clickListener != null) {
                clickListener!!.onItemClicked(position, v.tag.toString())
            }
        }

        init {
            itemView.run {
                tvNombreDocente.tag = "Docentes"
                rlFotoDocente.tag = "Docentes"
                ivDetalleDocente.tag = "Docentes"
                tvNombreDocente.setOnClickListener(this@ModulosViewHolder)
                rlFotoDocente.setOnClickListener(this@ModulosViewHolder)
                ivDetalleDocente.setOnClickListener(this@ModulosViewHolder)
                tvNombreModulo.tag = "Modulos"
                ivContenido.tag = "Modulos"
                tvNombreModulo.setOnClickListener(this@ModulosViewHolder)
                ivContenido.setOnClickListener(this@ModulosViewHolder)
                ivHorario.tag = "Horarios"
                ivAlerta.tag = "Alerta"
                ivHorario.setOnClickListener(this@ModulosViewHolder)
                ivAlerta.setOnClickListener(this@ModulosViewHolder)
            }

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

            modulosViewHolder.itemView.tvNombreModulo.text = modulo.nombre
            modulosViewHolder.itemView.tvDuracionModulo.text = modulo.duracion
            modulosViewHolder.itemView.tvNombreDocente.text = nombredocente
            modulosViewHolder.itemView.tvCalificacion.text = promedio
            modulosViewHolder.itemView.rbCalificacion.rating = calificacion.calificacion
            //modulosViewHolder.itemView.rbCalificacion.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser -> SetCalificacion(position, rating) }
            Glide.with(modulosViewHolder.itemView.ivFotoDocente.context).load(docente.imagen).into(modulosViewHolder.itemView.ivFotoDocente)
        }
    }

    override fun getItemCount(): Int {
        return informacion?.modulos!!.size.let {
            it
        }
    }

    fun clear() {
        informacion?.modulos!!.size.let {
            informacion = null
            notifyItemRangeRemoved(0, it)
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