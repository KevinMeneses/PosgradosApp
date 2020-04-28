package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface PosgradosUseCase {

    suspend fun getPosgrados(): Result<List<Posgrados>>
}