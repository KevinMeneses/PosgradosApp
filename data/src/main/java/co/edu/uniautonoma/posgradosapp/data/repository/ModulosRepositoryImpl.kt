package co.edu.uniautonoma.posgradosapp.data.repository

import co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit.PeticionesApi
import co.edu.uniautonoma.posgradosapp.data.mapper.EntityMapper
import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.domain.repository.ModulosRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import java.io.IOException
import java.lang.Exception

class ModulosRepositoryImpl (private val retrofit: PeticionesApi): ModulosRepository {
    override suspend fun getModulos(id_posgrado: String): Result<List<Modulos>> {
        try {
            val result = retrofit.getAllModulos(id_posgrado)
            return if (result.isSuccessful){
                val body = EntityMapper.modulosApiListToModulos(result.body())
                if (body != null){
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
        catch (e: Exception){
            return Result.Error(IOException("No hay respuesta del servidor"))
        }
    }
}

