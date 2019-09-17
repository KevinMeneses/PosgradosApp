package co.edu.uniautonoma.posgradosapp.Modelos;

public class Docentes {
    private String id;
    private String nombre;
    private String apellido;
    private String profesion;
    private String descripcion;
    private String imagen;

    public Docentes(String nombre, String apellido, String profesion, String descripcion, String imagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.profesion = profesion;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getProfesion() {
        return profesion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
