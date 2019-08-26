package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import co.edu.uniautonoma.posgradosapp.Dao.DocentesDao;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.R;

public class DocentesViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Docentes>> docentes;
    private String url = getApplication().getResources().getString(R.string.BASE_URL);
    private DocentesDao docentesDao = new DocentesDao();

    public DocentesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Docentes>> getDocentes(){
        return docentes;
    }

    public void EnviarPeticion(String id_posgrado){
        docentes = new MutableLiveData<>();
        docentes.setValue(docentesDao.getAllDocentes(url, id_posgrado));
    }
}
