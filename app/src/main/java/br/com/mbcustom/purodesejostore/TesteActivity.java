package br.com.mbcustom.purodesejostore;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class TesteActivity extends AppCompatActivity {

    private String evento, uidFavorito;
    private Favorito favorito;
    private Produto produto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_teste );

        evento = getIntent().getExtras().getString("evento");

        if(evento.equals( "favorito" )){
            favorito = getIntent().getExtras().getParcelable("dadosFavorito");
            Log.d("teste","entou no IF favorito");
            uidFavorito = favorito.getUuidproduto();
            Log.d("teste", "UidFavorito: " + uidFavorito);

        }else{
            produto = getIntent().getExtras().getParcelable( "dadosItem" );
        }

    }
}
