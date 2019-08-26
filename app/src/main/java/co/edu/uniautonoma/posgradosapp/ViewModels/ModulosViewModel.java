package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import co.edu.uniautonoma.posgradosapp.Dao.ModulosDao;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.R;

public class ModulosViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Modulos>> modulos;
    private ModulosDao modulosDao = new ModulosDao();
    private String url = getApplication().getResources().getString(R.string.BASE_URL);

    public ModulosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Modulos>> getModulos(){
        return modulos;
    }

    public void ObtenerModulos(String id_posgrado){
        modulos = new MutableLiveData<>();
        modulos.setValue(modulosDao.getAllModulos(url, id_posgrado));
    }
}
