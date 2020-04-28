package co.edu.uniautonoma.posgradosapp.presentation.adapter

import android.content.Context
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.lista_docentes.view.*

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

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val views = inflate(context, R.layout.lista_docentes, null)

        views.run {
            tvNdocente.text = "${docente[i].nombre} ${docente[i].apellido}"
            tvPdocente.text = docente[i].profesion
            Glide.with(context).load(docente[i].imagen).into(ivDocente)
        }

        return views
    }
}