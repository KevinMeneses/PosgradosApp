package co.edu.uniautonoma.posgradosapp.data.datasource.models;

public class Escuela {

    private Double coordenada1;
    private Double coordenada2;
    private String correo;
    private String descripcion;
    private String direccion;
    private String director;

    public Escuela(String director, String descripcion, String correo, String direccion, Double coordenada1, Double coordenada2) {
        this.director = director;
        this.descripcion = descripcion;
        this.correo = correo;
        this.direccion = direccion;
        this.coordenada1 = coordenada1;
        this.coordenada2 = coordenada2;
    }

    public String getDirector() {
        return director;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public Double getCoordenada1() {
        return coordenada1;
    }

    public Double getCoordenada2() {
        return coordenada2;
    }
}
