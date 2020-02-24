package co.edu.uniautonoma.posgradosapp.presentation.ui.modulos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.edu.uniautonoma.posgradosapp.presentation.R
import kotlinx.android.synthetic.main.activity_detalle_modulo.*

class DetalleModuloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_modulo)

        setDetalles()
    }

    private fun setDetalles() {
        val titulo = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val creditos = "Creditos: " + Integer.toString(intent.getIntExtra("creditos", 0))

        tvTituloModulo.text = titulo
        tvDetalleModulo.text = descripcion
        tvCreditosModulo.text = creditos
    }
}