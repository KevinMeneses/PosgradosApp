package co.edu.uniautonoma.posgradosapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniautonoma.posgradosapp.domain.entity.Calificaciones
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.adapter.AdapterPrincipal.ModulosViewHolder
import com.bumptech.glide.Glide

class AdapterPrincipal : RecyclerView.Adapter<ModulosViewHolder>() {

    private var modulos: MutableList<Modulos>? = null
    private var docentes: MutableList<Docentes>? = null
    private var calificaciones: MutableList<Calificaciones>? = null
    private var id_usuario: String? = null
    private var clickListener: ClickListener? = null

    fun setInfo(modulos: MutableList<Modulos>?, docentes: MutableList<Docentes>?, calificaciones: MutableList<Calificaciones>?, id_usuario: String?) {
        this.modulos = modulos
        this.docentes = docentes
        this.calificaciones = calificaciones
        this.id_usuario = id_usuario
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClicked(position: Int, tag: String?)
    }

    inner class ModulosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvNombreModulo: TextView
        val tvDuracionModulo: TextView
        val tvNombreDocente: TextView
        val ivFotoDocente: ImageView
        private val rlFotoDocente: RelativeLayout
        private val ivDetalleDocente: ImageView
        private val ivContenido: ImageView
        private val ivHorario: ImageView
        private val ivAlerta: ImageView
        val rbCalificacion: RatingBar
        val tvCalificacion: TextView
        override fun onClick(v: View) {
            if (clickListener != null) {
                clickListener!!.onItemClicked(position, v.tag.toString())
            }
        }

        init {
            tvNombreModulo = itemView.findViewById(R.id.tvNombreModulo)
            tvDuracionModulo = itemView.findViewById(R.id.tvDuracionModulo)
            tvNombreDocente = itemView.findViewById(R.id.tvNombreDocente)
            ivFotoDocente = itemView.findViewById(R.id.ivFotoDocente)
            rlFotoDocente = itemView.findViewById(R.id.rlFotoDocente)
            ivDetalleDocente = itemView.findViewById(R.id.ivDetalleDocente)
            ivContenido = itemView.findViewById(R.id.ivContenido)
            ivHorario = itemView.findViewById(R.id.ivHorario)
            ivAlerta = itemView.findViewById(R.id.ivAlerta)
            rbCalificacion = itemView.findViewById(R.id.rbCalificacion)
            tvCalificacion = itemView.findViewById(R.id.tvCalificacion)

            tvNombreDocente.tag = "Docentes"
            rlFotoDocente.tag = "Docentes"
            ivDetalleDocente.tag = "Docentes"
            tvNombreDocente.setOnClickListener(this)
            rlFotoDocente.setOnClickListener(this)
            ivDetalleDocente.setOnClickListener(this)
            tvNombreModulo.tag = "Modulos"
            ivContenido.tag = "Modulos"
            tvNombreModulo.setOnClickListener(this)
            ivContenido.setOnClickListener(this)
            ivHorario.tag = "Horarios"
            ivAlerta.tag = "Alerta"
            ivHorario.setOnClickListener(this)
            ivAlerta.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ModulosViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cardview_principal, viewGroup, false)
        return ModulosViewHolder(view)
    }

    override fun onBindViewHolder(modulosViewHolder: ModulosViewHolder, i: Int) {
        val modulo = modulos!![i]
        val docente = docentes!![i]
        val calificacion = calificaciones!![i]
        val nombredocente = docente.nombre + " " + docente.apellido
        val promedio = java.lang.Float.toString(calificacion.promedio)
        modulosViewHolder.tvNombreModulo.text = modulo.nombre
        modulosViewHolder.tvDuracionModulo.text = modulo.duracion
        modulosViewHolder.tvNombreDocente.text = nombredocente
        modulosViewHolder.tvCalificacion.text = promedio
        modulosViewHolder.rbCalificacion.rating = calificacion.calificacion
        modulosViewHolder.rbCalificacion.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser -> SetCalificacion(i, rating) }
        Glide.with(modulosViewHolder.ivFotoDocente.context).load(docente.imagen).into(modulosViewHolder.ivFotoDocente)
    }

    override fun getItemCount(): Int {
        return modulos!!.size
    }

    fun clear() {
        val size = modulos!!.size
        if (size > 0) {
            modulos!!.clear()
            docentes!!.clear()
            calificaciones!!.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    private fun SetCalificacion(position: Int, calificacion: Float) {
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
    }
}