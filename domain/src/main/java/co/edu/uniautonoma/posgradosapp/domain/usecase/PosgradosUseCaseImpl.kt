package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados
import co.edu.uniautonoma.posgradosapp.domain.repository.PosgradosRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result

class PosgradosUseCaseImpl (private val posgradosRepository: PosgradosRepository): PosgradosUseCase{

    override suspend fun getPosgrados(): Result<List<Posgrados>> {
        return posgradosRepository.getPosgrados()
    }
}

