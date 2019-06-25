package br.com.fernandodutra.prj_android_carros.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.fernandodutra.prj_android_carros.R;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:04
 * Prj_Android_Carros
 */
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {

    protected DrawerLayout drawerLayout;

    // Configura a Toolbar
    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    // Configura o nav drawer
    protected void setupNavDrawer() {
        // Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        // Ícone do menu do Nav Drawer
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null && drawerLayout != null) {
            // Atualiza a imagem e os textos do header
            setNavViewValues(navigationView, R.string.nav_drawer_username, R.string.nav_drawer_email, R.mipmap.ic_launcher);
            // Trata o evento de clique no menu
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // Seleciona a linha
                            //menuItem.setChecked(true);
                            // Fecha o menu
                            drawerLayout.closeDrawers();
                            // Trata o evento do menu
                            onNavDrawerItemSelected(menuItem);
                            return true;
                        }
                    });
        }
    }

    // Atualiza os dados do Header do Navigation View
    private static void setNavViewValues(NavigationView navView, int nome, int email, int img) {
        View headerView = navView.getHeaderView(0);
        TextView tNome = headerView.findViewById(R.id.tNome);
        TextView tEmail = headerView.findViewById(R.id.tEmail);
        ImageView imgView = headerView.findViewById(R.id.img);

        tNome.setText(nome);
        tEmail.setText(email);
        imgView.setImageResource(img);
    }

    // Trava o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_carros_todos:
                break;
            case R.id.nav_item_carros_classicos:
                Intent intent = new Intent(getContext(), CarrosActivity.class);
                intent.putExtra("tipo", R.string.classicos);
                startActivity(intent);
                break;
            case R.id.nav_item_carros_esportivos:
                intent = new Intent(getContext(), CarrosActivity.class);
                intent.putExtra("tipo", R.string.esportivos);
                startActivity(intent);
                break;
            case R.id.nav_item_carros_luxuosos:
                intent = new Intent(getContext(), CarrosActivity.class);
                intent.putExtra("tipo", R.string.luxo);
                startActivity(intent);
                break;
            case R.id.nav_item_site_livro:
                intent = new Intent(getContext(), SiteLivroActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_item_settings:
                break;
            case R.id.nav_item_camera:
                intent = new Intent(getContext(), CameraActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Trata o clique no botão que abre o menu
                if (drawerLayout != null) {
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o Menu Lateral
    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Fecha o Menu Lateral
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

}
