package co.edu.uniautonoma.posgradosapp.Retrofit;

import java.util.List;

import co.edu.uniautonoma.posgradosapp.Modelos.Calificaciones;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.Modelos.Escuela;
import co.edu.uniautonoma.posgradosapp.Modelos.Horarios;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.Modelos.Posgrados;
import co.edu.uniautonoma.posgradosapp.Modelos.Usuarios;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PeticionesApi {

    @GET("get_escuela_.php")
    Observable<Escuela> getEscuela(@Query("id") int id);

    @GET("get_usuario_.php")
    Observable<Usuarios> getUsuario(@Query("correo") String correo);

    @GET("get_all_posgrados_.php")
    Observable<List<Posgrados>> getAllPosgrados();

    @GET("get_all_modulos_.php")
    Observable<List<Modulos>> getAllModulos(@Query("id_posgrado") String id_posgrado);

    @GET("get_all_docentes_.php")
    Observable<List<Docentes>> getAllDocentes(@Query("id_posgrado") String id_posgrado);

    @GET("get_posgrado_.php")
    Observable<Posgrados> getPosgrado(@Query("id_posgrado") String id_posgrado);

    @GET("get_some_modulos_.php")
    Observable<List<Modulos>> getSomeModulos(@Query("id_posgrado") String id_posgrado, @Query("semestre") int semestre);

    @GET("get_some_docentes_.php")
    Observable<List<Docentes>> getSomeDocentes(@Query("id_posgrado") String id_posgrado, @Query("semestre") int semestre);

    @GET("get_horario_.php")
    Observable<List<Horarios>> getHorario(@Query("id_posgrado") String id_posgrado, @Query("semestre") int semestre);

    @GET("get_some_calificaciones_.php")
    Observable<List<Calificaciones>> getSomeCalificacion(@Query("id_posgrado") String id_posgrado, @Query("semestre") int semestre, @Query("id_usuario") String id_usuario);
}
