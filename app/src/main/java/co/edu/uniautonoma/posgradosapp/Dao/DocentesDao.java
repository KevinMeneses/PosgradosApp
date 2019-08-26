package co.edu.uniautonoma.posgradosapp.Dao;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Docentes;

public class DocentesDao {

    private static ArrayList<Docentes> docentes;
    private static ArrayList<String> id_docentes;
    private static String id_posgrado;
    private static String url;

    public ArrayList<Docentes> getDocentes(String url, ArrayList<String> id_docentes){
        DocentesDao.url = url;
        DocentesDao.id_docentes = id_docentes;
        try {
            new getDocentesAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    private static class getDocentesAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String,String> httpParams = new HashMap();
            docentes = new ArrayList<>();

            for (int i = 0; i < id_docentes.size(); i++) {
                httpParams.clear();
                httpParams.put("id", id_docentes.get(i));
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        url + "get_docente.php", "GET", httpParams);
                try {
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONObject docentejson = jsonObject.getJSONObject("data");
                        docentes.add(new Docentes(
                                docentejson.getString("nombre"),
                                docentejson.getString("apellido"),
                                docentejson.getString("profesion"),
                                docentejson.getString("descripcion"),
                                docentejson.getString("imagen")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public ArrayList<Docentes> getAllDocentes(String url, String id_posgrado){
        DocentesDao.url = url;
        DocentesDao.id_posgrado = id_posgrado;
        try {
            new getAllDocenteAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    private static class getAllDocenteAsyncTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> httpParams = new HashMap();
            HttpJsonParser httpJsonParser = new HttpJsonParser();

            httpParams.put("id_posgrado", id_posgrado);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_all_docentes.php", "GET", httpParams);
            if(jsonObject!=null) {
                try {
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray docentesjson = jsonObject.getJSONArray("data");
                        for (int i = 0; i < docentesjson.length(); i++) {
                            JSONObject docenteitem = docentesjson.getJSONObject(i);
                            docentes.add(new Docentes(
                                    docenteitem.getString("nombre"),
                                    docenteitem.getString("apellido"),
                                    docenteitem.getString("profesion"),
                                    docenteitem.getString("descripcion"),
                                    docenteitem.getString("imagen")
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
