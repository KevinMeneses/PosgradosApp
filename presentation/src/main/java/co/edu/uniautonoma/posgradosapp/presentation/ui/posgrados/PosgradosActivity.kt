package co.edu.uniautonoma.posgradosapp.presentation.ui.posgrados

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_seleccionar_posgrado.*
import xdroid.toaster.Toaster
import java.util.*

class PosgradosActivity : BaseActivity() {

    private var posgrados: List<Posgrados> = ArrayList()
    private val listaposgrados = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_posgrado)

        val viewModel = ViewModelProviders.of(this).get(PosgradosViewModel::class.java)
        if (viewModel.allPosgrados != null) {
            viewModel.allPosgrados?.observe(this, Observer {
                it?.let {
                    posgrados = it
                    llenarPosgrados()
                }
            })
        } else {
            Toaster.toast(R.string.EstadoServidor)
        }
        viewModel.estado.observe(this, Observer {
            if (it) mostrarDialog() else ocultarDialog()
        })
    }

    private fun llenarPosgrados() {

        for (i in posgrados.indices) {
            listaposgrados.add(posgrados[i].getNombre())
        }

        lvPosgrados.adapter = ArrayAdapter(this, R.layout.lista_posgrado, R.id.tvlistaposgrado, listaposgrados)

        lvPosgrados.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val i = Intent(this@PosgradosActivity, DetallePosgradoActivity::class.java)
            i.putExtra("id_posgrado", posgrados[position].getId())
            i.putExtra("cod_snies", posgrados[position].getCod_snies())
            i.putExtra("nombre", posgrados[position].getNombre())
            i.putExtra("duracion", posgrados[position].getDuracion())
            i.putExtra("totalcreditos", posgrados[position].getTotalcreditos())
            i.putExtra("descripcion", posgrados[position].getDescripcion())
            i.putExtra("valorsemestre", posgrados[position].getValorsemestre())
            startActivity(i)
        }
    }
}