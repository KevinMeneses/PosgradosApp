package co.edu.uniautonoma.posgradosapp.domain.usecase

import co.edu.uniautonoma.posgradosapp.domain.entity.Usuario
import co.edu.uniautonoma.posgradosapp.domain.repository.UsuarioRepository
import co.edu.uniautonoma.posgradosapp.domain.util.Result

class InicioSesionUseCaseImpl(private val usuarioRepository: UsuarioRepository): InicioSesionUseCase {

    override suspend fun iniciarSesion(correo: String): Result<Usuario> {
        return usuarioRepository.getUsuario(correo)
    }
}