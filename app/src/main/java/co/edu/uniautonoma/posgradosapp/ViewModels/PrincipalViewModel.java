package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import co.edu.uniautonoma.posgradosapp.Dao.CalificacionesDao;
import co.edu.uniautonoma.posgradosapp.Dao.DocentesDao;
import co.edu.uniautonoma.posgradosapp.Dao.HorariosDao;
import co.edu.uniautonoma.posgradosapp.Dao.ModulosDao;
import co.edu.uniautonoma.posgradosapp.Dao.PosgradosDao;
import co.edu.uniautonoma.posgradosapp.Modelos.Calificaciones;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.Modelos.Horarios;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.Modelos.Posgrados;
import co.edu.uniautonoma.posgradosapp.R;

public class PrincipalViewModel extends AndroidViewModel {

    public class Informacion{

        private Posgrados posgrados;
        private ArrayList<Modulos> modulos;
        private ArrayList<Horarios> horario;
        private ArrayList<Docentes> docente;
        private ArrayList<Calificaciones> calificaciones;

        private Informacion(Posgrados posgrados, ArrayList<Modulos> modulos, ArrayList<Horarios> horario, ArrayList<Docentes> docente, ArrayList<Calificaciones> calificaciones) {
            this.posgrados = posgrados;
            this.modulos = modulos;
            this.horario = horario;
            this.docente = docente;
            this.calificaciones = calificaciones;
        }

        public Posgrados getPosgrados() {
            return posgrados;
        }

        public ArrayList<Modulos> getModulos() {
            return modulos;
        }

        public ArrayList<Horarios> getHorario() {
            return horario;
        }

        public ArrayList<Docentes> getDocente() {
            return docente;
        }

        public ArrayList<Calificaciones> getCalificaciones() {
            return calificaciones;
        }
    }

    private PosgradosDao posgradosDao = new PosgradosDao();
    private ModulosDao modulosDao = new ModulosDao();
    private HorariosDao horariosDao = new HorariosDao();
    private DocentesDao docentesDao = new DocentesDao();
    private CalificacionesDao calificacionesDao = new CalificacionesDao();

    private MutableLiveData<Informacion> informacion;

    private String url = getApplication().getResources().getString(R.string.BASE_URL);

    private Posgrados posgrado;
    private ArrayList<String> id_docentes = new ArrayList<>();
    private ArrayList<Calificaciones> calificaciones;
    private ArrayList<Modulos> modulos;
    private ArrayList<Horarios> horarios;
    private ArrayList<Docentes> docentes;

    public PrincipalViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Informacion> getInformacion(){
        return informacion;
    }

    public void EnviarPeticion(String id_posgrado, int semestre, String id_usuario){

        posgrado = posgradosDao.getPosgrado(url,id_posgrado);
        modulos = modulosDao.getSomeModulos(url,id_posgrado,semestre);
        horarios = horariosDao.getHorario(url,id_posgrado,semestre);

        id_docentes.clear();
        for (int i = 0; i < modulos.size(); i++) {id_docentes.add(modulos.get(i).getId_docente());}

        docentes = docentesDao.getDocentes(url, id_docentes);
        calificaciones = calificacionesDao.getCalificaciones(url, id_docentes, id_usuario);
        ObtenerDatos();
    }

    private void ObtenerDatos(){
        if(posgrado!=null && modulos!=null && horarios!=null && docentes!=null && calificaciones!=null) {
            Informacion info = new Informacion(posgrado, modulos, horarios, docentes, calificaciones);
            informacion = new MutableLiveData<>();
            informacion.setValue(info);
        }
    }
}
