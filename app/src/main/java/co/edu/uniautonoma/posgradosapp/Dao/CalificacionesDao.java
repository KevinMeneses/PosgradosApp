package co.edu.uniautonoma.posgradosapp.Dao;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Calificaciones;

public class CalificacionesDao {

    private static String url;
    private static ArrayList<String> id_docentes;
    private static String id_usuario;
    private static String id_docente;
    private static ArrayList<Calificaciones> calificacion;
    private static float calif;

    public ArrayList<Calificaciones> getCalificaciones(String url, ArrayList<String> id_docentes, String id_usuario){
        CalificacionesDao.url = url;
        CalificacionesDao.id_docentes = id_docentes;
        CalificacionesDao.id_usuario = id_usuario;
        try {
            new GetCalificacionesAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return calificacion;
    }

    public void postCalificaciones(float calificacion, String id_docente){
        calif = calificacion;
        CalificacionesDao.id_docente = id_docente;
        try {
            new PostCalificacionAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class GetCalificacionesAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String,String> httpParams = new HashMap();
            calificacion = new ArrayList<>();

            for (int i = 0; i < id_docentes.size(); i++) {
                httpParams.clear();
                httpParams.put("id_docente", id_docentes.get(i));
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        url + "get_calificacion.php", "GET", httpParams);
                httpParams.put("id_usuario", id_usuario);
                JSONObject jsonObject1 = httpJsonParser.makeHttpRequest(
                        url + "get_some_calificacion.php", "GET", httpParams);
                try {
                    int success = jsonObject.getInt("success");
                    int success1 = jsonObject1.getInt("success");
                    if (success == 1 && success1 == 1) {
                        JSONObject calificacionjson = jsonObject1.getJSONObject("data");
                        JSONObject promediojson = jsonObject.getJSONObject("data");
                        calificacion.add(new Calificaciones(id_usuario, calificacionjson.getLong("calificacion"), promediojson.getLong("promedio")));
                    }
                    else{
                        calificacion.add(new Calificaciones(id_usuario, 0, 0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private static class PostCalificacionAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> httpParams = new HashMap();
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            httpParams.put("id_usuario", id_usuario);
            httpParams.put("id_docente", id_docente);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_some_calificacion.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt("success");
                httpParams.put("calificacion", Float.toString(calif));
                if (success == 1) {
                    httpJsonParser.makeHttpRequest(
                            url + "update_calificacion.php", "POST", httpParams);
                }
                else{
                    httpJsonParser.makeHttpRequest(
                            url + "add_calificacion.php", "POST", httpParams);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
