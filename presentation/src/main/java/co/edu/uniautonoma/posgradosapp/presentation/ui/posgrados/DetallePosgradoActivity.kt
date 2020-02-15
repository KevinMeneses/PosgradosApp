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

        tvNombreposgrado!!.text = intent.getStringExtra("nombre")
        tvCod_snies!!.text = "Código snies: " + intent.getStringExtra("cod_snies")
        tvTotalcreditos!!.text = "Total créditos: " + Integer.toString(intent.getIntExtra("totalcreditos", 0))
        tvDuracion!!.text = "Duración: " + intent.getStringExtra("duracion")
        tvValorsemestre!!.text = "Valor semestre: " + intent.getStringExtra("valorsemestre")
        tvDescripcionposgrado!!.text = intent.getStringExtra("descripcion")

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