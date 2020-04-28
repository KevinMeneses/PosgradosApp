package co.edu.uniautonoma.posgradosapp.domain.repository

import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface DocentesRepository {
    suspend fun getDocentes(id_posgrado: String): Result<List<Docentes>>
}