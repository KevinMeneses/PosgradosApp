package co.edu.uniautonoma.posgradosapp.data.datasource.models;

public class Modulos {
    private String id;
    private String id_docente;
    private String nombre;
    private String descripcion;
    private int creditos;
    private String duracion;

    public Modulos(String id, String id_docente, String nombre, String descripcion, int creditos, String duracion) {
        this.id = id;
        this.id_docente = id_docente;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.duracion = duracion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_docente() {
        return id_docente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public String getDuracion() {
        return duracion;
    }
}