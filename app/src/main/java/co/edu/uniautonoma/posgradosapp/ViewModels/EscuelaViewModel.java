package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import co.edu.uniautonoma.posgradosapp.Dao.EscuelaDao;
import co.edu.uniautonoma.posgradosapp.Modelos.Escuela;
import co.edu.uniautonoma.posgradosapp.R;

public class EscuelaViewModel extends AndroidViewModel {

    private EscuelaDao escuelaDao = new EscuelaDao();
    private String url = getApplication().getResources().getString(R.string.BASE_URL);
    private MutableLiveData<Escuela> escuela;

    public EscuelaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Escuela> getEscuela(){
        EnviarPeticion();
        return escuela;
    }

    private void EnviarPeticion(){
        escuela = new MutableLiveData<>();
        escuela.setValue(escuelaDao.getEscuela(url));
    }

}
