package co.edu.uniautonoma.posgradosapp.domain.repository

import co.edu.uniautonoma.posgradosapp.domain.entity.Usuario
import co.edu.uniautonoma.posgradosapp.domain.util.Result

interface UsuarioRepository {

    suspend fun getUsuario(correo: String): Result<Usuario>
}