package co.edu.uniautonoma.posgradosapp.presentation.ui.principal

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.uniautonoma.posgradosapp.domain.entity.Informacion
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.adapter.AdapterPrincipal
import co.edu.uniautonoma.posgradosapp.presentation.helper.AlarmReceiver
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.docentes.DetalleDocenteActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.modulos.DetalleModuloActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.popup_horarios.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xdroid.toaster.Toaster
import java.util.*

class PrincipalActivity : BaseActivity() {

    private val principalViewModel: PrincipalViewModel by viewModel()
    private var adapterPrincipal: AdapterPrincipal? = null
    private var informacion: Informacion? =null
    private var semestre = 0
    private var estadoalarma = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        mostrarDialog()
        semestreClickListener()
        setSemestre()
        enviarPeticion()
        observarResultado()
        modulosClickListener()
    }

    private fun semestreClickListener() {
        btSemestre1.setOnClickListener {
            if (semestre != 1) {
                cargarSemestre(1)
            }
        }

        btSemestre2.setOnClickListener {
            if (semestre != 2) {
                cargarSemestre(2)
            }
        }
    }

    private fun setSemestre() {
        runOnUiThread {
            if (semestre == 1) {
                tvSemestre.setText("PRIMER SEMESTRE")
            } else {
                tvSemestre.setText("SEGUNDO SEMESTRE")
            }
        }
    }

    private fun enviarPeticion() {

        val id_usuario = intent.getStringExtra("id_usuario")
        val semestre = intent.getIntExtra("semestre", 0)
        val id_posgrado = intent.getStringExtra("id_posgrado")

        id_usuario?.let {it ->
            id_posgrado?.let {it2 ->
                principalViewModel.getPrincipal(it2, semestre, it)
            }
        }

    }

    private fun observarResultado() {

        principalViewModel.informacionLiveData.observe(this, Observer {
            informacion = it
            tvEspecializacion.setText(it.posgrados.nombre)
            llenarLista()
            ocultarDialog()
        })

        principalViewModel.errorLiveData.observe(this, Observer {
            ocultarDialog()
            Toaster.toast(R.string.EstadoServidor)
            Log.d("PrincipalError:", it)
        })
    }

    private fun modulosClickListener() {
        adapterPrincipal?.setClickListener(object : AdapterPrincipal.ClickListener {
            override fun onItemClicked(position: Int, tag: String?) {
                when (tag) {
                    "Docentes" -> verDocente(position)
                    "Modulos" -> verModulo(position)
                    "Horarios" ->
                        if (!informacion?.horario?.isEmpty()!!) verHorario()
                        else Toaster.toast(R.string.msg_horario_err)
                    "Alerta" ->
                        if (!informacion?.horario?.isEmpty()!!) setAlarma()
                        else Toaster.toast(R.string.msg_horario_err)
                }
            }
        })
    }

    private fun llenarLista() {
        adapterPrincipal = AdapterPrincipal()
        adapterPrincipal?.setInformacion(informacion)
        rvModulos.layoutManager = LinearLayoutManager(this)
        rvModulos.adapter = adapterPrincipal
    }

    private fun cargarSemestre(semestre: Int) {
        this.semestre = semestre
        limpiarLista()
        setSemestre()
        enviarPeticion()
    }

    private fun limpiarLista() {
        rvModulos.removeAllViewsInLayout()
        adapterPrincipal?.clear()
    }

    private fun verDocente(position: Int) {
        val i = Intent(this@PrincipalActivity, DetalleDocenteActivity::class.java)
        informacion?.run {
            i.putExtra("nombre", docente[position].nombre + " " + docente[position].apellido)
            i.putExtra("profesion", docente[position].profesion)
            i.putExtra("descripcion", docente[position].descripcion)
            i.putExtra("imagen", docente[position].imagen)
        }
        startActivity(i)
    }

    private fun verModulo(position: Int) {
        val i = Intent(this@PrincipalActivity, DetalleModuloActivity::class.java)
        informacion?.run {
            i.putExtra("nombre", modulos[position].nombre)
            i.putExtra("descripcion", modulos[position].descripcion)
            i.putExtra("creditos", modulos[position].creditos)
        }
        startActivity(i)
    }

    private fun verHorario() {
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

        informacion?.run {
            val dia1: String = horario[0].dia + " " + horario[0].hora_inicio.substring(0, 5) + " - " + horario[0].hora_fin.substring(0, 5)
            val dia2: String = horario[1].dia + " " + horario[1].hora_inicio.substring(0, 5) + " - " + horario[1].hora_fin.substring(0, 5)
            val salon = "Salon " + horario[0].salon

            tvDiahora1.text = dia1
            tvDiahora2.text = dia2
            tvSede.setText(horario[0].sede)
            tvSalon.text = salon
        }
    }

    private fun setAlarma() {

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
            val horaviernes: Int = informacion!!.horario[0].hora_inicio.substring(0, 2).toInt() - 1
            viernes.timeInMillis = System.currentTimeMillis()
            viernes[Calendar.DAY_OF_WEEK] = 5
            viernes[Calendar.HOUR_OF_DAY] = horaviernes
            viernes[Calendar.MINUTE] = 30
            manager.setRepeating(AlarmManager.RTC_WAKEUP, viernes.timeInMillis, intervalo.toLong(), pendingIntent1)

            val sabado = Calendar.getInstance()
            val horasabado: Int = informacion!!.horario[1].hora_inicio.substring(0, 2).toInt() - 1
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