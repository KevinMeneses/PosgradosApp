package co.edu.uniautonoma.posgradosapp.Dao;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Posgrados;
import co.edu.uniautonoma.posgradosapp.R;
import xdroid.toaster.Toaster;

public class PosgradosDao {

    private static String url;
    private static String id_posgrado;
    private static Posgrados posgrado;
    private static ArrayList<Posgrados> posgrados;

    public Posgrados getPosgrado(String url, String id_posgrado){
        PosgradosDao.url = url;
        PosgradosDao.id_posgrado = id_posgrado;
        try {
            new getPosgradoAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return posgrado;
    }

    public ArrayList<Posgrados> getAllPosgrados(String url){
        PosgradosDao.url = url;
        try {
            new getAllPosgradosAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return posgrados;
    }

    private static class getPosgradoAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String, String> httpParams = new HashMap();

            httpParams.put("id_posgrado", id_posgrado);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_posgrado.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONObject posgradojson = jsonObject.getJSONObject("data");
                    posgrado = new Posgrados(posgradojson.getString("nombre"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class getAllPosgradosAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_all_posgrados.php", "GET", null);
            if(jsonObject!=null) {
                try {
                    int success = jsonObject.getInt("success");
                    JSONArray posgradosjson;
                    if (success == 1) {
                        posgradosjson = jsonObject.getJSONArray("data");
                        posgrados = new ArrayList<>();
                        for (int i = 0; i < posgradosjson.length(); i++) {
                            JSONObject posgradoitem = posgradosjson.getJSONObject(i);
                            posgrados.add(new Posgrados(posgradoitem.getString("id"),
                                    posgradoitem.getString("cod_snies"),
                                    posgradoitem.getString("nombre"),
                                    posgradoitem.getString("duracion"),
                                    posgradoitem.getInt("totalcreditos"),
                                    posgradoitem.getString("descripcion"),
                                    posgradoitem.getString("valorsemestre")
                            ));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toaster.toast(R.string.EstadoServidor);
            }
            return null;
        }
    }
}
