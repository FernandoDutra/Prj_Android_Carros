package br.com.fernandodutra.prj_android_carros.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.domain.Carro;
import livroandroid.lib.utils.PermissionUtils;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:15
 * Prj_Android_Carros
 */
public class MapaFragment extends BaseFragment implements OnMapReadyCallback {

    // O Objeto que controla o Google Maps
    private GoogleMap map;
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        // Recupera o Fragment que está no Layout
        // Utiliza o getChildFragmentManager(), pois é um fragment dentro do outro
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        // Inicia o Google Maps dentro do Fragment
        mapFragment.getMapAsync(this);
        this.carro = (Carro) getArguments().getParcelable("carro");

        return view;
    }

    private void setupPermissoes() {
        // Solicita as permissões
        String[] permissoes = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
        };
        PermissionUtils.validate(getActivity(), 0, permissoes);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // O método onMapready(map) é chamado quando a ainicialização do mapa estiver OK
        this.map = googleMap;
        if (carro != null) {
            // Ativa o botão para mostrar minha localização
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                setupPermissoes();
            } else {
                map.setMyLocationEnabled(true);
                // Cria o objeto LatLng com a coordenada da fábrica
                LatLng location = new LatLng(Double.parseDouble(carro.getLatitude()), Double.parseDouble(carro.getLongitude()));
                // Posiciiona o mapa com a coordenada da fábrica (zoom = 13)
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 13);
                map.moveCamera(update);
                // Marcador no local da fábrica
                map.addMarker(new MarkerOptions().title(carro.getNome()).snippet(carro.getDesc()).position(location));
                // Tipo do Mapa:
                // (normal, satélite, terreno ou híbrido)
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
