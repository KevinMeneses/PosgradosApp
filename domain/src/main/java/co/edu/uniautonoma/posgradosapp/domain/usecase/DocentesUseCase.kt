package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Docentes
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface DocentesUseCase {

    suspend fun getDocentes(id_posgrado: String): Result<List<Docentes>>

}