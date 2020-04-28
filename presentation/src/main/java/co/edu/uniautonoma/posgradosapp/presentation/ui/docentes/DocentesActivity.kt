package co.edu.uniautonoma.posgradosapp.presentation.ui.docentes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.lifecycle.Observer
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.adapter.AdapterDocente
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_docentes.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xdroid.toaster.Toaster

class DocentesActivity : BaseActivity() {

    private val docentesViewModel: DocentesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_docentes)

        mostrarDialog()
        hacerPeticion()
        observarResultados()
    }

    private fun hacerPeticion() {
        val id_posgrado = intent.getStringExtra("id_posgrado")
        id_posgrado?.let {
            docentesViewModel.getDocentes(it)
        }
    }

    private fun observarResultados() {

        docentesViewModel.docentesLiveData.observe(this, Observer {
            llenarLista(it)
            ocultarDialog()
        })

        docentesViewModel.errorLiveData.observe(this, Observer {
            Toaster.toast(R.string.EstadoServidor)
            Log.d("DocentesError:", it)
            ocultarDialog()
        })
    }

    private fun llenarLista(docentes: List<Docentes>) {
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