package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.domain.repository.EscuelaRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result

class EscuelaUseCaseImpl (private val escuelaRepository: EscuelaRepository): EscuelaUseCase {

    override suspend fun getEscuela(): Result<Escuela> {
        return escuelaRepository.getEscuela()
    }

    override suspend fun getDestinatario(): Result<String> {
        return escuelaRepository.getDestinatario()
    }
}