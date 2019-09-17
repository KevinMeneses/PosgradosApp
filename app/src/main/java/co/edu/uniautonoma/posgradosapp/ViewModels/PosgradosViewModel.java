package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.edu.uniautonoma.posgradosapp.Modelos.Posgrados;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PosgradosViewModel extends AndroidViewModel {

    private MutableLiveData <List<Posgrados>> posgrados;
    private MutableLiveData<Boolean> estado = new MutableLiveData<>();

    public PosgradosViewModel(@NonNull Application application) {
        super(application);
        ObtenerPosgrados();
    }

    public LiveData<List<Posgrados>> getAllPosgrados(){
        return posgrados;
    }

    public LiveData<Boolean> getEstado(){
        return estado;
    }

    private void ObtenerPosgrados(){

        posgrados = new MutableLiveData<>();

        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);
        peticionesApi.getAllPosgrados()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Posgrados>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        estado.setValue(true);
                    }
                    @Override
                    public void onNext(List<Posgrados> response) {
                        posgrados.setValue(response);
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
