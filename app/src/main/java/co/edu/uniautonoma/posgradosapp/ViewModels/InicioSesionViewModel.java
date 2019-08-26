package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import co.edu.uniautonoma.posgradosapp.Dao.UsuariosDao;
import co.edu.uniautonoma.posgradosapp.Modelos.Usuarios;
import co.edu.uniautonoma.posgradosapp.R;

public class InicioSesionViewModel extends AndroidViewModel {

    private UsuariosDao usuariosDao = new UsuariosDao();
    private MutableLiveData<Usuarios> usuario;
    private String url = getApplication().getResources().getString(R.string.BASE_URL);

    public InicioSesionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuarios> getUsuario() {
        return usuario;
    }

    public void EnviarPeticion(String correo){
        usuario = new MutableLiveData<>();
        usuario.postValue(usuariosDao.getUsuario(url, correo));
    }
}