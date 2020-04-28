package co.edu.uniautonoma.posgradosapp.presentation.ui.pqrs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pqrs.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xdroid.toaster.Toaster

class PqrsActivity : BaseActivity() {

    private val pqrsViewModel: PqrsViewModel by viewModel()
    private var destinatario = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pqrs)

        observarResultados()
        enviarCorreo()
    }

    private fun observarResultados() {
        pqrsViewModel.destinatarioLiveData.observe(this, Observer {
            destinatario = it
        })

        pqrsViewModel.errorLiveData.observe(this, Observer {
            Toaster.toast(R.string.EstadoServidor)
            Log.d("PqrsError:", it)
        })
    }

    private fun enviarCorreo() {
        btEnviar.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            val comentario = etComentario!!.text.toString()

            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(destinatario))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.asuntopqrs))
            emailIntent.putExtra(Intent.EXTRA_TEXT, comentario)
            emailIntent.type = "message/rfc822"
            startActivity(Intent.createChooser(emailIntent, "Email"))
        }
    }
}