package br.com.fernandodutra.prj_android_carros.activity;

import android.os.Bundle;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.fragments.CarrosFragment;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:05
 * Prj_Android_Carros
 */
public class CarrosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros);

        // Configura o Toolbar
        setUpToolbar();

        // Mostra o botão voltar "up navigation"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Mostra o tipo d ocarro no título
        String tipo = getString(getIntent().getIntExtra("tipo", 0));

        getSupportActionBar().setTitle(tipo);

        // Adicionar o fragment com o mesmo Bundle (args) da intent
        if (savedInstanceState == null){
            // Cria uma instância do Fragment, e configura os argumentos.
            CarrosFragment frag = new CarrosFragment();

            // Cria uma instância do fragment que foram passados para a activity, está o tipo do carro.
            frag.setArguments(getIntent().getExtras());

            // Adiciona o fragment no layout de marcação
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }
}
