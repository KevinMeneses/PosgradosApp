package co.edu.uniautonoma.posgradosapp.presentation.ui.modulos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_modulos.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xdroid.toaster.Toaster
import java.util.*

class ModulosActivity : BaseActivity() {

    private val modulosViewModel: ModulosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modulos)

        mostrarDialog()
        hacerPeticion()
        observarResultado()
    }

    private fun hacerPeticion() {
        val id_posgrado = intent.getStringExtra("id_posgrado")
        id_posgrado?.let {
            modulosViewModel.getModulos(it)
        }
    }

    private fun observarResultado() {

        modulosViewModel.modulosLiveData.observe(this, Observer {
            llenarLista(it)
            mostrarModulo(it)
            ocultarDialog()
        })

        modulosViewModel.errorLiveData.observe(this, Observer {
            ocultarDialog()
            Toaster.toast(R.string.EstadoServidor)
            Log.d("ModulosError:", it)
        })
    }

    private fun llenarLista(modulos: List<Modulos>) {
        val nombremodulos = ArrayList<String>()
        modulos.map { nombremodulos.add(it.nombre) }
        lvModulos.adapter = ArrayAdapter(this, R.layout.lista_modulos, R.id.tvModulo, nombremodulos)
    }

    private fun mostrarModulo(modulos: List<Modulos>) {
        lvModulos.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val i = Intent(this@ModulosActivity, DetalleModuloActivity::class.java)
            i.putExtra("nombre", modulos[position].nombre)
            i.putExtra("descripcion", modulos[position].descripcion)
            i.putExtra("creditos", modulos[position].creditos)
            startActivity(i)
        }
    }
}