package co.edu.uniautonoma.posgradosapp.presentation.ui.modulos

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_modulos.*
import xdroid.toaster.Toaster
import java.util.*

class ModulosActivity : BaseActivity() {

    private var modulos: List<Modulos> = ArrayList()
    private val nombremodulos = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modulos)

        val id_posgrado = intent.getStringExtra("id_posgrado")
        val viewModel = ViewModelProvider(this)[ModulosViewModel::class.java]

        viewModel.ObtenerModulos(id_posgrado)
        if (viewModel.modulos != null) {
            viewModel.modulos?.observe(this, Observer {
                it?.let {
                    modulos = it
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

        for (i in modulos.indices) {
            nombremodulos.add(modulos[i].getNombre())
        }

        lvModulos.adapter = ArrayAdapter(this, R.layout.lista_modulos, R.id.tvModulo, nombremodulos)
        lvModulos.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val i = Intent(this@ModulosActivity, DetalleModuloActivity::class.java)
            i.putExtra("nombre", modulos[position].getNombre())
            i.putExtra("descripcion", modulos[position].getDescripcion())
            i.putExtra("creditos", modulos[position].getCreditos())
            startActivity(i)
        }
    }
}