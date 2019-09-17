package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ModulosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Modulos>> modulos;
    private MutableLiveData<Boolean> estado = new MutableLiveData<>();

    public ModulosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Modulos>> getModulos(){
        return modulos;
    }

    public LiveData<Boolean> getEstado(){
        return estado;
    }

    public void ObtenerModulos(String id_posgrado){

        modulos = new MutableLiveData<>();

        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);
        peticionesApi.getAllModulos(id_posgrado)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Modulos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        estado.setValue(true);
                    }

                    @Override
                    public void onNext(List<Modulos> response) {
                        modulos.setValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        estado.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        estado.setValue(false);
                    }
                });

    }
}
