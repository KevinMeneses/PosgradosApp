package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;

import co.edu.uniautonoma.posgradosapp.Dao.EscuelaDao;
import co.edu.uniautonoma.posgradosapp.Modelos.Escuela;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.Retrofit.PosgradosAppApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class EscuelaViewModel extends AndroidViewModel {

    private EscuelaDao escuelaDao = new EscuelaDao();
    private String url = getApplication().getResources().getString(R.string.BASE_URL);
    private MutableLiveData<Escuela> escuela;

    public EscuelaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Escuela> getEscuela(){
        //EnviarPeticion();
        getEscuelaApi();
        return escuela;
    }

    private void EnviarPeticion(){
        escuela = new MutableLiveData<>();
        escuela.setValue(escuelaDao.getEscuela(url));
    }

    private void getEscuelaApi() {

        escuela = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PosgradosAppApi posgradosAppApi = retrofit.create(PosgradosAppApi.class);
        Call<Escuela> call = posgradosAppApi.getEscuela(1);

        call.enqueue(new Callback<Escuela>() {
            @Override
            public void onResponse(Call<Escuela> call, Response<Escuela> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    escuela.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Escuela> call, Throwable t) {

            }
        });
    }

}
