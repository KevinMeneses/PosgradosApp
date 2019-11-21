package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocentesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Docentes>> docentes;
    private MutableLiveData<Boolean> estado = new MutableLiveData<>();

    public DocentesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Docentes>> getDocentes(){
        return docentes;
    }

    public LiveData<Boolean> getEstado(){
        return estado;
    }

    public void EnviarPeticion(String id_posgrado){

        docentes = new MutableLiveData<>();

        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);
        peticionesApi.getAllDocentes(id_posgrado)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Docentes>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        estado.setValue(true);
                    }

                    @Override
                    public void onNext(List<Docentes> response) {
                        docentes.setValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        docentes.setValue(null);
                        estado.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        estado.setValue(false);
                    }
                });

    }
}
