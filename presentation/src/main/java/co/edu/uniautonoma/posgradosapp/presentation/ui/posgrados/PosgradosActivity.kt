package co.edu.uniautonoma.posgradosapp.presentation.ui.posgrados

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_seleccionar_posgrado.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xdroid.toaster.Toaster
import java.util.*

class PosgradosActivity : BaseActivity() {

    private val posgradosViewModel: PosgradosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_posgrado)

        mostrarDialog()
        observarResultado()
    }

    private fun observarResultado() {

        posgradosViewModel.posgradosLiveData.observe(this, Observer {
            llenarPosgrados(it)
            mostrarPosgrado(it)
            ocultarDialog()
        })

        posgradosViewModel.errorLiveData.observe(this, Observer {
            ocultarDialog()
            Toaster.toast(R.string.EstadoServidor)
            Log.d("PosgradosError:", it)
        })
    }

    private fun llenarPosgrados(posgrados: List<Posgrados>) {
        val listaposgrados = ArrayList<String>()
        posgrados.map {
            it.nombre?.let {
                listaposgrados.add(it)
            }
        }
        lvPosgrados.adapter = ArrayAdapter(this, R.layout.lista_posgrado, R.id.tvlistaposgrado, listaposgrados)
    }

    private fun mostrarPosgrado(posgrados: List<Posgrados>) {
        lvPosgrados.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val i = Intent(this@PosgradosActivity, DetallePosgradoActivity::class.java)
            i.putExtra("id_posgrado", posgrados[position].id)
            i.putExtra("cod_snies", posgrados[position].cod_snies)
            i.putExtra("nombre", posgrados[position].nombre)
            i.putExtra("duracion", posgrados[position].duracion)
            i.putExtra("totalcreditos", posgrados[position].totalcreditos)
            i.putExtra("descripcion", posgrados[position].descripcion)
            i.putExtra("valorsemestre", posgrados[position].valorsemestre)
            startActivity(i)
        }
    }
}