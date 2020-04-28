package co.edu.uniautonoma.posgradosapp.presentation.ui.posgrados

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.ui.docentes.DocentesActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.modulos.ModulosActivity
import kotlinx.android.synthetic.main.activity_detalle_posgrado.*

class DetallePosgradoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_posgrado)

        setDetalles()
        onClick()
    }

    private fun setDetalles() {
        val nombreposgrado = intent.getStringExtra("nombre")
        val codsnies = "Código snies: " + intent.getStringExtra("cod_snies")
        val totalcreditos = "Total créditos: " + Integer.toString(intent.getIntExtra("totalcreditos", 0))
        val duracion = "Duración: " + intent.getStringExtra("duracion")
        val valorsemestre = "Valor semestre: " + intent.getStringExtra("valorsemestre")
        val descripcion = intent.getStringExtra("descripcion")

        tvNombreposgrado.text = nombreposgrado
        tvCod_snies.text = codsnies
        tvTotalcreditos.text = totalcreditos
        tvDuracion.text = duracion
        tvValorsemestre.text = valorsemestre
        tvDescripcionposgrado.text = descripcion
    }

    private fun onClick() {
        tvOpModulos!!.setOnClickListener {
            val i = Intent(this@DetallePosgradoActivity, ModulosActivity::class.java)
            i.putExtra("id_posgrado", intent.getStringExtra("id_posgrado"))
            i.putExtra("semestre", 3)
            startActivity(i)
        }

        tvOpDocentes!!.setOnClickListener {
            val i = Intent(this@DetallePosgradoActivity, DocentesActivity::class.java)
            i.putExtra("id_posgrado", intent.getStringExtra("id_posgrado"))
            startActivity(i)
        }
    }
}