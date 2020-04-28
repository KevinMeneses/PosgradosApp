package co.edu.uniautonoma.posgradosapp.presentation.ui.docentes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.edu.uniautonoma.posgradosapp.presentation.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detalle_docente.*

class DetalleDocenteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_docente)

        setViews()
    }

    private fun setViews() {
        val foto = intent.getStringExtra("imagen")
        val nombre = intent.getStringExtra("nombre")
        val profesion = intent.getStringExtra("profesion")
        val descripcion = intent.getStringExtra("descripcion")

        Glide.with(this@DetalleDocenteActivity).load(foto).into(ivFoto)
        tvNombre.text = nombre
        tvProfesion.text = profesion
        tvDescripcion.text = descripcion
    }
}