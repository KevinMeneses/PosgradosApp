package co.edu.uniautonoma.posgradosapp.presentation.ui.pqrs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.uniautonoma.posgradosapp.presentation.R
import kotlinx.android.synthetic.main.activity_pqrs.*
import xdroid.toaster.Toaster

class PqrsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pqrs)

        var destinatario = ""
        val viewModel = ViewModelProvider(this)[PqrsViewModel::class.java]

        if (viewModel.escuela != null) {
            viewModel.escuela?.observe(this, Observer {
                destinatario = it!!.getCorreo()
            })
        } else {
            Toaster.toast(R.string.EstadoServidor)
        }

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