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

        val docente = intent

        Glide.with(this@DetalleDocenteActivity).load(docente.getStringExtra("imagen")).into(ivFoto!!)
        tvNombre!!.text = docente.getStringExtra("nombre")
        tvProfesion!!.text = docente.getStringExtra("profesion")
        tvDescripcion!!.text = docente.getStringExtra("descripcion")
    }
}