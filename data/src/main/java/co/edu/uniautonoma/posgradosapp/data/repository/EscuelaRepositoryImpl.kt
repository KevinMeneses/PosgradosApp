package co.edu.uniautonoma.posgradosapp.data.repository

import co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit.PeticionesApi
import co.edu.uniautonoma.posgradosapp.data.mapper.EntityMapper
import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.domain.repository.EscuelaRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import java.io.IOException
import java.lang.Exception

class EscuelaRepositoryImpl(private val retrofit: PeticionesApi): EscuelaRepository {

    override suspend fun getEscuela(): Result<Escuela> {
        try {
            val response = retrofit.getEscuela(1)
            return if(response.isSuccessful){
                val body = EntityMapper.escuelaApiToEscuela(response.body())
                if(body!=null)
                    Result.Success(body)
                else
                    Result.Error(IOException(response.errorBody().toString()))
            }
            else{
                Result.Error(IOException(response.code().toString()))
            }
        }
        catch (e: Exception){
            return Result.Error(IOException("No hay respuesta del servidor"))
        }

    }

    override suspend fun getDestinatario(): Result<String> {
        try {
            val response = retrofit.getEscuela(1)
            return if(response.isSuccessful){
                val body = response.body()?.correo
                if(body!=null)
                    Result.Success(body)
                else
                    Result.Error(IOException(response.errorBody().toString()))
            }
            else{
                Result.Error(IOException(response.code().toString()))
            }
        }
        catch (e: Exception){
            return Result.Error(IOException("No hay respuesta del servidor"))
        }
    }
}