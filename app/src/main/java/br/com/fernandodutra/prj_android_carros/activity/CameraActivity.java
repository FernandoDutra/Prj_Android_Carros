package br.com.fernandodutra.prj_android_carros.activity;

import android.os.Bundle;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.fragments.CameraFragment;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:04
 * Prj_Android_Carros
 */
public class CameraActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Configura o Toolbar
        setUpToolbar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.camera);

        if (savedInstanceState == null) {
            CameraFragment frag = new CameraFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.CameraFragment, frag).commit();
        }
    }
}
