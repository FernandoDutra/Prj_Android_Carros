package br.com.fernandodutra.prj_android_carros.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.fernandodutra.prj_android_carros.R;
import br.com.fernandodutra.prj_android_carros.domain.Carro;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:06
 * Prj_Android_Carros
 */
public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarrosViewHolder> {

    protected static final String TAG = "livroandroid";
    private final Context context;

    // Lista de Carros
    private final List<Carro> carros;
    // Interface de callback para expor os eventos de toque na lista
    private CarroOnClickListener carroOnClickListener;

    // Construtor, geralmente recebe o contexto, a lista e a implementação da interface de callback
    public CarroAdapter(Context context, List<Carro> carros, CarroOnClickListener carroOnClickListener) {
        this.context = context;
        this.carros = carros;
        this.carroOnClickListener = carroOnClickListener;
    }

    @Override
    public int getItemCount() {
        // Retorna a quantidade de linhas da lista (geramente é a quantidade de elementos do array)
        // Uma view será criada para cada linha
        return this.carros != null ? this.carros.size() : 0;
    }

    @NonNull
    @Override
    public CarrosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Este método deve inflar a view e criar um ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_carro, viewGroup, false);
        // Depois de inflar a view (R.layout.adapter_carro), criar o ViewHolder
        // O ViewHolder contém a referência para todas as views do layout
        CarrosViewHolder holder = new CarrosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarrosViewHolder holder, final int position) {
        // Recupera o carro da linha x
        Carro c = carros.get(position);
        // Faz a ligação (bind) das informações do carro, com as views do layout
        holder.t_adapter_carro.setText(c.getNome());
        holder.pb_adapter_carro.setVisibility(View.VISIBLE);
        // Faz o download da foto e mostra o ProgressBar
        Picasso.with(context).load(c.getUrlFoto()).fit().into(holder.img_adapter_carro,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // Download OK, então esconde o progress
                        holder.pb_adapter_carro.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        //
                        holder.pb_adapter_carro.setVisibility(View.GONE);
                    }
                });

        // Click
        if (carroOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ao Clicar na view da lista, chama a interface de callback(listener)
                    // Obs: A varia´vel position é final
                    carroOnClickListener.onClickCarro(holder.itemView, position);
                }
            });
        }
    }

    // Interface de callback para export os eventos da lista
    public interface CarroOnClickListener {
        public void onClickCarro(View view, int idx);
    }

    // O ViewHolder possui a referência para as  views do layout
    public static class CarrosViewHolder extends RecyclerView.ViewHolder {
        public TextView t_adapter_carro;
        public ImageView img_adapter_carro;
        public ProgressBar pb_adapter_carro;
        public CardView cv_adapter_carro;

        private CarrosViewHolder(View view) {
            super(view);
            // Faz o findViewById(id) para armazenar as views
            // O Android vai reutilizar este ViewHolder durante a rolagem
            t_adapter_carro = (TextView) view.findViewById(R.id.t_adapter_carro);
            img_adapter_carro = (ImageView) view.findViewById(R.id.img_adapter_carro);
            pb_adapter_carro = (ProgressBar) view.findViewById(R.id.pb_adapter_carro);
            cv_adapter_carro = (CardView) view.findViewById(R.id.cv_adapter_carro);
        }
    }
}
