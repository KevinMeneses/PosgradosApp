package co.edu.uniautonoma.posgradosapp.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.edu.uniautonoma.posgradosapp.Modelos.Calificaciones;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.Retrofit.PeticionesApi;
import co.edu.uniautonoma.posgradosapp.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdapterPrincipal extends RecyclerView.Adapter<AdapterPrincipal.ModulosViewHolder>{

    private List<Modulos> modulos;
    private List<Docentes> docentes;
    private List<Calificaciones> calificaciones;
    private String id_usuario;
    private ClickListener clickListener;

    public void setInfo(List<Modulos> modulos, List<Docentes> docentes, List<Calificaciones> calificaciones, String id_usuario){
        this.modulos = modulos;
        this.docentes = docentes;
        this.calificaciones = calificaciones;
        this.id_usuario = id_usuario;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClicked(int position, String tag);
    }

    public class ModulosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNombreModulo;
        private TextView tvDuracionModulo;
        private TextView tvNombreDocente;
        private ImageView ivFotoDocente;
        private RelativeLayout rlFotoDocente;

        private ImageView ivDetalleDocente;
        private ImageView ivContenido;
        private ImageView ivHorario;
        private ImageView ivAlerta;
        private RatingBar rbCalificacion;
        private TextView tvCalificacion;

        private ModulosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreModulo = (TextView) itemView.findViewById(R.id.tvNombreModulo);
            tvDuracionModulo = (TextView) itemView.findViewById(R.id.tvDuracionModulo);
            tvNombreDocente = (TextView) itemView.findViewById(R.id.tvNombreDocente);
            ivFotoDocente = (ImageView) itemView.findViewById(R.id.ivFotoDocente);
            rlFotoDocente = (RelativeLayout) itemView.findViewById(R.id.rlFotoDocente);

            ivDetalleDocente = (ImageView) itemView.findViewById(R.id.ivDetalleDocente);
            ivContenido = (ImageView) itemView.findViewById(R.id.ivContenido);
            ivHorario = (ImageView) itemView.findViewById(R.id.ivHorario);
            ivAlerta = (ImageView) itemView.findViewById(R.id.ivAlerta);
            rbCalificacion = (RatingBar) itemView.findViewById(R.id.rbCalificacion);
            tvCalificacion = (TextView) itemView.findViewById(R.id.tvCalificacion);

            tvNombreDocente.setTag("Docentes");
            rlFotoDocente.setTag("Docentes");
            ivDetalleDocente.setTag("Docentes");

            tvNombreDocente.setOnClickListener(this);
            rlFotoDocente.setOnClickListener(this);
            ivDetalleDocente.setOnClickListener(this);

            tvNombreModulo.setTag("Modulos");
            ivContenido.setTag("Modulos");

            tvNombreModulo.setOnClickListener(this);
            ivContenido.setOnClickListener(this);

            ivHorario.setTag("Horarios");
            ivAlerta.setTag("Alerta");

            ivHorario.setOnClickListener(this);
            ivAlerta.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(getPosition(), v.getTag().toString());
            }
        }
    }

    @NonNull
    @Override
    public ModulosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_principal, viewGroup, false);
        ModulosViewHolder holder = new ModulosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ModulosViewHolder modulosViewHolder, final int i){

        Modulos modulo = modulos.get(i);
        Docentes docente = docentes.get(i);
        Calificaciones calificacion = calificaciones.get(i);

        String nombredocente = docente.getNombre() + " " + docente.getApellido();
        String promedio = Float.toString(calificacion.getPromedio());

        modulosViewHolder.tvNombreModulo.setText(modulo.getNombre());
        modulosViewHolder.tvDuracionModulo.setText(modulo.getDuracion());
        modulosViewHolder.tvNombreDocente.setText(nombredocente);
        modulosViewHolder.tvCalificacion.setText(promedio);
        modulosViewHolder.rbCalificacion.setRating(calificacion.getCalificacion());
        modulosViewHolder.rbCalificacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                SetCalificacion(i, rating);
            }
        });
        Glide.with(modulosViewHolder.ivFotoDocente.getContext()).load(docente.getImagen()).into(modulosViewHolder.ivFotoDocente);
    }

    @Override
    public int getItemCount() {
        return modulos.size();
    }

    public void clear() {

        final int size = modulos.size();

        if (size > 0) {
            modulos.clear();
            docentes.clear();
            calificaciones.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    private void SetCalificacion(int position, float calificacion) {

        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);

        peticionesApi.getSomeCalificacion(id_usuario,modulos.get(position).getId_docente())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Calificaciones>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Calificaciones calificaciones) {
                    }
                    @Override
                    public void onError(Throwable e) {
                        agregarCalificacion(calificacion,modulos.get(position).getId_docente());
                    }
                    @Override
                    public void onComplete() {
                        modificarCalificacion(calificacion,modulos.get(position).getId_docente());
                    }
                });
    }

    private void modificarCalificacion(float calificacion, String id_docente){
        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);
        peticionesApi.updateCalificacion(calificacion,id_usuario,id_docente)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Calificaciones>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Calificaciones calificaciones) {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void agregarCalificacion(float calificacion, String id_docente){
        PeticionesApi peticionesApi = RetrofitClient.getRetrofitInstance().create(PeticionesApi.class);
        peticionesApi.addCalificacion(calificacion,id_usuario,id_docente)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Calificaciones>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Calificaciones calificaciones) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
