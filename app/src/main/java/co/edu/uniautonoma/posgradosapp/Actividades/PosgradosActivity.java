package co.edu.uniautonoma.posgradosapp.Actividades;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import co.edu.uniautonoma.posgradosapp.Modelos.Posgrados;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.PosgradosViewModel;
import xdroid.toaster.Toaster;

public class PosgradosActivity extends AppCompatActivity {

    private ArrayList<Posgrados> posgrados = new ArrayList<>();
    private ArrayList<String> listaposgrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_posgrado);
        PosgradosViewModel viewModel = ViewModelProviders.of(this).get(PosgradosViewModel.class);
        if(viewModel.getAllPosgrados()!=null){
            viewModel.getAllPosgrados().observe(this, posgrado ->{
                posgrados = posgrado;
                llenarPosgrados();
            });
        }else {
            Toaster.toast(R.string.EstadoServidor);
        }
    }

    private void llenarPosgrados() {

        ListView lvPosgrados = (ListView) findViewById(R.id.lvPosgrados);

        for(int i = 0; i< posgrados.size(); i++) {
            listaposgrados.add(posgrados.get(i).getNombre());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.lista_posgrado, R.id.tvlistaposgrado, listaposgrados);
        lvPosgrados.setAdapter(arrayAdapter);

        lvPosgrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(PosgradosActivity.this, DetallePosgradoActivity.class);
                i.putExtra("id_posgrado", posgrados.get(position).getId());
                i.putExtra("cod_snies", posgrados.get(position).getCod_snies());
                i.putExtra("nombre", posgrados.get(position).getNombre());
                i.putExtra("duracion", posgrados.get(position).getDuracion());
                i.putExtra("totalcreditos", posgrados.get(position).getTotalcreditos());
                i.putExtra("descripcion", posgrados.get(position).getDescripcion());
                i.putExtra("valorsemestre", posgrados.get(position).getValorsemestre());
                startActivity(i);
            }
        });
    }
}