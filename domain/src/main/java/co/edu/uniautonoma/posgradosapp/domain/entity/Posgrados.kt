package co.edu.uniautonoma.posgradosapp.domain.entity

data class Posgrados(
        var id: String = "",
        val cod_snies: String = "",
        var nombre: String = "",
        val duracion: String = "",
        val totalcreditos: Int = 0,
        var descripcion: String = "",
        val valorsemestre: String = ""
)