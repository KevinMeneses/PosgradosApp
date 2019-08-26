package co.edu.uniautonoma.posgradosapp.Modelos;

public class Usuarios {

    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private String id_posgrado;
    private int semestre;
    private String contrasena;

    public Usuarios(String codigo, String nombre, String apellido, String correo, String id_posgrado, int semestre) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
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

    public String getCorreo() {
        return correo;
    }

    public String getId_posgrado() {
        return id_posgrado;
    }

    public int getSemestre() {
        return semestre;
    }
}
