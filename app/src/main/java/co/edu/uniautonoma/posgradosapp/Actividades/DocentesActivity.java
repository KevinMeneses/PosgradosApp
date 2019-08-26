package co.edu.uniautonoma.posgradosapp.Actividades;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import co.edu.uniautonoma.posgradosapp.Adaptadores.AdapterDocente;
import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.DocentesViewModel;
import xdroid.toaster.Toaster;

public class DocentesActivity extends AppCompatActivity {

    private ArrayList<Docentes> docentes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docentes);
        String id_posgrado = getIntent().getStringExtra("id_posgrado");

        DocentesViewModel viewModel = ViewModelProviders.of(this).get(DocentesViewModel.class);
        viewModel.EnviarPeticion(id_posgrado);
        if (viewModel.getDocentes()!=null){
            viewModel.getDocentes().observe(this, docentes1 -> {
                docentes = docentes1;
                llenarLista();
            });
        }else {
            Toaster.toast(R.string.EstadoServidor);
        }
    }

    private void llenarLista() {

        ListView lvDocentes = (ListView) findViewById(R.id.lvDocentes);
        lvDocentes.setAdapter(new AdapterDocente(getApplicationContext(), docentes));

        lvDocentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DocentesActivity.this, DetalleDocenteActivity.class);
                i.putExtra("nombre", docentes.get(position).getNombre() + " " + docentes.get(position).getApellido());
                i.putExtra("profesion", docentes.get(position).getProfesion());
                i.putExtra("descripcion", docentes.get(position).getDescripcion());
                i.putExtra("imagen", docentes.get(position).getImagen());
                startActivity(i);
            }
        });
    }
}