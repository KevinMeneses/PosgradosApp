package co.edu.uniautonoma.posgradosapp.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.edu.uniautonoma.posgradosapp.Modelos.Calificaciones;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.Modelos.Horarios;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.Modelos.Posgrados;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PrincipalViewModel extends AndroidViewModel {

    public class Informacion{

        private Posgrados posgrados;
        private List<Modulos> modulos;
        private List<Horarios> horario;
        private List<Docentes> docente;
        private List<Calificaciones> calificaciones;

        private Informacion(Posgrados posgrados, List<Modulos> modulos, List<Horarios> horario, List<Docentes> docente, List<Calificaciones> calificaciones) {
            this.posgrados = posgrados;
            this.modulos = modulos;
            this.horario = horario;
            this.docente = docente;
            this.calificaciones = calificaciones;
        }

        public Posgrados getPosgrados() {
            return posgrados;
        }

        public List<Modulos> getModulos() {
            return modulos;
        }

        public List<Horarios> getHorario() {
            return horario;
        }

        public List<Docentes> getDocente() {
            return docente;
        }

        public List<Calificaciones> getCalificaciones() {
            return calificaciones;
        }
    }

    private MutableLiveData<Informacion> informacion;
    private MutableLiveData<Boolean> estado = new MutableLiveData<>();

    private Posgrados posgrado;
    private List<Calificaciones> calificaciones;
    private List<Modulos> modulos;
    private List<Horarios> horarios;
    private List<Docentes> docentes;

    private Boolean termino = false;

    private String id_usuario;
    private String id_posgrado;
    private int semestre;

    private PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);

    public PrincipalViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Informacion> getInformacion(){
        return informacion;
    }

    public LiveData<Boolean> getEstado(){
        return estado;
    }

    public void EnviarPeticion(String id_posgrado, int semestre, String id_usuario){

        this.id_usuario = id_usuario;
        this.id_posgrado = id_posgrado;
        this.semestre = semestre;

        estado.setValue(true);
        Empezar();
        if(termino){
        EnviarDatos();}
    }

    private void Empezar(){
        ObtenerPosgrado();
    }

    private void ObtenerPosgrado() {
        peticionesApi.getPosgrado(id_posgrado)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Posgrados>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Posgrados posgrados) {
                        posgrado = posgrados;
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                        ObtenerModulos();
                    }
                });
    }

    private void ObtenerModulos() {
        peticionesApi.getSomeModulos(id_posgrado,semestre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Modulos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onNext(List<Modulos> response) {
                        modulos = response;
                    }
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onComplete() {
                        ObtenerDocentes();}
                });
    }

    private void ObtenerDocentes() {
        peticionesApi.getSomeDocentes(id_posgrado,semestre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Docentes>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(List<Docentes> response) {
                        docentes = response;
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() {
                        ObtenerHorario();}
                });
    }

    private void ObtenerHorario() {
        peticionesApi.getHorario(id_posgrado, semestre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Horarios>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(List<Horarios> response) {
                        horarios = response;
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() {
                        ObtenerCalificaciones(); }
                });
    }

    private void ObtenerCalificaciones() {
        peticionesApi.getSomeCalificacion(id_posgrado,semestre,id_usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Calificaciones>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(List<Calificaciones> response) {
                        calificaciones = response;
                    }
                    @Override
                    public void onError(Throwable e) {
                        estado.setValue(false);
                        Log.d("errTAG", e.getMessage());
                    }
                    @Override
                    public void onComplete() { termino = true;}
                });
    }

    private void EnviarDatos(){
        if(posgrado!=null && modulos!=null && horarios!=null && docentes!=null && calificaciones!=null) {
            Informacion info = new Informacion(posgrado, modulos, horarios, docentes, calificaciones);
            informacion = new MutableLiveData<>();
            informacion.setValue(info);
            estado.setValue(false);
        }
    }

}
