package co.edu.uniautonoma.posgradosapp.Dao;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Escuela;

public class EscuelaDao {

    private static String url;
    private static Escuela escuela;

    public Escuela getEscuela(String url){
        EscuelaDao.url = url;
        try {
            new EscuelaAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return escuela;
    }

    private static class EscuelaAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String, String> parametrosHttp = new HashMap<>();

            parametrosHttp.put("id", "1");

            JSONObject escuelaJson = httpJsonParser.makeHttpRequest(
                    url + "get_escuela.php", "GET", parametrosHttp);
            if(escuelaJson!=null) {
                try {
                    int success = escuelaJson.getInt("success");
                    if (success == 1) {
                        JSONObject datosescuela = escuelaJson.getJSONObject("data");
                        escuela = new Escuela("Director: " + datosescuela.getString("director"),
                                datosescuela.getString("descripcion"),
                                datosescuela.getString("correo"),
                                datosescuela.getString("direccion"),
                                Double.parseDouble(datosescuela.getString("coordenada1")),
                                Double.parseDouble(datosescuela.getString("coordenada2"))
                                );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
