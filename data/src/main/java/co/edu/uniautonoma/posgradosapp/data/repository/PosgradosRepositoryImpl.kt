package co.edu.uniautonoma.posgradosapp.data.repository

import co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit.PeticionesApi
import co.edu.uniautonoma.posgradosapp.data.mapper.EntityMapper
import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados
import co.edu.uniautonoma.posgradosapp.domain.repository.PosgradosRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import java.io.IOException
import java.lang.Exception

class PosgradosRepositoryImpl(private val retrofit: PeticionesApi): PosgradosRepository{

    override suspend fun getPosgrados(): Result<List<Posgrados>> {
        try {
            val response = retrofit.getAllPosgrados()
            return if (response.isSuccessful){
                val body = EntityMapper.posgradosApiListToPosgrados(response.body())
                if(body != null){
                    Result.Success(body)
                }
                else{
                    Result.Error(IOException(response.errorBody().toString()))
                }
            }
            else{
                Result.Error(IOException(response.code().toString()))
            }
        }
        catch (e:Exception){
            return Result.Error(IOException("No hay respuesta del servidor"))
        }
    }
}

