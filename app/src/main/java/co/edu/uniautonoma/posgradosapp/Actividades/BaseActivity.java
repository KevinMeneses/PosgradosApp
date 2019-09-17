package co.edu.uniautonoma.posgradosapp.Actividades;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando...");
        dialog.setCancelable(true);
        dialog.setIndeterminate(true);
    }

    protected void mostrarDialog() {

        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    protected void ocultarDialog() {

        if(dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
