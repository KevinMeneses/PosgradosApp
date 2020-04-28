package co.edu.uniautonoma.posgradosapp.domain.entity

data class Informacion (
        val posgrados: Posgrados,
        val modulos: List<Modulos>,
        val horario: List<Horarios>,
        val docente: List<Docentes>,
        val calificaciones: List<Calificaciones>
)