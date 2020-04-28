package co.edu.uniautonoma.posgradosapp.data.models

data class UsuarioApi(
        val codigo: String,
        val nombre: String,
        val apellido: String,
        val correo: String?,
        val id_posgrado: String,
        val semestre: Int
)