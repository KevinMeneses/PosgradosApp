package co.edu.uniautonoma.posgradosapp.presentation.ui.principal

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.adapter.AdapterPrincipal
import co.edu.uniautonoma.posgradosapp.presentation.helper.AlarmReceiver
import co.edu.uniautonoma.posgradosapp.domain.entity.Calificaciones
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.domain.entity.Horarios
import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.docentes.DetalleDocenteActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.modulos.DetalleModuloActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.popup_horarios.*
import xdroid.toaster.Toaster
import java.util.*

class PrincipalActivity : BaseActivity() {

    private var adapterPrincipal: AdapterPrincipal? = null
    private var modulos: MutableList<Modulos> = ArrayList()
    private var docentes: MutableList<Docentes> = ArrayList()
    private var horarios: MutableList<Horarios> = ArrayList()
    private var calificaciones: MutableList<Calificaciones> = ArrayList()
    private var id_usuario: String? = null
    private var id_posgrado: String? = null
    private var semestre = 0
    private var estadoalarma = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        id_usuario = intent.getStringExtra("id_usuario")
        semestre = intent.getIntExtra("semestre", 0)
        id_posgrado = intent.getStringExtra("id_posgrado")

        val btSemestre1 = findViewById<View>(R.id.btSemestre1) as Button
        btSemestre1.setOnClickListener {
            if (semestre != 1) {
                CargarSemestre(1)
            }
        }
        val btSemestre2 = findViewById<View>(R.id.btSemestre2) as Button
        btSemestre2.setOnClickListener {
            if (semestre != 2) {
                CargarSemestre(2)
            }
        }
        SetSemestre()
        ObtenerInformacion()
    }

    private fun SetSemestre() {
        runOnUiThread {
            if (semestre == 1) {
                tvSemestre.setText("PRIMER SEMESTRE")
            } else {
                tvSemestre.setText("SEGUNDO SEMESTRE")
            }
        }
    }

    private fun ObtenerInformacion() {

        adapterPrincipal = AdapterPrincipal()
        val viewModel = ViewModelProvider(this)[PrincipalViewModel::class.java]
        viewModel.EnviarPeticion(id_posgrado, semestre, id_usuario)

        viewModel.estado.observe(this, Observer {
            if (it) mostrarDialog() else {
                if (viewModel.informacion != null) {
                    viewModel.informacion?.observe(this, Observer {
                        it?.let {

                        }
                        tvEspecializacion.setText(it.posgrados.getNombre())
                        modulos = it.modulos as MutableList<Modulos>
                        docentes = it.docente as MutableList<Docentes>
                        calificaciones = it.calificaciones as MutableList<Calificaciones>
                        horarios = it.horario as MutableList<Horarios>
                        adapterPrincipal?.setInfo(modulos, docentes, calificaciones, id_usuario)
                        LlenarLista()
                    })
                } else {
                    Toaster.toast(R.string.EstadoServidor)
                }
                ocultarDialog()
            }
        })
    }

    private fun LlenarLista() {
        rvModulos!!.layoutManager = LinearLayoutManager(this)
        rvModulos!!.adapter = adapterPrincipal

        adapterPrincipal?.setClickListener(object : AdapterPrincipal.ClickListener {
            override fun onItemClicked(position: Int, tag: String?) {
                when (tag) {
                    "Docentes" -> VerDocente(position)
                    "Modulos" -> VerModulo(position)
                    "Horarios" -> if (!horarios.isEmpty()) {
                        VerHorario()
                    } else {
                        Toaster.toast(R.string.msg_horario_err)
                    }
                    "Alerta" -> if (!horarios.isEmpty()) {
                        SetAlerta()
                    } else {
                        Toaster.toast(R.string.msg_horario_err)
                    }
                }
            }
        })
    }

    private fun CargarSemestre(semestre: Int) {
        this.semestre = semestre
        LimpiarLista()
        SetSemestre()
        ObtenerInformacion()
    }

    private fun LimpiarLista() {
        rvModulos!!.removeAllViewsInLayout()
        modulos.clear()
        horarios.clear()
        docentes.clear()
        calificaciones.clear()
        adapterPrincipal?.clear()
    }

    private fun VerDocente(position: Int) {
        val i = Intent(this@PrincipalActivity, DetalleDocenteActivity::class.java)
        i.putExtra("nombre", docentes[position].getNombre().toString() + " " + docentes[position].getApellido())
        i.putExtra("profesion", docentes[position].getProfesion())
        i.putExtra("descripcion", docentes[position].getDescripcion())
        i.putExtra("imagen", docentes[position].getImagen())
        startActivity(i)
    }

    private fun VerModulo(position: Int) {
        val i = Intent(this@PrincipalActivity, DetalleModuloActivity::class.java)
        i.putExtra("nombre", modulos[position].getNombre())
        i.putExtra("descripcion", modulos[position].getDescripcion())
        i.putExtra("creditos", modulos[position].getCreditos())
        startActivity(i)
    }

    private fun VerHorario() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_horarios, null)
        val dimen = LinearLayout.LayoutParams.WRAP_CONTENT
        val popupWindow = PopupWindow(popupView, dimen, dimen, true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 50f
        }

        popupWindow.showAtLocation(tvEspecializacion, Gravity.CENTER, 0, 0)
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }

        val dia1: String = horarios[0].getDia().toString() + " " + horarios[0].getHora_inicio().substring(0, 5) + " - " + horarios[0].getHora_fin().substring(0, 5)
        val dia2: String = horarios[1].getDia().toString() + " " + horarios[1].getHora_inicio().substring(0, 5) + " - " + horarios[1].getHora_fin().substring(0, 5)
        val salon = "Salon " + horarios[0].getSalon()

        tvDiahora1.text = dia1
        tvDiahora2.text = dia2
        tvSede.setText(horarios[0].getSede())
        tvSalon.text = salon
    }

    private fun SetAlerta() {
        val manager = this@PrincipalActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this@PrincipalActivity, AlarmReceiver::class.java)
        alarmIntent.action = "alarma"
        val pendingIntent1 = PendingIntent.getBroadcast(this@PrincipalActivity, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingIntent2 = PendingIntent.getBroadcast(this@PrincipalActivity, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (!estadoalarma) {
            estadoalarma = true
            Snackbar.make(rvModulos!!, "Alerta activada", Snackbar.LENGTH_SHORT).show()
            val intervalo = 1000 * 60 * 60 * 24 * 7
            val viernes = Calendar.getInstance()
            val horaviernes: Int = horarios[0].getHora_inicio().substring(0, 2).toInt() - 1
            viernes.timeInMillis = System.currentTimeMillis()
            viernes[Calendar.DAY_OF_WEEK] = 5
            viernes[Calendar.HOUR_OF_DAY] = horaviernes
            viernes[Calendar.MINUTE] = 30
            manager.setRepeating(AlarmManager.RTC_WAKEUP, viernes.timeInMillis, intervalo.toLong(), pendingIntent1)
            val sabado = Calendar.getInstance()
            val horasabado: Int = horarios[1].getHora_inicio().substring(0, 2).toInt() - 1
            sabado.timeInMillis = System.currentTimeMillis()
            sabado[Calendar.DAY_OF_WEEK] = 6
            sabado[Calendar.HOUR_OF_DAY] = horasabado
            sabado[Calendar.MINUTE] = 30
            manager.setRepeating(AlarmManager.RTC_WAKEUP, sabado.timeInMillis, intervalo.toLong(), pendingIntent2)
        } else {
            estadoalarma = false
            manager.cancel(pendingIntent1)
            manager.cancel(pendingIntent2)
            Snackbar.make(rvModulos!!, "Alerta desactivada", Snackbar.LENGTH_SHORT).show()
        }
    }
}