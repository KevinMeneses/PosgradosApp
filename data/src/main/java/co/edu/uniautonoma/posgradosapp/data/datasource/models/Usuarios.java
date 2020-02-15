package co.edu.uniautonoma.posgradosapp.data.datasource.models;

public class Usuarios {

    private String codigo;
    private String nombre;
    private String apellido;
    private String id_posgrado;
    private int semestre;

    public Usuarios(String codigo, String nombre, String apellido, String correo, String id_posgrado, int semestre) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_posgrado = id_posgrado;
        this.semestre = semestre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getId_posgrado() {
        return id_posgrado;
    }

    public int getSemestre() {
        return semestre;
    }
}
