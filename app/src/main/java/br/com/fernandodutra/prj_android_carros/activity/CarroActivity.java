package br.com.fernandodutra.prj_android_carros.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.domain.Carro;
import br.com.fernandodutra.prj_android_carros.fragments.CarroFragment;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:05
 * Prj_Android_Carros
 */
public class CarroActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);

        // Configura o Toolbar e botão Up Navigation
        setUpToolbar();

        // Configura o Toolbar e botão Up Navigation
        Carro c = (Carro) getIntent().getParcelableExtra("carro");
        getSupportActionBar().setTitle(c.getNome());

        // Liga o botão Ip Navigation para voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Imagem do Cabeçalho na AppBar
        ImageView appImgBar = (ImageView) findViewById(R.id.img_appbarimg);
        Picasso.with(getContext()).load(c.getUrlFoto()).into(appImgBar);

        if (savedInstanceState == null) {
            // Cria o Fragment com o mesmo Bundle (args) da Intent
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            // Adicionar o fragment no layout
            getSupportFragmentManager().beginTransaction().add(R.id.CarroFragment, frag).commit();
        }
    }
}
