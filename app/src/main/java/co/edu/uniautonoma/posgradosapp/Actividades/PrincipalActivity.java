package co.edu.uniautonoma.posgradosapp.Actividades;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import co.edu.uniautonoma.posgradosapp.Adaptadores.AdapterPrincipal;
import co.edu.uniautonoma.posgradosapp.Helper.AlarmReceiver;
import co.edu.uniautonoma.posgradosapp.Modelos.Calificaciones;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.Modelos.Horarios;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.PrincipalViewModel;
import xdroid.toaster.Toaster;

public class PrincipalActivity extends AppCompatActivity {

    private TextView tvEspecializacion;
    private TextView tvSemestre;
    private RecyclerView rvModulos;

    private AdapterPrincipal adapterPrincipal;
    private ArrayList<Modulos> modulos = new ArrayList<>();
    private ArrayList<Docentes> docentes = new ArrayList<>();
    private ArrayList<Horarios> horarios = new ArrayList<>();
    private ArrayList<Calificaciones> calificaciones = new ArrayList<>();

    private String id_usuario;
    private String id_posgrado;
    private int semestre;

    private Boolean estadoalarma = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        id_usuario = getIntent().getStringExtra("id_usuario");
        semestre = getIntent().getIntExtra("semestre",0);
        id_posgrado = getIntent().getStringExtra("id_posgrado");

        tvEspecializacion = (TextView) findViewById(R.id.tvEspecializacion);

        rvModulos = (RecyclerView) findViewById(R.id.rvModulos);

        Button btSemestre1 = (Button) findViewById(R.id.btSemestre1);
        btSemestre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(semestre !=1){
                    CargarSemestre(1);
                }
            }
        });

        Button btSemestre2 = (Button) findViewById(R.id.btSemestre2);
        btSemestre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(semestre !=2) {
                    CargarSemestre(2);
                }
            }
        });

        ObtenerInformacion();
    }

    private void ObtenerSemestre() {
        tvSemestre = findViewById(R.id.tvSemestre);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(semestre == 1)
                {tvSemestre.setText("PRIMER SEMESTRE");}
                else
                {tvSemestre.setText("SEGUNDO SEMESTRE");}
            }});
    }

    private void ObtenerInformacion(){

        ObtenerSemestre();
        adapterPrincipal = new AdapterPrincipal();

        PrincipalViewModel viewModel = ViewModelProviders.of(this).get(PrincipalViewModel.class);
        viewModel.EnviarPeticion(id_posgrado,semestre,id_usuario);

        if(viewModel.getInformacion()!=null){
            viewModel.getInformacion().observe(this, informacion -> {
                String nombreposgrado = informacion.getPosgrados().getNombre();
                tvEspecializacion.setText(nombreposgrado);
                modulos = informacion.getModulos();
                docentes = informacion.getDocente();
                calificaciones = informacion.getCalificaciones();
                horarios = informacion.getHorario();
                adapterPrincipal.setInfo(modulos,docentes,calificaciones);
            });
        }else{
            Toaster.toast(R.string.EstadoServidor);
        }

        LlenarLista();
    }

    private void LlenarLista() {

        rvModulos.setLayoutManager(new LinearLayoutManager(this));

        rvModulos.setAdapter(adapterPrincipal);

        adapterPrincipal.setClickListener(new AdapterPrincipal.ClickListener() {
            @Override
            public void onItemClicked(int position, String tag) {
                switch (tag){
                    case "Docentes":
                        VerDocente(position);
                        break;
                    case "Modulos":
                        VerModulo(position);
                        break;
                    case "Horarios":
                        if(!horarios.isEmpty()) {
                        VerHorario();
                        }else{
                            Toaster.toast(R.string.msg_horario_err);}
                        break;
                    case "Alerta":
                        if(!horarios.isEmpty()) {
                        SetAlerta();
                        }else{
                            Toaster.toast(R.string.msg_horario_err);}
                        break;
                }
            }
        });
    }

    private void CargarSemestre(int semestre){
        this.semestre = semestre;
        LimpiarLista();
        ObtenerInformacion();
    }

    private void LimpiarLista(){
        rvModulos.removeAllViewsInLayout();
        modulos.clear();
        horarios.clear();
        docentes.clear();
        calificaciones.clear();
        adapterPrincipal.clear();
    }

    private void VerDocente(int position){
        Intent i = new Intent(PrincipalActivity.this, DetalleDocenteActivity.class);
        i.putExtra("nombre", docentes.get(position).getNombre() + " " + docentes.get(position).getApellido());
        i.putExtra("profesion", docentes.get(position).getProfesion());
        i.putExtra("descripcion", docentes.get(position).getDescripcion());
        i.putExtra("imagen", docentes.get(position).getImagen());
        startActivity(i);
    }

    private void VerModulo(int position){
        Intent i = new Intent(PrincipalActivity.this, DetalleModuloActivity.class);
        i.putExtra("nombre", modulos.get(position).getNombre());
        i.putExtra("descripcion", modulos.get(position).getDescripcion());
        i.putExtra("creditos", modulos.get(position).getCreditos());
        startActivity(i);
    }

    private void VerHorario() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_horarios, null);
        int dimen = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, dimen, dimen, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(50);
        }

        popupWindow.showAtLocation(tvEspecializacion, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        String dia1 = horarios.get(0).getDia() + " " + horarios.get(0).getHora_inicio().substring(0, 5) + " - " + horarios.get(0).getHora_fin().substring(0, 5);
        String dia2 = horarios.get(1).getDia() + " " + horarios.get(1).getHora_inicio().substring(0, 5) + " - " + horarios.get(1).getHora_fin().substring(0, 5);
        String salon = "Salon " + horarios.get(0).getSalon();

        TextView tvDiahora1 = (TextView) popupView.findViewById(R.id.tvDiahora1);
        TextView tvDiahora2 = (TextView) popupView.findViewById(R.id.tvDiahora2);
        TextView tvSede = (TextView) popupView.findViewById(R.id.tvSede);
        TextView tvSalon = (TextView) popupView.findViewById(R.id.tvSalon);

        tvDiahora1.setText(dia1);
        tvDiahora2.setText(dia2);
        tvSede.setText(horarios.get(0).getSede());
        tvSalon.setText(salon);
    }

    private void SetAlerta() {

        AlarmManager manager = (AlarmManager) PrincipalActivity.this.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(PrincipalActivity.this, AlarmReceiver.class);
        alarmIntent.setAction("alarma");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(PrincipalActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(PrincipalActivity.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (!estadoalarma){
            estadoalarma = true;
            Snackbar.make(rvModulos,"Alerta activada",Snackbar.LENGTH_SHORT).show();
            int intervalo = 1000*60*60*24*7;

            Calendar viernes = Calendar.getInstance();
            int horaviernes = Integer.parseInt(horarios.get(0).getHora_inicio().substring(0,2))-1;

            viernes.setTimeInMillis(System.currentTimeMillis());
            viernes.set(Calendar.DAY_OF_WEEK, 5);
            viernes.set(Calendar.HOUR_OF_DAY, horaviernes);
            viernes.set(Calendar.MINUTE, 30);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, viernes.getTimeInMillis(), intervalo, pendingIntent1);

            Calendar sabado = Calendar.getInstance();
            int horasabado = Integer.parseInt(horarios.get(1).getHora_inicio().substring(0,2))-1;

            sabado.setTimeInMillis(System.currentTimeMillis());
            sabado.set(Calendar.DAY_OF_WEEK, 6);
            sabado.set(Calendar.HOUR_OF_DAY, horasabado);
            sabado.set(Calendar.MINUTE, 30);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, sabado.getTimeInMillis(), intervalo, pendingIntent2);
        }
        else{
            estadoalarma = false;
            manager.cancel(pendingIntent1);
            manager.cancel(pendingIntent2);
            Snackbar.make(rvModulos,"Alerta desactivada",Snackbar.LENGTH_SHORT).show();
        }
    }

}