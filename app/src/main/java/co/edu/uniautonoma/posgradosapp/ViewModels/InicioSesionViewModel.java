package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import co.edu.uniautonoma.posgradosapp.Modelos.Usuarios;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InicioSesionViewModel extends AndroidViewModel {

    private MutableLiveData<Usuarios> usuario;

    public InicioSesionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuarios> getUsuario() {
        return usuario;
    }

    public void EnviarPeticion(String correo){

        usuario = new MutableLiveData<>();

        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);
        peticionesApi.getUsuario(correo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Usuarios>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Usuarios usuarios) {
                        usuario.setValue(usuarios);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });

    }
}