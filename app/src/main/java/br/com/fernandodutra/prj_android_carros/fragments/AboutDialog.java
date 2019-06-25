package br.com.fernandodutra.prj_android_carros.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import br.com.fernandodutra.prj_android_carros.R;
import livroandroid.lib.utils.AndroidUtils;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:14
 * Prj_Android_Carros
 */
public class AboutDialog extends DialogFragment {

    // Método utilitário para mostrar o dialog
    public static void showAbout(android.support.v4.app.FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new AboutDialog().show(ft, "dialog_about");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Cria o HTML com o texo de sobre o Aplicativo
        SpannableStringBuilder aboutbody = new SpannableStringBuilder();

        // Versão do Aplicativo
        String versionName = AndroidUtils.getVersionName(getActivity());

        // Converte o texto do string.xml para HTML
        aboutbody.append(Html.fromHtml(getString(R.string.about_dialog_text, versionName)));

        // Infla o Layout
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TextView view = (TextView) inflater.inflate(R.layout.dialog_about, null);
        view.setText(aboutbody);
        view.setMovementMethod(new LinkMovementMethod());

        // Cria o dialog customizado
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
