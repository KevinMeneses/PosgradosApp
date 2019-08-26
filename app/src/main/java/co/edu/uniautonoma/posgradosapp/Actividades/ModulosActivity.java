package co.edu.uniautonoma.posgradosapp.Actividades;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.ModulosViewModel;
import xdroid.toaster.Toaster;

public class ModulosActivity extends AppCompatActivity {

    private ArrayList<Modulos> modulos = new ArrayList<>();
    private ArrayList<String> nombremodulos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulos);

        String id_posgrado = getIntent().getStringExtra("id_posgrado");

        ModulosViewModel viewModel = ViewModelProviders.of(this).get(ModulosViewModel.class);
        viewModel.ObtenerModulos(id_posgrado);
        if(viewModel.getModulos()!=null){
            viewModel.getModulos().observe(this, modulos1 -> {
                modulos = modulos1;
                llenarLista();
            });
        }else {
            Toaster.toast(R.string.EstadoServidor);
        }
    }

    private void llenarLista() {

        ListView lvModulos = (ListView) findViewById(R.id.lvModulos);

        for(int i = 0; i< modulos.size(); i++) {
            nombremodulos.add(modulos.get(i).getNombre());
        }

        lvModulos.setAdapter(new ArrayAdapter<String>(this, R.layout.lista_modulos, R.id.tvModulo, nombremodulos));

        lvModulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ModulosActivity.this, DetalleModuloActivity.class);
                i.putExtra("nombre", modulos.get(position).getNombre());
                i.putExtra("descripcion", modulos.get(position).getDescripcion());
                i.putExtra("creditos", modulos.get(position).getCreditos());
                startActivity(i);
            }
        });
    }
}
