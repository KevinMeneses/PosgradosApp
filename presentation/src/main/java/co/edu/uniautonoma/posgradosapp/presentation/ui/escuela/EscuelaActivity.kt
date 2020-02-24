package co.edu.uniautonoma.posgradosapp.presentation.ui.escuela

import android.os.Bundle
import android.util.Log
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

        mostrarDialog()
        observarResultados()
    }

    private fun observarResultados() {
        escuelaViewModel.escuelaLiveData.observe(this, Observer{
            setViews(it)
            ocultarDialog()
        })

        escuelaViewModel.errorLiveData.observe(this, Observer {
            ocultarDialog()
            Toaster.toast(R.string.EstadoServidor)
            Log.d("EscuelaError:",  it)
        })
    }

    private fun setViews(it: Escuela) {
        val descripcion = it.descripcion
        val director = "Director: ${it.director}"

        tvDescripcionescuela.text = descripcion
        tvDirector.text = director
    }
}