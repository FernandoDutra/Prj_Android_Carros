package br.com.fernandodutra.prj_android_carros.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import br.com.fernandodutra.prj_android_carros.R;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:15
 * Prj_Android_Carros
 */
public class SiteLivroFragment extends BaseFragment {

    private static final String URL_SOBRE = "http://www.livroandroid.com.br/sobre.htm";
    private WebView webView;
    private ProgressBar progress;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_site_livro, container, false);
        webView = view.findViewById(R.id.webview);
        progress = view.findViewById(R.id.progress);

        setWebViewClient(webView);

        // Carrega a Página
        webView.loadUrl(URL_SOBRE);

        // Swipe to Refresh
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener());

        //Cores da Animação
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Atualiza a Página
                webView.reload();
            }
        };
    }

    private void setWebViewClient(final WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Liga o Progress
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Desliga o Progress
                progress.setVisibility(View.INVISIBLE);
                // Termina a animação do Swipe to Refresh
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("livroandroid", "webview url:" + url);
                if (url != null && url.endsWith("sobre.htm")){
                    AboutDialog.showAbout(getFragmentManager());
                    // Retorna true para informar que interceptamos o evento
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}
