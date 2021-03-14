package br.com.mbcustom.purodesejostore;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Destaque implements Parcelable {

    private String uuidProduto;
//    private String descricaoProduto;
    private String urlDestaque;
    private String categoriaProduto;
//    private String valorProduto;
//    private String quantidadeProduto;
//    private String classificacao3;
//    private String classificacao4;
//    private String classificacao5;
//    private String tamanhoProduto;
//    private String grandezaProduto;

    public Destaque(){

    }

    public Destaque(String uuidproduto, String urldestaque, String categoriaproduto) {

        this.uuidProduto = uuidproduto;
        this.categoriaProduto = categoriaproduto;
  //      this.descricaoProduto = descricaoproduto;
  //      this.categoriaProduto = categoriaproduto;
        this.urlDestaque = urldestaque;

    }

    protected Destaque(Parcel in) {
        uuidProduto = in.readString();
//        nomeProduto = in.readString();
//        descricaoProduto = in.readString();
        categoriaProduto = in.readString();
        urlDestaque = in.readString();

    }

    public static final Creator<Destaque> CREATOR = new Creator<Destaque>() {
        @Override
        public Destaque createFromParcel(Parcel in) {
            return new Destaque(in);
        }

        @Override
        public Destaque[] newArray(int size) {
            return new Destaque[size];
        }
    };

    public String getUuidproduto() {

        Log.d("XXX", "Entrou aqui no loja");
        return uuidProduto;
    }

    public void setUuidproduto(String uuidproduto) {
        this.uuidProduto = uuidproduto;
    }

    public String getCategoriaproduto() {
        return categoriaProduto;
    }

    public void setCategoriaproduto(String categoriaproduto) {
        this.categoriaProduto = categoriaProduto;
    }

//    public String getDescricaoproduto() {
//        return descricaoProduto;
//    }

//    public void setDescricaoproduto(String descricaoProduto) {
//        this.descricaoProduto = descricaoProduto;
//    }



//    public String getCategoriaproduto() {
//        return categoriaProduto;
//    }

//    public void setCategoriaproduto(String categoriaproduto) {
//        this.categoriaProduto = categoriaProduto;
//    }

    public String getUrldestaque() {

        return urlDestaque;
    }

    public void setUrldestaque(String urlDestaque) {
        this.urlDestaque = urlDestaque;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuidProduto);
        dest.writeString(categoriaProduto);
//        dest.writeString(descricaoProduto);
//        dest.writeString(categoriaProduto);
        dest.writeString( urlDestaque );
    }
}
