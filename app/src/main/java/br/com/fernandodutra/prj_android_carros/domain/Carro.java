package br.com.fernandodutra.prj_android_carros.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:07
 * Prj_Android_Carros
 */
public class Carro implements Parcelable {

    private static final long serialVersionUID = 6601006766842473959L;
    private long id;
    private String tipo;
    private String nome;
    private String desc;
    private String urlFoto;
    private String urlInfo;
    private String urlVideo;
    private String latitude;
    private String longitude;

    public long getId() {
        return id;
    }

    public void setId(long idd) {
        this.id = idd;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Carro{ nome = " + this.getNome() + "\n" + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Escreve dos dados na mesma ordem em que foram escritos
        dest.writeLong(this.getId());
        dest.writeString(this.getTipo());
        dest.writeString(this.getNome());
        dest.writeString(this.getDesc());
        dest.writeString(this.getUrlFoto());
        dest.writeString(this.getUrlInfo());
        dest.writeString(this.getUrlVideo());
        dest.writeString(this.getLatitude());
        dest.writeString(this.getLongitude());
    }

    public void readFromParcel(Parcel parcel) {
        // Lê os dados na mesma ordem em que foram escritos
        this.setId(parcel.readLong());
        this.setTipo(parcel.readString());
        this.setNome(parcel.readString());
        this.setDesc(parcel.readString());
        this.setUrlFoto(parcel.readString());
        this.setUrlInfo(parcel.readString());
        this.setUrlVideo(parcel.readString());
        this.setLatitude(parcel.readString());
        this.setLongitude(parcel.readString());
    }

    public static final Creator<Carro> CREATOR = new Creator<Carro>() {
        @Override
        public Carro createFromParcel(Parcel source) {
            Carro c = new Carro();
            c.readFromParcel(source);
            return c;
        }

        @Override
        public Carro[] newArray(int size) {
            return new Carro[size];
        }
    };
}
