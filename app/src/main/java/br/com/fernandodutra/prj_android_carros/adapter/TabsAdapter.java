package br.com.fernandodutra.prj_android_carros.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.fragments.CarrosFragment;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:07
 * Prj_Android_Carros
 */
public class TabsAdapter extends FragmentPagerAdapter {


    private Context context;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        // Quantidade de Páginas
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Esse título será mostrado nas Tabs
        if (position == 0) {
            return context.getString(R.string.classicos);
        } else if (position == 1) {
            return context.getString(R.string.esportivos);
        } else if (position == 2) {
            return context.getString(R.string.luxo);
        }
        return context.getString(R.string.favoritos);
    }

    @Override
    public Fragment getItem(int position) {
        // Cria o Fragment oara cada página
        Fragment f = null;
        if (position == 0) {
            f = CarrosFragment.newInstance(R.string.classicos);
        } else if (position == 1) {
            f = CarrosFragment.newInstance(R.string.esportivos);
        } else if (position == 2) {
            f = CarrosFragment.newInstance(R.string.luxo);
        } else if (position == 3) {
            f = CarrosFragment.newInstance(R.string.favoritos);
        }
        return f;
    }
}
