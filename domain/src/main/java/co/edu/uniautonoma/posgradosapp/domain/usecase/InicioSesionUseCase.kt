package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Usuario
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface InicioSesionUseCase {

    suspend fun iniciarSesion(correo: String): Result<Usuario>
}