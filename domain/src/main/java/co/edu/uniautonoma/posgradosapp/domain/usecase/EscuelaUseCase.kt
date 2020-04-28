package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Escuela
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface EscuelaUseCase {
    suspend fun getEscuela(): Result<Escuela>
    suspend fun getDestinatario(): Result<String>
}