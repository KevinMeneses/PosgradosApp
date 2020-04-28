package co.edu.uniautonoma.posgradosapp.domain.repository

import co.edu.uniautonoma.posgradosapp.domain.entity.Modulos
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface ModulosRepository {
    suspend fun getModulos(id_posgrado: String): Result<List<Modulos>>
}