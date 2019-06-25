package br.com.fernandodutra.prj_android_carros.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.List;

import br.com.fernandodutra.prj_android_carros.CarrosApplication;
import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.activity.CarroActivity;
import br.com.fernandodutra.prj_android_carros.adapter.CarroAdapter;
import br.com.fernandodutra.prj_android_carros.domain.Carro;
import br.com.fernandodutra.prj_android_carros.domain.CarroService;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:14
 * Prj_Android_Carros
 */
public class CarrosFragment extends BaseFragment {

    protected RecyclerView recyclerView;
    // Tipo do carro passado pelos argumentos
    private int tipo;
    // Lista de Carros
    private List<Carro> carros;

    private ProgressDialog dialog;

    // Método para instanciar esse frament pelo tipo
    public static CarrosFragment newInstance(int tipo) {
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        CarrosFragment f = new CarrosFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Lê o tipo dos Argumentos
            this.tipo = getArguments().getInt("tipo");
        }
        // Registra a classe para receber eventos
        CarrosApplication.getInstance().getBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos
        CarrosApplication.getInstance().getBus().unregister(this);
    }

    @Subscribe
    public void onBusAtualizarListaCarros(String refresh) {
        // Recebeu o evento, e atualizar a lista
        taskCarros();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros();
    }

    private void taskCarros() {
        new GetCarrosTask().execute();
    }

    // Da mesma forma que tratamos o evento de clique em um botão (OnClickListener)
    // Vamos trator o evento de clique na lista
    // A diferença é que a interface Carro.CarroOnClickListener nós mesmo criamos
    private CarroAdapter.CarroOnClickListener onClickCarro() {
        return new
                CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {
                // Carro selecionado
                Carro c = carros.get(idx);
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro", c);
                startActivity(intent);
            }
        };
    }

    // Task para a buscar os carros
    private class GetCarrosTask extends AsyncTask<Void, Void, List<Carro>> {
        // Atualiza a interface
        @Override
        protected void onPostExecute(List<Carro> carros) {
            if (carros != null) {
                CarrosFragment.this.carros = carros;
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
            }
        }

        @Override
        protected List<Carro> doInBackground(Void... voids) {
            try {
                // Busca os carros em Background (Thread)
                return CarroService.getCarros(getContext(), tipo);
            } catch (IOException e) {
                Log.e(TAG, "Erro: " + e.getMessage());
                return null;
            }
        }
    }

}
