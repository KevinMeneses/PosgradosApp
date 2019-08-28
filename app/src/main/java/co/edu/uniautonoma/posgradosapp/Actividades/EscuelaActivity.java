package co.edu.uniautonoma.posgradosapp.Actividades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.EscuelaViewModel;
import xdroid.toaster.Toaster;

public class EscuelaActivity extends BaseActivity {

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

        mostrarDialog();
        escuelaViewModel = ViewModelProviders.of(this).get(EscuelaViewModel.class);

        if(escuelaViewModel.getEscuela()!=null) {
            escuelaViewModel.getEscuela().observe(this, escuela -> {
                tvDescripcionescuela.setText(escuela.getDescripcion());
                String director = "Director: " + escuela.getDirector();
                tvDirector.setText(director);
            });
        }else{
            ocultarDialog();
            Toaster.toast(R.string.EstadoServidor);
        }
        ocultarDialog();
    }
}
