package co.edu.uniautonoma.posgradosapp.Dao;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Horarios;

public class HorariosDao {

    private static ArrayList<Horarios> horarios;
    private static String url;
    private static String id_posgrado;
    private static int semestre;

    public ArrayList<Horarios> getHorario (String url, String id_posgrado, int semestre){
        HorariosDao.url = url;
        HorariosDao.id_posgrado = id_posgrado;
        HorariosDao.semestre = semestre;
        try {
            new HorariosAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    private static class HorariosAsyncTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String,String> httpParams = new HashMap();

            httpParams.put("id_posgrado", id_posgrado);
            httpParams.put("semestre", Integer.toString(semestre));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_horario.php", "GET", httpParams);
            if(jsonObject!=null) {
                try {
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray horariosjson = jsonObject.getJSONArray("data");
                        horarios = new ArrayList<>();
                        for (int i = 0; i < horariosjson.length(); i++) {
                            JSONObject horarioitem = horariosjson.getJSONObject(i);
                            horarios.add(new Horarios(
                                    horarioitem.getString("dia"),
                                    horarioitem.getString("hora_inicio"),
                                    horarioitem.getString("hora_fin"),
                                    horarioitem.getString("sede"),
                                    horarioitem.getString("salon")
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
