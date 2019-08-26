package co.edu.uniautonoma.posgradosapp.Actividades;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import co.edu.uniautonoma.posgradosapp.R;

public class DetalleDocenteActivity extends AppCompatActivity {

    ImageView ivFoto;
    TextView tvNombre;
    TextView tvProfesion;
    TextView tvDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_docente);

        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvProfesion = (TextView) findViewById(R.id.tvProfesion);
        tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);

        Intent docente = getIntent();

        Glide.with(DetalleDocenteActivity.this).load(docente.getStringExtra("imagen")).into(ivFoto);
        tvNombre.setText(docente.getStringExtra("nombre"));
        tvProfesion.setText(docente.getStringExtra("profesion"));
        tvDescripcion.setText(docente.getStringExtra("descripcion"));

    }
}
