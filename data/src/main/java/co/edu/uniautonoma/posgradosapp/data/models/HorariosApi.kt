package co.edu.uniautonoma.posgradosapp.data.models

data class HorariosApi(
        val dia: String,
        val hora_inicio: String,
        val hora_fin: String,
        val sede: String,
        val salon: String) {
    var id = 0
}