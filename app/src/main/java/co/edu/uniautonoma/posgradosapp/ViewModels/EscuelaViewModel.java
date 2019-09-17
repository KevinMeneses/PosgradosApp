package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import co.edu.uniautonoma.posgradosapp.Modelos.Escuela;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscuelaViewModel extends AndroidViewModel {

    private MutableLiveData<Escuela> escuela;
    private MutableLiveData<Boolean> estado = new MutableLiveData<>();

    public EscuelaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Escuela> getEscuela(){
        EnviarPeticion();
        return escuela;
    }

    public LiveData<Boolean> getEstado(){
        return estado;
    }

    private void EnviarPeticion() {

        escuela = new MutableLiveData<>();

        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);
        peticionesApi.getEscuela(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Escuela>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        estado.setValue(true);
                    }

                    @Override
                    public void onNext(Escuela response) {
                        escuela.setValue(response);
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
