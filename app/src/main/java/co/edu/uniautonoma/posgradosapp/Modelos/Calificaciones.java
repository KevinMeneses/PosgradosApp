package co.edu.uniautonoma.posgradosapp.Modelos;

import com.google.gson.annotations.SerializedName;

public class Calificaciones {
    private String id_usuario;
    @SerializedName("calificacion")
    private float calificacion;
    @SerializedName("promedio")
    private float promedio;

    public Calificaciones(String id_usuario, float calificacion, float promedio) {
        this.id_usuario = id_usuario;
        this.calificacion = calificacion;
        this.promedio = promedio;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public float getPromedio() {
        return promedio;
    }
}
