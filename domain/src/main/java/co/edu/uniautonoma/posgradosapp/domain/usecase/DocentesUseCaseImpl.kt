package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.domain.repository.DocentesRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result

class DocentesUseCaseImpl(private val docentesRepository: DocentesRepository): DocentesUseCase {
    override suspend fun getDocentes(id_posgrado: String): Result<List<Docentes>> {
        return docentesRepository.getDocentes(id_posgrado)
    }
}