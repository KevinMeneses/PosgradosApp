package co.edu.uniautonoma.posgradosapp.domain.repository

import co.edu.uniautonoma.posgradosapp.domain.entity.Posgrados
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface PosgradosRepository {
    suspend fun getPosgrados(): Result<List<Posgrados>>
}