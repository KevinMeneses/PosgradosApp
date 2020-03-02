package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.domain.repository.ModulosRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result

class ModulosUseCaseImpl(private val modulosRepository: ModulosRepository): ModulosUseCase {

    override suspend fun getModulos(id_posgrado: String): Result<List<Modulos>> {
        return modulosRepository.getModulos(id_posgrado)
    }
}

