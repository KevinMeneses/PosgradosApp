package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import co.edu.uniautonoma.posgradosapp.Dao.PosgradosDao;
import co.edu.uniautonoma.posgradosapp.Modelos.Posgrados;
import co.edu.uniautonoma.posgradosapp.R;

public class PosgradosViewModel extends AndroidViewModel {

    private MutableLiveData <ArrayList<Posgrados>> posgrados;
    private String url = getApplication().getResources().getString(R.string.BASE_URL);
    private PosgradosDao posgradosDao = new PosgradosDao();

    public PosgradosViewModel(@NonNull Application application) {
        super(application);
        ObtenerPosgrados();
    }

    public LiveData<ArrayList<Posgrados>> getAllPosgrados(){
        return posgrados;
    }

    private void ObtenerPosgrados(){
        posgrados = new MutableLiveData<>();
        posgrados.setValue(posgradosDao.getAllPosgrados(url));
    }

}
