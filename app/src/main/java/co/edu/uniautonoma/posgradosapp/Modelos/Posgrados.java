package co.edu.uniautonoma.posgradosapp.Modelos;

public class Posgrados {

    private String id;
    private String cod_snies;
    private String nombre;
    private String duracion;
    private int totalcreditos;
    private String descripcion;
    private String valorsemestre;

    public Posgrados(String id, String cod_snies, String nombre, String duracion, int totalcreditos, String descripcion, String valorsemestre)
    {
        this.id = id;
        this.cod_snies = cod_snies;
        this.nombre = nombre;
        this.duracion = duracion;
        this.totalcreditos = totalcreditos;
        this.descripcion = descripcion;
        this.valorsemestre = valorsemestre;
    }

    public Posgrados(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCod_snies() {
        return cod_snies;
    }

    public String getValorsemestre() {
        return valorsemestre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public int getTotalcreditos() {
        return totalcreditos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
