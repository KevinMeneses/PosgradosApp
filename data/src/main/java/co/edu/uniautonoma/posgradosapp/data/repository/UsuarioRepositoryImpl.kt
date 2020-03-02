package co.edu.uniautonoma.posgradosapp.data.repository

import co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit.PeticionesApi
import co.edu.uniautonoma.posgradosapp.data.mapper.EntityMapper
import co.edu.uniautonoma.posgradosapp.domain.entity.Usuario
import co.edu.uniautonoma.posgradosapp.domain.repository.UsuarioRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception

class UsuarioRepositoryImpl(private val retrofit: PeticionesApi): UsuarioRepository{

    override suspend fun getUsuario(correo: String): Result<Usuario> {
        try {
            val response = retrofit.getUsuario(correo)
            if (response.isSuccessful){
                val body = EntityMapper.usuarioApiToUsuario(response.body())
                if (body!=null){
                    return Result.Success(body)
                }
                else{
                    return Result.Error(IOException(response.errorBody().toString()))
                }
            }
            else{
                return Result.Error(IOException(response.code().toString()))
            }
        }
        catch (e:Exception){
            return Result.Error(IOException("No hay respuesta del servidor"))
        }

    }
}