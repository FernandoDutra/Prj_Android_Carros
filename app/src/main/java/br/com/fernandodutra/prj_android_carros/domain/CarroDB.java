package br.com.fernandodutra.prj_android_carros.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:08
 * Prj_Android_Carros
 */
public class CarroDB extends SQLiteOpenHelper {

    public static final String TAG = "sql";
    // Nome do Banco
    public static final String NOME_BANCO = "livro_android.sqlite";
    public static final int VERSA_BANCO = 1;

    public CarroDB(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BANCO, null, VERSA_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criado a Tabela carro...");
        db.execSQL("create table if not exists carro (_id bigint primary key, nome text, _desc text, " +
                "url_foto text, url_video text, url_info text, latitude text, longitude text, tipo text);");
        Log.d(TAG, "Tabela Carro criado com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versão do banco de dados, podemos executar um SQL aqui
        if (oldVersion == 1 && newVersion == 2) {
            // Mudou a versão
        }
    }

    // Insere um novo carro, ou atualiza se já existe
    public long save(Carro carro) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("_id", carro.getId());
            values.put("nome", carro.getNome());
            values.put("_desc", carro.getDesc());
            values.put("url_foto", carro.getUrlFoto());
            values.put("url_info", carro.getUrlInfo());
            values.put("url_video", carro.getUrlVideo());
            values.put("latitude", carro.getLatitude());
            values.put("longitude", carro.getLongitude());
            values.put("tipo", carro.getTipo());

            return db.insert("carro", "", values);
        } finally {
            db.close();
        }
    }

    // Deleta o carro
    public int delete(Carro carro) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from carro where _id=?
            int count = db.delete("carro", "_id=?", new String[]{String.valueOf(carro.getId())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }

    // Consulta a lista com todos os carros
    public List<Carro> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // select * from carro
            Cursor c = db.query("carro", null, null, null, null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    private List<Carro> toList(Cursor c) {
        List<Carro> carros = new ArrayList<Carro>();
        if (c.moveToFirst()) {
            do {
                Carro carro = new Carro();
                carros.add(carro);
                // Recupera ps atributos de carro
                carro.setId(c.getLong(c.getColumnIndex("_id")));
                carro.setLongitude(c.getString(c.getColumnIndex("longitude")));
                carro.setLatitude(c.getString(c.getColumnIndex("latitude")));
                carro.setUrlVideo(c.getString(c.getColumnIndex("url_video")));
                carro.setUrlInfo(c.getString(c.getColumnIndex("url_info")));
                carro.setUrlFoto(c.getString(c.getColumnIndex("url_foto")));
                carro.setNome(c.getString(c.getColumnIndex("nome")));
                carro.setDesc(c.getString(c.getColumnIndex("_desc")));
                carro.setTipo(c.getString(c.getColumnIndex("tipo")));
            } while (c.moveToNext());
        }
        return carros;
    }

    public boolean exists(String nome) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from carro
            Cursor c = db.query("carro", null, "nome=?", new String[]{nome}, null, null, null, null);
            boolean exists = c.getCount() > 0;
            return exists;
        } finally {
            db.close();
        }
    }

    // Executa um SQL
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }

    // Executa um SQL
    private void execSQL(String sql, Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        } finally {
            db.close();
        }
    }
}
