package co.edu.uniautonoma.posgradosapp.presentation.adapter

import android.content.Context
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import com.bumptech.glide.Glide

class AdapterDocente(private val context: Context, private val docente: List<Docentes>) : BaseAdapter() {

    override fun getCount(): Int {
        return docente.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {

        inflate(view.context, R.layout.lista_docentes, null)

        val tvNdocente = view.findViewById<View>(R.id.tvNdocente) as TextView
        val tvPdocente = view.findViewById<View>(R.id.tvPdocente) as TextView
        val ivDocente = view.findViewById<View>(R.id.ivDocente) as ImageView

        tvNdocente.text = "${docente[i].nombre} ${docente[i].apellido}"
        tvPdocente.text = docente[i].profesion
        Glide.with(context).load(docente[i].imagen).into(ivDocente)

        return view
    }
}