package br.com.fernandodutra.prj_android_carros.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.adapter.TabsAdapter;
import livroandroid.lib.utils.PermissionUtils;
import livroandroid.lib.utils.Prefs;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:05
 * Prj_Android_Carros
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        setupNavDrawer();
        setupViewPagerTabs();
        setupFAB();
        setupPermissoes();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Veja o código-fonte de exemplo deste capítulo
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setupPermissoes() {
        // Solicita as permissões
        String[] permissoes = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
        };
        PermissionUtils.validate(this, 0, permissoes);
    }

    private void setupFAB() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack(v, "Exemplo de FAB Button.");
            }
        });
    }

    // Configura o ViewPager + Tabs
    private void setupViewPagerTabs() {
        // ViewPager
        final ViewPager viewPager = (ViewPager) (findViewById(R.id.vp_viewpager));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new TabsAdapter(getContext(), getSupportFragmentManager()));
        // Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_tablayout);
        // Cria as tabs com o mesmo adapter utilizado pelo ViewPager
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(), R.color.white);
        // Cor branca no texto (o fundo azul foi definido no layout)
        tabLayout.setTabTextColors(cor, cor);

        // Lê o Índice da última tab utilizada no aplicativo
        int tabIdx = Prefs.getInteger(getContext(), "tabIdx");
        viewPager.setCurrentItem(tabIdx);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Salva o ìndice da página/tab selecionada
                Prefs.setInteger(getContext(), "tabIdx", viewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
