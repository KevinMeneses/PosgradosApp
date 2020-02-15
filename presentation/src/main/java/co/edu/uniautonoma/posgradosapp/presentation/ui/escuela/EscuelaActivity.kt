package co.edu.uniautonoma.posgradosapp.presentation.ui.escuela

import android.os.Bundle
import androidx.lifecycle.Observer
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_escuela.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xdroid.toaster.Toaster

class EscuelaActivity : BaseActivity() {

    private val escuelaViewModel: EscuelaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escuela)
        ObtenerEscuela()
    }

    private fun ObtenerEscuela() {
        mostrarDialog()
        if (escuelaViewModel.escuela != null) {
            escuelaViewModel.escuela?.observe(this, Observer{
                it?.let {
                    tvDescripcionescuela.setText(it.getDescripcion())
                    val director = "Director: " + it.getDirector()
                    tvDirector!!.text = director
                }

            })
        } else {
            Toaster.toast(R.string.EstadoServidor)
        }
        escuelaViewModel.estado.observe(this, Observer { estado: Boolean -> if (estado) mostrarDialog() else ocultarDialog() })
    }
}