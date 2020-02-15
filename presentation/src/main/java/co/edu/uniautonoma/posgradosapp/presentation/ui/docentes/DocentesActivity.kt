package co.edu.uniautonoma.posgradosapp.presentation.ui.docentes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.adapter.AdapterDocente
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import xdroid.toaster.Toaster
import java.util.*

class DocentesActivity : BaseActivity() {

    private var docentes: List<Docentes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_docentes)

        val id_posgrado = intent.getStringExtra("id_posgrado")
        val viewModel = ViewModelProvider(this)[DocentesViewModel::class.java]
        viewModel.EnviarPeticion(id_posgrado)

        if (viewModel.docentes != null) {
            viewModel.docentes?.observe(this, Observer {
                it?.let {
                    docentes = it
                    llenarLista()
                }

            })
        } else {
            Toaster.toast(R.string.EstadoServidor)
        }

        viewModel.estado.observe(this, Observer {
            if (it) mostrarDialog() else ocultarDialog()
        })
    }

    private fun llenarLista() {
        val lvDocentes = findViewById<View>(R.id.lvDocentes) as ListView

        lvDocentes.adapter = AdapterDocente(applicationContext, docentes)
        lvDocentes.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val i = Intent(this@DocentesActivity, DetalleDocenteActivity::class.java)
            i.putExtra("nombre", docentes[position].nombre + " " + docentes[position].apellido)
            i.putExtra("profesion", docentes[position].profesion)
            i.putExtra("descripcion", docentes[position].descripcion)
            i.putExtra("imagen", docentes[position].imagen)
            startActivity(i)
        }
    }
}