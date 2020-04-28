package co.edu.uniautonoma.posgradosapp.data.repository

import co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit.PeticionesApi
import co.edu.uniautonoma.posgradosapp.data.mapper.EntityMapper
import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.domain.repository.DocentesRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception

class DocentesRepositoryImpl(private val retrofit: PeticionesApi): DocentesRepository {
    override suspend fun getDocentes(id_posgrado: String): Result<List<Docentes>> {
        try {
            val result = retrofit.getAllDocentes(id_posgrado)
            return if (result.isSuccessful){
                val body = EntityMapper.docentesApiListToDocentes(result.body())
                if(body != null){
                    Result.Success(body)
                }
                else{
                    Result.Error(IOException(result.errorBody().toString()))
                }
            }
            else{
                Result.Error(IOException(result.code().toString()))
            }
        }
        catch (e:Exception){
            return Result.Error(IOException("No hay respuesta del servidor"))
        }

    }
}