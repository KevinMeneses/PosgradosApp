package co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit

import co.edu.uniautonoma.posgradosapp.data.datasource.models.*
import io.reactivex.Flowable
import retrofit2.http.*

interface PeticionesApi {
    @GET("get_escuela_.php")
    fun getEscuela(@Query("id") id: Int): Flowable<Escuela?>?

    @GET("get_usuario_.php")
    fun getUsuario(@Query("correo") correo: String?): Flowable<Usuarios?>?

    @get:GET("get_all_posgrados_.php")
    val allPosgrados: Flowable<List<Posgrados?>?>?

    @GET("get_all_modulos_.php")
    fun getAllModulos(@Query("id_posgrado") id_posgrado: String?): Flowable<List<Modulos?>?>?

    @GET("get_all_docentes_.php")
    fun getAllDocentes(@Query("id_posgrado") id_posgrado: String?): Flowable<List<Docentes?>?>?

    @GET("get_posgrado_.php")
    fun getPosgrado(@Query("id_posgrado") id_posgrado: String?): Flowable<Posgrados?>?

    @GET("get_some_modulos_.php")
    fun getSomeModulos(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int): Flowable<List<Modulos?>?>?

    @GET("get_some_docentes_.php")
    fun getSomeDocentes(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int): Flowable<List<Docentes?>?>?

    @GET("get_horario_.php")
    fun getHorario(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int): Flowable<List<Horarios?>?>?

    @GET("get_some_calificaciones_.php")
    fun getSomeCalificaciones(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int, @Query("id_usuario") id_usuario: String?): Flowable<List<Calificaciones?>?>?

    @GET("get_some_calificacion_.php")
    fun getSomeCalificacion(@Query("id_usuario") id_usuario: String?, @Query("id_docente") id_docente: String?): Flowable<Calificaciones?>?

    @FormUrlEncoded
    @POST("update_calificacion.php")
    fun updateCalificacion(@Field("calificacion") calificacion: Float, @Field("id_usuario") id_usuario: String?, @Field("id_docente") id_docente: String?): Flowable<Calificaciones?>?

    @FormUrlEncoded
    @POST("add_calificacion.php")
    fun addCalificacion(@Field("calificacion") calificacion: Float, @Field("id_usuario") id_usuario: String?, @Field("id_docente") id_docente: String?): Flowable<Calificaciones?>?
}