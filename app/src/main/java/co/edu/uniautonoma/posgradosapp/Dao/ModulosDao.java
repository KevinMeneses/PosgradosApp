package co.edu.uniautonoma.posgradosapp.Dao;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Modulos;

public class ModulosDao {

    private static ArrayList<Modulos> modulos;
    private static String url;
    private static String id_posgrado;
    private static int semestre;

    public ArrayList<Modulos> getSomeModulos(String url, String id_posgrado, int semestre){
        ModulosDao.url = url;
        ModulosDao.id_posgrado = id_posgrado;
        ModulosDao.semestre = semestre;
        try {
            new getSomeModulosAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return modulos;
    }

    private static class getSomeModulosAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String,String> httpParams = new HashMap();

            httpParams.put("id_posgrado", id_posgrado);
            httpParams.put("semestre", Integer.toString(semestre));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_some_modulos.php", "GET", httpParams);
            if (jsonObject != null) {
                try {
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray modulosjson = jsonObject.getJSONArray("data");
                        modulos = new ArrayList<>();
                        for (int i = 0; i < modulosjson.length(); i++) {
                            JSONObject modulojson = modulosjson.getJSONObject(i);
                            modulos.add(new Modulos(
                                    modulojson.getString("id"),
                                    modulojson.getString("id_docente"),
                                    modulojson.getString("nombre"),
                                    modulojson.getString("descripcion"),
                                    modulojson.getInt("creditos"),
                                    modulojson.getString("duracion")
                            ));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public ArrayList<Modulos> getAllModulos(String url, String id_posgrado){
        ModulosDao.url = url;
        ModulosDao.id_posgrado = id_posgrado;
        try {
            new getAllModulosAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return modulos;
    }

    private static class getAllModulosAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> httpParams = new HashMap();
            HttpJsonParser httpJsonParser = new HttpJsonParser();

            httpParams.put("id_posgrado",id_posgrado);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_all_modulos.php", "GET", httpParams);
            if(jsonObject!=null) {
                try {
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray modulosjson = jsonObject.getJSONArray("data");
                        modulos = new ArrayList<>();
                        for (int i = 0; i < modulosjson.length(); i++) {
                            JSONObject moduloitem = modulosjson.getJSONObject(i);
                            modulos.add(new Modulos(
                                    moduloitem.getString("id"),
                                    moduloitem.getString("id_docente"),
                                    moduloitem.getString("nombre"),
                                    moduloitem.getString("descripcion"),
                                    moduloitem.getInt("creditos"),
                                    moduloitem.getString("duracion")
                            ));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
