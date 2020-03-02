package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Informacion
import co.edu.uniautonoma.posgradosapp.domain.repository.PrincipalRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result

class PrincipalUseCaseImpl (private val principalRepository: PrincipalRepository): PrincipalUseCase{

    override suspend fun getInformacion(id_posgrado: String, semestre: Int, id_usuario: String): Result<Informacion> {
        return principalRepository.getInformacion(id_posgrado, semestre, id_usuario)
    }
}

