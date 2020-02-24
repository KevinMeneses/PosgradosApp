package co.edu.uniautonoma.posgradosapp.domain.entity

data class Modulos(
        var id: String,
        val id_docente: String,
        var nombre: String,
        var descripcion: String,
        val creditos: Int,
        val duracion: String)