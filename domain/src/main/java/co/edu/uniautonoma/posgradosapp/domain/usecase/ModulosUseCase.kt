package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface ModulosUseCase {

    suspend fun getModulos(id_posgrado: String): Result<List<Modulos>>
}