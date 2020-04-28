package co.edu.uniautonoma.posgradosapp.domain.entity

data class Usuario(
        val codigo: String,
        val nombre: String,
        val apellido: String,
        val correo: String?,
        val id_posgrado: String,
        val semestre: Int
)