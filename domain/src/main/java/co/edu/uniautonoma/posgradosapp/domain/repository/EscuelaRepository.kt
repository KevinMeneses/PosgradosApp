package co.edu.uniautonoma.posgradosapp.domain.repository

import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface EscuelaRepository {

    suspend fun getEscuela(): Result<Escuela>
    suspend fun getDestinatario(): Result<String>
}