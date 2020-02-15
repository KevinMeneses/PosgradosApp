package co.edu.uniautonoma.posgradosapp.presentation.ui.modulos;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import co.edu.uniautonoma.posgradosapp.R;

public class DetalleModuloActivity extends AppCompatActivity {

    TextView tvTituloModulo;
    TextView tvDetalleModulo;
    TextView tvCreditosModulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_modulo);

        tvTituloModulo = (TextView) findViewById(R.id.tvTituloModulo);
        tvDetalleModulo = (TextView) findViewById(R.id.tvDetalleModulo);
        tvCreditosModulo = (TextView) findViewById(R.id.tvCreditosModulo);

        Intent detalles = getIntent();

        tvTituloModulo.setText(detalles.getStringExtra("nombre"));
        tvDetalleModulo.setText(detalles.getStringExtra("descripcion"));
        tvCreditosModulo.setText("Creditos: " + Integer.toString(detalles.getIntExtra("creditos", 0)));
    }

}
