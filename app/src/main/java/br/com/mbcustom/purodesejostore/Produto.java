package br.com.mbcustom.purodesejostore;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Produto implements Parcelable {

    private String uuidProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private String valorProduto;
    private String quantidadeProduto;
    private String categoriaProduto;
    private String valorDesconto;
    private String estadoAtual;
    private Integer quantidadeFavorito;
    private String classificacao5;
    private String tamanhoProduto;
    private String grandezaProduto;
    private String urlProduto;

    public Produto(){

    }

    public Produto(String uuidproduto, String nomeproduto, String descricaoproduto,
                   String valorproduto, String quantidadeproduto, String categoriaproduto,
                   String valordesconto, String estadoatual, Integer quantidadefavorito,
                   String classificacao5, String tamanhoproduto, String grandezaproduto,
                   String urlproduto) {

        this.uuidProduto = uuidproduto;
        this.nomeProduto = nomeproduto;
        this.descricaoProduto = descricaoproduto;
        this.valorProduto = valorproduto;
        this.quantidadeProduto = quantidadeproduto;
        this.categoriaProduto = categoriaproduto;
        this.valorDesconto = valordesconto;
        this.estadoAtual = estadoatual;
        this.quantidadeFavorito = quantidadefavorito;
        this.classificacao5 = classificacao5;
        this.tamanhoProduto = tamanhoproduto;
        this.grandezaProduto = grandezaproduto;
        this.urlProduto = urlproduto;

    }

    protected Produto(Parcel in) {
        uuidProduto = in.readString();
        nomeProduto = in.readString();
        descricaoProduto = in.readString();
        valorProduto = in.readString();
        quantidadeProduto = in.readString();
        categoriaProduto = in.readString();
        valorDesconto = in.readString();
        estadoAtual = in.readString();
        quantidadeFavorito = in.readInt();
        classificacao5 = in.readString();
        tamanhoProduto = in.readString();
        grandezaProduto = in.readString();
        urlProduto = in.readString();

    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public String getUuidProduto() {

        Log.d("XXX", "Entrou aqui no loja");
        return uuidProduto;
    }

    public void setUuidProduto(String uuidProduto) {
        this.uuidProduto = uuidProduto;
    }

    public String getNomeproduto() {
        return nomeProduto;
    }

    public void setNomeproduto(String numeroloja) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoproduto() {
        return descricaoProduto;
    }

    public void setDescricaoproduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getValorproduto() {
        return valorProduto;
    }

    public void setValorproduto(String valorProduto) {
        this.valorProduto = valorProduto;
    }

    public String getQuantidadeproduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeproduto(String quantidadeproduto) {
        this.quantidadeProduto = quantidadeproduto;
    }

    public String getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(String valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(String estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public Integer getQuantidadeFavorito() {
        return quantidadeFavorito;
    }

    public void setQuantidadeFavorito(Integer quantidadeFavorito) {
        this.quantidadeFavorito = quantidadeFavorito;
    }

    public String getClassificacao5() {
        return classificacao5;
    }

    public void setClassificacao5(String classificacao5) {
        this.classificacao5 = classificacao5;
    }

    public String getTamanhoproduto() {
        return tamanhoProduto;
    }

    public void setTamanhoproduto(String tamanhoproduto) {
        this.tamanhoProduto = tamanhoproduto;
    }

    public String getGrandezaproduto() {

        return grandezaProduto;
    }

    public void setGrandezaproduto(String grandezaproduto) {
        this.grandezaProduto = grandezaproduto;
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
        dest.writeString(valorProduto);
        dest.writeString(quantidadeProduto);
        dest.writeString(categoriaProduto);
        dest.writeString(valorDesconto);
        dest.writeString(estadoAtual);
        dest.writeInt(quantidadeFavorito);
        dest.writeString(classificacao5);
        dest.writeString(tamanhoProduto);
        dest.writeString(grandezaProduto);
        dest.writeString( urlProduto );
    }
}
