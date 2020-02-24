package co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit

import co.edu.uniautonoma.posgradosapp.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface PeticionesApi {
    @GET("get_escuela_.php")
    suspend fun getEscuela(@Query("id") id: Int): Response<EscuelaApi?>

    @GET("get_usuario_.php")
    suspend fun getUsuario(@Query("correo") correo: String?): Response<UsuarioApi?>

    @GET("get_all_posgrados_.php")
    suspend fun getAllPosgrados(): Response<List<PosgradosApi>?>

    @GET("get_all_modulos_.php")
    suspend fun getAllModulos(@Query("id_posgrado") id_posgrado: String?): Response<List<ModulosApi>?>

    @GET("get_all_docentes_.php")
    suspend fun getAllDocentes(@Query("id_posgrado") id_posgrado: String?): Response<List<DocentesApi>?>

    @GET("get_posgrado_.php")
    suspend fun getPosgrado(@Query("id_posgrado") id_posgrado: String?): Response<PosgradosApi?>

    @GET("get_some_modulos_.php")
    suspend fun getSomeModulos(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int): Response<List<ModulosApi>?>

    @GET("get_some_docentes_.php")
    suspend fun getSomeDocentes(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int): Response<List<DocentesApi>?>

    @GET("get_horario_.php")
    suspend fun getHorario(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int): Response<List<HorariosApi>?>

    @GET("get_some_calificaciones_.php")
    suspend fun getSomeCalificaciones(@Query("id_posgrado") id_posgrado: String?, @Query("semestre") semestre: Int, @Query("id_usuario") id_usuario: String?): Response<List<CalificacionesApi>?>

    @GET("get_some_calificacion_.php")
    suspend fun getSomeCalificacion(@Query("id_usuario") id_usuario: String?, @Query("id_docente") id_docente: String?): Response<CalificacionesApi?>

    @FormUrlEncoded
    @POST("update_calificacion.php")
    suspend fun updateCalificacion(@Field("calificacion") calificacion: Float, @Field("id_usuario") id_usuario: String?, @Field("id_docente") id_docente: String?): Response<CalificacionesApi?>

    @FormUrlEncoded
    @POST("add_calificacion.php")
    suspend fun addCalificacion(@Field("calificacion") calificacion: Float, @Field("id_usuario") id_usuario: String?, @Field("id_docente") id_docente: String?): Response<CalificacionesApi?>
}