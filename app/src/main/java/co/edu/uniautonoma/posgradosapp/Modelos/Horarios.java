package co.edu.uniautonoma.posgradosapp.Modelos;

public class Horarios {
    private int id;
    private String dia;
    private String hora_inicio;
    private String hora_fin;
    private String sede;
    private String salon;

    public Horarios(String dia, String hora_inicio, String hora_fin, String sede, String salon) {
        this.dia = dia;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.sede = sede;
        this.salon = salon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSede() {
        return sede;
    }

    public String getSalon() {
        return salon;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public String getDia() {
        return dia;
    }
}
