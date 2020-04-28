package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Informacion
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface PrincipalUseCase {

    suspend fun getInformacion(id_posgrado: String, semestre: Int, id_usuario: String): Result<Informacion>
}