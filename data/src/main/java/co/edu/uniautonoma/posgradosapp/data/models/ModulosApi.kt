package co.edu.uniautonoma.posgradosapp.data.models

data class ModulosApi(
        var id: String,
        val id_docente: String,
        var nombre: String,
        var descripcion: String,
        val creditos: Int,
        val duracion: String
)