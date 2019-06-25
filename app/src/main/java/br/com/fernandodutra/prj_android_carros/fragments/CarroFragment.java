package br.com.fernandodutra.prj_android_carros.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.fernandodutra.prj_android_carros.CarrosApplication;
import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.domain.Carro;
import br.com.fernandodutra.prj_android_carros.domain.CarroDB;
import livroandroid.lib.task.TaskListener;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:14
 * Prj_Android_Carros
 */
public class CarroFragment extends BaseFragment {

    private Carro carro;
    private FloatingActionButton fab;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla a view deste Fragment
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        // Lê o objeto carro dos parâmetros
        carro = (Carro) getArguments().getParcelable("carro");

        // Latitude e Longitude
        setupDetalheCarro(view);

        // Favoritos
        setupFab();

        // Imagem
        setupPlayVideo(view);

        // Mapa
        setupMapa();

        return view;
    }

    private void setupDetalheCarro(View view) {
        // Atualiza a descrição do carro no TextView
        TextView txt_fragment_carro_desc = (TextView) view.findViewById(R.id.txt_fragment_carro_desc);
        TextView txt_fragment_carro_latlng = (TextView) view.findViewById(R.id.txt_fragment_carro_latlng);

        String latlng = carro.getLatitude() + " / " + carro.getLongitude();
        txt_fragment_carro_latlng.setText(latlng);
        txt_fragment_carro_desc.setText(carro.getDesc());
    }

    private void setupMapa() {
        MapaFragment mapaFragment = new MapaFragment();
        mapaFragment.setArguments(getArguments());
        getChildFragmentManager().beginTransaction().replace(R.id.mapaFragment, mapaFragment).commit();
    }

    private void setupPlayVideo(View view) {
        imageView = (ImageView) view.findViewById(R.id.img_fragment_carro_playvideo);
        imageView.setOnClickListener(onClickPlayVideo());
    }

    private View.OnClickListener onClickPlayVideo() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent para tocar o vídeo no Player Nativo
                String url = carro.getUrlVideo();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "video/*");
                startActivity(intent);
            }
        };
    }

    public void setupFab() {
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_floatingactionbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask("favorito", taskFavoritar());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Verifica se o carro está favoritado e troca a cor do botão FAB
        startTask("checkFavorito", checkFavorito());
    }

    private TaskListener checkFavorito() {
        return new BaseTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                CarroDB db = new CarroDB(getContext());
                // Verifica se este carro já foi salvo
                boolean exists = db.exists(carro.getNome());
                return exists;
            }

            @Override
            public void updateView(Boolean response) {
                setFABColor(response);
            }
        };
    }

    private void setFABColor(Boolean response) {
        if (response) {
            // Cor do botão FAB se está sendo favoritado
            fab.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.accent));
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.yellow));
        } else {
            // Cor do botão FAB se não está sendo favoritado
            fab.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.accent));
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));
        }
    }

    private TaskListener taskFavoritar() {
        return new BaseTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                CarroDB db = new CarroDB(getContext());
                // Verifica se este carro já foi salvo
                boolean exists = db.exists(carro.getNome());
                if (!exists) {
                    // Adiciona nos favoritos
                    db.save(carro);
                    return true;
                } else {
                    // Remove dos favoritos
                    db.delete(carro);
                    return false;
                }
            }

            @Override
            public void updateView(Boolean response) {
                // Mostra a msg na UI Thread
                if (response) {
                    snack(getView(), "Carro adicionado aos favoritos.");
                } else {
                    snack(getView(), "Carro removido dos favoritos.");
                }
                setFABColor(response);

                // Envia o evento para o bus depois de clicar no botão FAB
                CarrosApplication.getInstance().getBus().post("refresh");
            }
        };
    }
}
