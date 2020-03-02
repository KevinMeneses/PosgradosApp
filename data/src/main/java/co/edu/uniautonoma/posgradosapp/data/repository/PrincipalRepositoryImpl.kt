package co.edu.uniautonoma.posgradosapp.data.repository

import co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit.PeticionesApi
import co.edu.uniautonoma.posgradosapp.data.mapper.EntityMapper
import co.edu.uniautonoma.posgradosapp.domain.entity.Informacion
import co.edu.uniautonoma.posgradosapp.domain.repository.PrincipalRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result
import java.io.IOException
import java.lang.Exception

class PrincipalRepositoryImpl (private val retrofit: PeticionesApi): PrincipalRepository{

    override suspend fun getInformacion(id_posgrado: String, semestre: Int, id_usuario: String)
            :Result<Informacion>{
        try {
            val posgrado = retrofit.getPosgrado(id_posgrado)
            val modulos = retrofit.getSomeModulos(id_posgrado, semestre)
            val docentes = retrofit.getSomeDocentes(id_posgrado, semestre)
            val horarios = retrofit.getHorario(id_posgrado, semestre)
            val calificaciones = retrofit.getSomeCalificaciones(id_posgrado, semestre, id_usuario)

            return if(posgrado.isSuccessful && modulos.isSuccessful
                    && docentes.isSuccessful && horarios.isSuccessful
                    && calificaciones.isSuccessful){

                val posgradobody = EntityMapper.posgradosApiToPosgrados(posgrado.body())
                val modulosbody = EntityMapper.modulosApiListToModulos(modulos.body())
                val docentesbody = EntityMapper.docentesApiListToDocentes(docentes.body())
                val horariosbody = EntityMapper.horariosApiListToHorarios(horarios.body())
                val calificacionesbody = EntityMapper.calificacionesApiListToCalificaciones(calificaciones.body())
                if (posgradobody != null
                        && modulosbody != null
                        && docentesbody != null
                        && horariosbody != null
                        && calificacionesbody != null){
                    Result.Success(Informacion(
                            posgrados = posgradobody,
                            modulos = modulosbody,
                            horario = horariosbody,
                            docente = docentesbody,
                            calificaciones = calificacionesbody
                    ))
                }
                else{
                    Result.Error(IOException("Data is null"))
                }
            }
            else{
                Result.Error(IOException("Something went wrong with the server"))
            }
        }
        catch (e:Exception){
            return Result.Error(IOException("No hay respuesta del servidor"))
        }


    }
}
