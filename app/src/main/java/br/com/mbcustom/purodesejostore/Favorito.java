package br.com.mbcustom.purodesejostore;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Favorito implements Parcelable {

    private String uuidProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private String urlProduto;
    private String categoriaProduto;
//    private String valorProduto;
//    private String quantidadeProduto;
//    private String classificacao3;
//    private String classificacao4;
//    private String classificacao5;
//    private String tamanhoProduto;
//    private String grandezaProduto;

    public Favorito(){

    }

    public Favorito(String uuidproduto, String nomeproduto, String descricaoproduto,
                    String categoriaproduto, String urlproduto) {

        this.uuidProduto = uuidproduto;
        this.nomeProduto = nomeproduto;
        this.descricaoProduto = descricaoproduto;
        this.categoriaProduto = categoriaproduto;
        this.urlProduto = urlproduto;

    }

    protected Favorito(Parcel in) {
        uuidProduto = in.readString();
        nomeProduto = in.readString();
        descricaoProduto = in.readString();
        categoriaProduto = in.readString();
        urlProduto = in.readString();

    }

    public static final Creator<Favorito> CREATOR = new Creator<Favorito>() {
        @Override
        public Favorito createFromParcel(Parcel in) {
            return new Favorito(in);
        }

        @Override
        public Favorito[] newArray(int size) {
            return new Favorito[size];
        }
    };

    public String getUuidproduto() {

        Log.d("XXX", "Entrou aqui no loja");
        return uuidProduto;
    }

    public void setUuidproduto(String uuidproduto) {
        this.uuidProduto = uuidproduto;
    }

    public String getNomeproduto() {
        return nomeProduto;
    }

    public void setNomeproduto(String numeroproduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoproduto() {
        return descricaoProduto;
    }

    public void setDescricaoproduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }



    public String getCategoriaproduto() {
        return categoriaProduto;
    }

    public void setCategoriaproduto(String categoriaproduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public String getUrlproduto() {

        return urlProduto;
    }

    public void setUrlproduto(String urlProduto) {
        this.urlProduto = urlProduto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuidProduto);
        dest.writeString(nomeProduto);
        dest.writeString(descricaoProduto);
        dest.writeString(categoriaProduto);
        dest.writeString( urlProduto );
    }
}
