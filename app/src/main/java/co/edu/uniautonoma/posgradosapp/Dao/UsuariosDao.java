package co.edu.uniautonoma.posgradosapp.Dao;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.Modelos.Usuarios;

public class UsuariosDao {

    private static String url;
    private static String correo;
    private static Usuarios usuario;

    public Usuarios getUsuario(String url, String correo){
        UsuariosDao.url = url;
        UsuariosDao.correo = correo;
        try {
            new UsuarioAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private static class UsuarioAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String, String> httpParams = new HashMap();

            httpParams.put("correo", correo);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url + "get_usuario_institucional.php", "GET", httpParams);
            if (jsonObject != null) {
                try {
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONObject usuariojson = jsonObject.getJSONObject("data");
                        usuario = new Usuarios(
                                usuariojson.getString("codigo"),
                                usuariojson.getString("nombre"),
                                usuariojson.getString("apellido"),
                                usuariojson.getString("correo"),
                                usuariojson.getString("id_posgrado"),
                                usuariojson.getInt("semestre"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

}
