package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import co.edu.uniautonoma.posgradosapp.Modelos.Escuela;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UbicacionViewModel extends AndroidViewModel {

    private String url = getApplication().getResources().getString(R.string.BASE_URL);
    private MutableLiveData<Escuela> escuela;
    private MutableLiveData<Boolean> estado = new MutableLiveData<>();

    public UbicacionViewModel(@NonNull Application application) {
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
                        escuela.setValue(null);
                        estado.setValue(false);
                    }
                    @Override
                    public void onComplete() {
                        estado.setValue(false);
                    }
                });
    }
}
