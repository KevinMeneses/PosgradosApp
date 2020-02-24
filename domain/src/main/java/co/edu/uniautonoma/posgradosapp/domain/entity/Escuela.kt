package co.edu.uniautonoma.posgradosapp.domain.entity

data class Escuela(
    val director: String,
    val descripcion: String,
    val correo: String,
    val direccion: String,
    val coordenada1: Double,
    val coordenada2: Double
)