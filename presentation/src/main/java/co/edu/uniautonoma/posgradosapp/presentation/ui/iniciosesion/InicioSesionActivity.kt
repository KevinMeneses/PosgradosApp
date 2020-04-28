package co.edu.uniautonoma.posgradosapp.presentation.ui.iniciosesion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import co.edu.uniautonoma.posgradosapp.domain.entity.Usuario
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.helper.RevisarEstadoRed
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.escuela.EscuelaActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.posgrados.PosgradosActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.pqrs.PqrsActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.principal.PrincipalActivity
import co.edu.uniautonoma.posgradosapp.presentation.ui.ubicacion.UbicacionActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_inicio_sesion.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xdroid.toaster.Toaster

class InicioSesionActivity : BaseActivity() {

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 101

    private val inicioSesionViewModel: InicioSesionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        onClickListener()
    }

    private fun onClickListener() {
        tvEnlace.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uniautonoma.edu.co"))
            startActivity(browserIntent)
        }

        btLogin.setOnClickListener {
            if (RevisarEstadoRed.ConexionInternet(applicationContext)) {
                mostrarDialog()
                signIn()
            } else {
                Toaster.toast(R.string.EstadoInternet)
            }
        }

        lyEscuela.setOnClickListener {
            if (RevisarEstadoRed.ConexionInternet(applicationContext)) {
                val i = Intent(this@InicioSesionActivity, EscuelaActivity::class.java)
                startActivity(i)
            } else {
                Toaster.toast(R.string.EstadoInternet)
            }
        }

        lyUbicacion.setOnClickListener {
            if (RevisarEstadoRed.ConexionInternet(applicationContext)) {
                val i = Intent(this@InicioSesionActivity, UbicacionActivity::class.java)
                startActivity(i)
            } else {
                Toaster.toast(R.string.EstadoInternet)
            }
        }

        lyEspecializaciones.setOnClickListener {
            if (RevisarEstadoRed.ConexionInternet(applicationContext)) {
                val i = Intent(this@InicioSesionActivity, PosgradosActivity::class.java)
                startActivity(i)
            } else {
                Toaster.toast(R.string.EstadoInternet)
            }
        }

        ivFacebook.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/uniautonomadc"))
            startActivity(browserIntent)
        }

        ivTwitter.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/uniautonomadc"))
            startActivity(browserIntent)
        }

        ivInstagram.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/uniautonomadelcauca"))
            startActivity(browserIntent)
        }

        ivPqrs.setOnClickListener {
            if (RevisarEstadoRed.ConexionInternet(applicationContext)) {
                val i = Intent(this@InicioSesionActivity, PqrsActivity::class.java)
                startActivity(i)
            } else {
                Toaster.toast(R.string.EstadoInternet)
            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val correo = completedTask.getResult(ApiException::class.java)
            hacerPeticion(correo)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            hacerPeticion(null)
        }
    }

    private fun hacerPeticion(correo: GoogleSignInAccount?) {
        if (correo != null) {
            inicioSesionViewModel.enviarPeticion(correo.email!!)
            observarResultados()
        }
        else {
            Toaster.toast(R.string.msg_correo_error)
        }
    }

    private fun observarResultados() {
        inicioSesionViewModel.usuarioLiveData.observe(this, Observer {
            it?.let {
                ocultarDialog()
                iniciarSesion(it)
            }
        })

        inicioSesionViewModel.errorLiveData.observe(this, Observer {
            it?.let {
                ocultarDialog()
                Toaster.toast(R.string.EstadoServidor)
                Log.d("InicioSesionError:", it)
            }
        })
    }

    private fun iniciarSesion(it: Usuario) {
        val i = Intent(this@InicioSesionActivity, PrincipalActivity::class.java)
        i.putExtra("id_usuario", it.codigo)
        i.putExtra("id_posgrado", it.id_posgrado)
        i.putExtra("semestre", it.semestre)
        Toaster.toast(it.nombre + " " + it.apellido + "\n" + resources.getString(R.string.msg_inicio_exito))
        startActivity(i)
    }

    companion object {
        private const val TAG = "AndroidClarified"
    }
}