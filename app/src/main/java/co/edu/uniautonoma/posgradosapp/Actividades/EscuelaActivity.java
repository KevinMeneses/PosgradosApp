package co.edu.uniautonoma.posgradosapp.Actividades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.EscuelaViewModel;
import xdroid.toaster.Toaster;

public class EscuelaActivity extends AppCompatActivity {

    EscuelaViewModel escuelaViewModel;
    TextView tvDescripcionescuela;
    TextView tvDirector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuela);

        tvDescripcionescuela = (TextView) findViewById(R.id.tvDescripcionescuela);
        tvDirector = (TextView) findViewById(R.id.tvDirector);

        ObtenerEscuela();
    }

    private void ObtenerEscuela(){
        escuelaViewModel = ViewModelProviders.of(this).get(EscuelaViewModel.class);
        if(escuelaViewModel.getEscuela()!=null) {
            escuelaViewModel.getEscuela().observe(this, escuela -> {
                tvDescripcionescuela.setText(escuela.getDescripcion());
                tvDirector.setText(escuela.getDirector());
            });
        }else{
            Toaster.toast(R.string.EstadoServidor);
        }
    }
}
