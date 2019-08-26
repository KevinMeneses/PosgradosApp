package co.edu.uniautonoma.posgradosapp.Actividades;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import co.edu.uniautonoma.posgradosapp.R;

public class DetallePosgradoActivity extends AppCompatActivity {

    TextView tvNombreposgrado;
    TextView tvCod_snies;
    TextView tvTotalcreditos;
    TextView tvDuracion;
    TextView tvValorsemestre;
    TextView tvDescripcionposgrado;
    TextView tvOpModulos;
    TextView tvOpDocentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_posgrado);

        tvNombreposgrado = (TextView) findViewById(R.id.tvNombreposgrado);
        tvCod_snies = (TextView) findViewById(R.id.tvCod_snies);
        tvTotalcreditos = (TextView) findViewById(R.id.tvTotalcreditos);
        tvDuracion = (TextView) findViewById(R.id.tvDuracion);
        tvValorsemestre = (TextView) findViewById(R.id.tvValorsemestre);
        tvDescripcionposgrado = (TextView) findViewById(R.id.tvDescripcionposgrado);

        tvNombreposgrado.setText(getIntent().getStringExtra("nombre"));
        tvCod_snies.setText("Código snies: " + getIntent().getStringExtra("cod_snies"));
        tvTotalcreditos.setText("Total créditos: " + Integer.toString(getIntent().getIntExtra("totalcreditos", 0)));
        tvDuracion.setText("Duración: " + getIntent().getStringExtra("duracion"));
        tvValorsemestre.setText("Valor semestre: " + getIntent().getStringExtra("valorsemestre"));
        tvDescripcionposgrado.setText(getIntent().getStringExtra("descripcion"));

        tvOpModulos = (TextView) findViewById(R.id.tvOpModulos);
        tvOpDocentes = (TextView) findViewById(R.id.tvOpDocentes);

        tvOpModulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetallePosgradoActivity.this, ModulosActivity.class);
                i.putExtra("id_posgrado", getIntent().getStringExtra("id_posgrado"));
                i.putExtra("semestre", 3);
                startActivity(i);
            }
        });

        tvOpDocentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetallePosgradoActivity.this, DocentesActivity.class);
                i.putExtra("id_posgrado", getIntent().getStringExtra("id_posgrado"));
                startActivity(i);
            }
        });

    }
}
