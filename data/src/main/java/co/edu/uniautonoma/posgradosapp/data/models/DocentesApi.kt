package co.edu.uniautonoma.posgradosapp.data.models

data class DocentesApi(
        var nombre: String,
        val apellido: String,
        val profesion: String,
        var descripcion: String,
        val imagen: String) {
    var id: String? = null
}