package co.edu.uniautonoma.posgradosapp.domain.repository

import co.edu.uniautonoma.posgradosapp.domain.entity.Informacion
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface PrincipalRepository {
    suspend fun getInformacion(id_posgrado: String, semestre: Int, id_usuario: String): Result<Informacion>
}