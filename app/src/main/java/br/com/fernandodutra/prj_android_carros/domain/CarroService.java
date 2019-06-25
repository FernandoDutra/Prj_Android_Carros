package br.com.fernandodutra.prj_android_carros.domain;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.fernandodutra.prj_android_carros.R;
import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:08
 * Prj_Android_Carros
 */
public class CarroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";
    private static final String URL = "http://www.livroandroid.com.br/livro/carros/v2/carros_{tipo}.json";

    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        List<Carro> carros = null;
        if (tipo == R.string.favoritos) {
            // Consulta no banco de dados
            CarroDB db = new CarroDB(context);
            carros = db.findAll();
        } else {
            String tipoString = getTipo(tipo);
            String url = URL.replace("{tipo}", tipoString);
            // Faz a requisição HTTP do servidor e retorna a string com o conteúdo
            HttpHelper http = new HttpHelper();
            String json = http.doGet(url);
            carros = parserJSON(context, json);
        }
        return carros;
    }

    private static String getTipo(int tipo) {
        if (tipo == R.string.classicos) {
            return "classicos";
        } else if (tipo == R.string.esportivos) {
            return "esportivos";
        }
        return "luxo";
    }

    // Método para leitura dos arquivos JSON que estão no diretório raw'
    private static String readFile(Context context, int tipo) throws IOException {
        if (tipo == R.string.classicos) {
            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");
        } else if (tipo == R.string.esportivos) {
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");
        }
        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");
    }

    private static List<Carro> parserJSON(Context context, String json) throws IOException {
        List<Carro> carros = new ArrayList<Carro>();
        try {
            JSONArray jsonCarros = new JSONArray(json);

            // Insere cada carro na lista
            for (int i = 0; i < jsonCarros.length(); i++) {
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();

                // Lê as informações de cada Carro
                c.setId(jsonCarro.getLong("id"));
                c.setNome(jsonCarro.getString("nome"));
                c.setDesc(jsonCarro.getString("desc"));
                c.setUrlFoto(jsonCarro.getString("url_foto"));
                c.setUrlInfo(jsonCarro.getString("url_info"));
                c.setUrlVideo(jsonCarro.getString("url_video"));
                c.setLatitude(jsonCarro.getString("latitude"));
                c.setLongitude(jsonCarro.getString("longitude"));

                if (LOG_ON) {
                    Log.d(TAG, "Carro " + c.getNome() + " > " + c.getUrlFoto());
                }
                carros.add(c);
            }
            if (LOG_ON) {
                Log.d(TAG, carros.size() + " encontradoos.");
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            throw new IOException(e.getMessage(), e);
        }
        return carros;
    }
}
