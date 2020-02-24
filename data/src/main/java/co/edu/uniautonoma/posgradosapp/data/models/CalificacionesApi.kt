package co.edu.uniautonoma.posgradosapp.data.models

import com.google.gson.annotations.SerializedName

data class CalificacionesApi(
        val id_usuario: String,
        @SerializedName("calificacion")
        val calificacion: Float,
        @SerializedName("promedio")
        val promedio: Float
)