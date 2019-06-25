package br.com.fernandodutra.prj_android_carros.activity;

import android.os.Bundle;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.fragments.SiteLivroFragment;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:06
 * Prj_Android_Carros
 */
public class SiteLivroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_livro);

        //  Configura o Toolbar
        setUpToolbar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.site_do_livro);

        if (savedInstanceState == null) {
            SiteLivroFragment frag = new SiteLivroFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }
}
