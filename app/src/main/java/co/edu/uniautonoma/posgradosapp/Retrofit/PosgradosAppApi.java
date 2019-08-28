package co.edu.uniautonoma.posgradosapp.Retrofit;

import co.edu.uniautonoma.posgradosapp.Modelos.Escuela;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PosgradosAppApi {

    @GET("get_escuela_.php")
    Call<Escuela> getEscuela(@Query("id") int id);
}
