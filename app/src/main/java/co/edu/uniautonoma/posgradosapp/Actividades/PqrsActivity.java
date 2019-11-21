package co.edu.uniautonoma.posgradosapp.Actividades;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.PqrsViewModel;
import xdroid.toaster.Toaster;

public class PqrsActivity extends AppCompatActivity {

    private EditText etComentario;
    private String destinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pqrs);

        etComentario = (EditText) findViewById(R.id.etComentario);
        Button btEnviar = (Button) findViewById(R.id.btEnviar);

        PqrsViewModel viewModel = ViewModelProviders.of(this).get(PqrsViewModel.class);
        if (viewModel.getEscuela()!=null){
            viewModel.getEscuela().observe(this, escuela -> {
                destinatario = escuela.getCorreo();
            });
        }else {
            Toaster.toast(R.string.EstadoServidor);
        }

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                String comentario = etComentario.getText().toString();

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.asuntopqrs));
                emailIntent.putExtra(Intent.EXTRA_TEXT, comentario);
                emailIntent.setType("message/rfc822");

                startActivity(Intent.createChooser(emailIntent,"Email"));
            }
        });
    }
}
