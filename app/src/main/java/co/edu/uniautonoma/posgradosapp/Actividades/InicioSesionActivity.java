package co.edu.uniautonoma.posgradosapp.Actividades;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import co.edu.uniautonoma.posgradosapp.Helper.RevisarEstadoRed;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.InicioSesionViewModel;
import xdroid.toaster.Toaster;

public class InicioSesionActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 101;
    private static final String TAG = "AndroidClarified";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        TextView tvEnlace = (TextView) findViewById(R.id.tvEnlace);
        tvEnlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uniautonoma.edu.co"));
                startActivity(browserIntent);
            }
        });

        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RevisarEstadoRed.ConexionInternet(getApplicationContext())) {
                    signIn();
                }else{
                    Toaster.toast(R.string.EstadoInternet);
                }
            }
        });

        LinearLayout lyEscuela = (LinearLayout) findViewById(R.id.lyEscuela);
        lyEscuela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RevisarEstadoRed.ConexionInternet(getApplicationContext())) {
                    Intent i = new Intent(InicioSesionActivity.this, EscuelaActivity.class);
                    startActivity(i);
                }else{
                    Toaster.toast(R.string.EstadoInternet);
                }
            }
        });

        LinearLayout lyUbicacion = (LinearLayout) findViewById(R.id.lyUbicacion);
        lyUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RevisarEstadoRed.ConexionInternet(getApplicationContext())) {
                    Intent i = new Intent(InicioSesionActivity.this, UbicacionActivity.class);
                    startActivity(i);
                }else{
                    Toaster.toast(R.string.EstadoInternet);
                }
            }
        });

        LinearLayout lyEspecializaciones = (LinearLayout) findViewById(R.id.lyEspecializaciones);
        lyEspecializaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RevisarEstadoRed.ConexionInternet(getApplicationContext())) {
                    Intent i = new Intent(InicioSesionActivity.this, PosgradosActivity.class);
                    startActivity(i);
                }else{
                    Toaster.toast(R.string.EstadoInternet);
                }
            }
        });

        ImageView ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/uniautonomadc"));
                startActivity(browserIntent);
            }
        });

        ImageView ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/uniautonomadc"));
                startActivity(browserIntent);
            }
        });

        ImageView ivInstagram = (ImageView) findViewById(R.id.ivInstagram);
        ivInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/uniautonomadelcauca"));
                startActivity(browserIntent);
            }
        });

        ImageView ivPqrs = (ImageView) findViewById(R.id.ivPqrs);
        ivPqrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RevisarEstadoRed.ConexionInternet(getApplicationContext())) {
                    Intent i = new Intent(InicioSesionActivity.this, PqrsActivity.class);
                    startActivity(i);
                }else{
                    Toaster.toast(R.string.EstadoInternet);
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            IniciarSesion(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            IniciarSesion(null);
        }
    }

    private void IniciarSesion(GoogleSignInAccount correo){
        if (correo!=null){
            InicioSesionViewModel usuario = ViewModelProviders.of(this).get(InicioSesionViewModel.class);
            usuario.EnviarPeticion(correo.getEmail());
            if(usuario.getUsuario()!=null){
                usuario.getUsuario().observe(this, item -> {
                    Intent i = new Intent(InicioSesionActivity.this, PrincipalActivity.class);
                    i.putExtra("id_usuario", item.getCodigo());
                    i.putExtra("id_posgrado", item.getId_posgrado());
                    i.putExtra("semestre", item.getSemestre());
                    Toaster.toast(item.getNombre() + " " + item.getApellido() + "\n" + getResources().getString(R.string.msg_inicio_exito));
                    startActivity(i);
                });
            }
            else{
                Toaster.toast(R.string.EstadoServidor);
            }
        }else{
            Toaster.toast(R.string.msg_correo_error);
        }
    }
}
