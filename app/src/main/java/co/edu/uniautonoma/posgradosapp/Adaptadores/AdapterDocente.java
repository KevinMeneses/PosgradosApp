package co.edu.uniautonoma.posgradosapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;
import co.edu.uniautonoma.posgradosapp.R;

public class AdapterDocente extends BaseAdapter {

    private Context context;
    private List<Docentes> docente;
    private LayoutInflater inflater;

    public AdapterDocente(Context context, List<Docentes> docente) {
        this.context = context;
        this.docente = docente;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return docente.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.lista_docentes, null);
        TextView tvNdocente = (TextView) view.findViewById(R.id.tvNdocente);
        TextView tvPdocente = (TextView) view.findViewById(R.id.tvPdocente);
        ImageView ivDocente = (ImageView) view.findViewById(R.id.ivDocente);

        tvNdocente.setText(docente.get(i).getNombre() + " " + docente.get(i).getApellido());
        tvPdocente.setText(docente.get(i).getProfesion());
        Glide.with(context).load(docente.get(i).getImagen()).into(ivDocente);
        return view;
    }
}
