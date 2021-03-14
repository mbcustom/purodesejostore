package br.com.mbcustom.purodesejostore;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ProdutoActivity extends AppCompatActivity {

    private TextView mNomeProduto, mValorProduto, mDescricaoProduto, mValorDesconto, mEstadoAtual,
            mTextQuantidade, mTextNomeRelacionado1, mTextNomeRelacionado2, mTextNomeRelacionado3,
            mTextNomeRelacionado4;

    private Button mButtonFavorito, mBotaoPedido;
    private String uidProduto, evento, uidFavorito, uidDestaque;

    private Favorito favorito;
    private Produto produto;
    private Destaque destaque;

    private ConstraintLayout mConstraintRelacionado1, mConstraintRelacionado2, mConstraintRelacionado3,
            mConstraintRelacionado4;

    private ImageView mImgProduto, mImgItemRelacionado1, mImgItemRelacionado2, mImgItemRelacionado3,
            mImgItemRelacionado4;

    String nomeProduto, descricaoProduto, quantidade, valorProduto, statusFavorito, urlProduto, uid,
            valorDesconto, estadoAtual, categoria, categoriaRecomendado, quantidadeFavString,
            produtoSelecionado;

    String uidRelacionado1, uidRelacionado2, uidRelacionado3, uidRelacionado4;

    int quantidadeFavorito;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView mImgTraco;
    private ConstraintLayout mConstraintPrincipal, mConstraintSecundaria;
    private TextView mTextQuantidadeFavoritos;
    private String categoria1;

    private GroupAdapter adapter;
    private ScrollView mScrool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_produto );

        getSupportActionBar().hide();
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        uid = FirebaseAuth.getInstance().getUid();

        statusFavorito = "";

        IniciaComponentes();

        produtoSelecionado = "";

        evento = getIntent().getExtras().getString("evento");

        Log.d("teste", "UidFavorito: " + evento);

        mConstraintSecundaria.setVisibility( View.VISIBLE );
        mConstraintPrincipal.setVisibility( View.INVISIBLE );

        VerificaEvento();

        Click();

    }

    private void VerificaEvento() {

        ////Verifica se a solicitação veio de favoritos (categoria favorito)
        if(evento.equals( "favorito" )){
            favorito = getIntent().getExtras().getParcelable("dadosNaoCategoria");
            Log.d("teste","entou no IF não categoria");
            uidFavorito = favorito.getUuidproduto();
//            Log.d("teste", "UidFavorito: " + uidFavorito);
            categoria = favorito.getCategoriaproduto();

            RecebeDados();

            fetchCategoria();

        }else {
            if (evento.equals( "destaque" )) {
                destaque = getIntent().getExtras().getParcelable( "dadosNaoCategoria" );
                Log.d( "teste", "entou no IF não categoria" );
                uidFavorito = destaque.getUuidproduto();
//            Log.d("teste", "UidFavorito: " + uidFavorito);
                categoria = destaque.getCategoriaproduto();

                RecebeDados();

                fetchCategoria();

            } else {
                //Verifica se a solicitação veio de recomendados (final da pagina de produtos)
                Log.d( "testes", "Entrou no Else" );
                if (evento.equals( "recomendados" )) {
//                    destaque = getIntent().getExtras().getParcelable( "dadosDestaque" );
                    categoria = categoriaRecomendado;
                    Log.d( "teste", "Entrou em recomendado: " + categoria );

                    RecebeDados();

                    fetchCategoria();

                } else {
                    //Vem direto da categoria
                    produto = getIntent().getExtras().getParcelable( "dadosCategoria" );

                    RecebeDados();

                    fetchCategoria();
                }
            }
        }

    }

    private void Click() {

//        CarregaDados();

        mImgItemRelacionado1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mConstraintSecundaria.setVisibility( View.VISIBLE );
                mConstraintPrincipal.setVisibility( View.INVISIBLE );

                produtoSelecionado = mTextNomeRelacionado1.getText().toString();

                Log.d("teste", "entrou em click relacionado");
                evento = "recomendado";
                uidFavorito = uidRelacionado1;
                VerificaEvento( );

                Log.d("teste", "AAAAAAAAAA Valor add a uidFavorito " + uidFavorito);
            }
        } );

        mImgItemRelacionado2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mConstraintSecundaria.setVisibility( View.VISIBLE );
                mConstraintPrincipal.setVisibility( View.INVISIBLE );

                produtoSelecionado = mTextNomeRelacionado2.getText().toString();

                evento = "recomendado";
                uidFavorito = uidRelacionado2;
                VerificaEvento( );

                Log.d("teste", "AAAAAAAAAA Valor add a uidFavorito " + uidFavorito);
            }
        } );

        mImgItemRelacionado3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mConstraintSecundaria.setVisibility( View.VISIBLE );
                mConstraintPrincipal.setVisibility( View.INVISIBLE );

                evento = "recomendado";
                uidFavorito = uidRelacionado3;
                VerificaEvento(  );

                Log.d("teste", "AAAAAAAAAA Valor add a uidFavorito " + uidFavorito);
            }
        } );

        mImgItemRelacionado4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mConstraintSecundaria.setVisibility( View.VISIBLE );
                mConstraintPrincipal.setVisibility( View.INVISIBLE );

                evento = "recomendado";
                uidFavorito = uidRelacionado4;
                VerificaEvento(  );

                Log.d("teste", "AAAAAAAAAA Valor add a uidFavorito " + uidFavorito);
            }
        } );

        mBotaoPedido.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto;

                texto = ("Olá, gostei do produto " + nomeProduto + ", como faço para finalizar a compra?");

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.putExtra("jid", "556584590193@s.whatsapp.net");
                sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                try {
                    startActivity(sendIntent);
                } catch (ActivityNotFoundException e) {

                    alert( "Você não possue o Whatsapp instalado em seu dispositivo" );
                }

           //     Uri uri = Uri.parse("smsto:+556584590193");
           //     Intent i = new Intent(Intent.ACTION_SENDTO, uri);
           //     i.setPackage("com.whatsapp");
           //     startActivity(Intent.createChooser(i, ""));

                /*         ********************** ENVIAR MSG PARA O WHATSAPP
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                 */
            }
        } );

        mButtonFavorito.setOnClickListener(     new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (statusFavorito == "") {

                    quantidadeFavorito += 1;

                    Log.d( "TesteFavorito", "UID: " + uid );

                    Map<String, Object> itemMenu = new HashMap<>();

                    itemMenu.put( "nomeProduto", nomeProduto );
                    itemMenu.put( "urlProduto", urlProduto );
                    itemMenu.put( "uuidProduto", uidProduto );
                   // itemMenu.put( "favoritoProduto", "Add" );
                    itemMenu.put( "categoriaProduto", categoria);

                    FirebaseFirestore.getInstance().collection( "/Usuarios" ).document( uid )
                            .collection( "/Favoritos" ).document( uidProduto )
                            .set( itemMenu ).addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( "Testes", "Document has been saved!" );

                            alert( "Adicionado aos favoritos com Sucesso." );

                            mButtonFavorito.setBackgroundResource( R.drawable.coracao_preenchido );

                            statusFavorito = "Add";

                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w( "Teste", "Documento não salvo" );
                            alert( "Erro ao adicionar aos favoritos, tente novamente." );
                        }
                    } );

                    //Campo responsavel por add dados soltos no documento**********************************************
                    FirebaseFirestore.getInstance().collection(categoria).document(uidProduto).update(
                            "quantidadeFavorito", quantidadeFavorito
                    );

                    AtualizaQuantidadeFavorito();

                }
                else{

                    new AlertDialog.Builder( ProdutoActivity.this)
                        .setTitle("Remover dos favoritos")
                        .setMessage("Tem certeza que deseja remover esse item dos favoritos?")
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseFirestore.getInstance().collection("/Usuarios").
                                        document(uid).collection( "/Favoritos" ).document(uidProduto).delete();

                                quantidadeFavorito -= 1;

                                mButtonFavorito.setBackgroundResource( R.drawable.coracao_contorno );

                                statusFavorito = "";

                                alert( "Removido dos favoritos com sucesso." );

                                //Campo responsavel por add dados soltos no documento**********************************************
                                FirebaseFirestore.getInstance().collection(categoria).document(uidProduto).update(
                                        "quantidadeFavorito", quantidadeFavorito
                                );

                                AtualizaQuantidadeFavorito();

                            }
                        }).setNegativeButton("não", null) .show();

                }
            }

        } );
    }

    private void fetchCategoria() {

        uid = FirebaseAuth.getInstance().getUid();
        Log.d("XXXX", uid);


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference studentsCollectionReference = rootRef.collection(categoria) ;
        studentsCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Produto> ListRelacionados = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Produto produto = document.toObject( Produto.class);
                        ListRelacionados.add(produto);
  //                      Log.d("testes","entrou no For");
                    }

                    int TamanhoLista = ListRelacionados.size();
                    int j = 0;

                    String Contador;
               //     Log.d("testes", "tamanho " + TamanhoLista);

                    List<Produto> randomRelacionadosLista = new ArrayList<>();
                    for(int i = 0; i < TamanhoLista; i++) {

//                        Log.d("testes","entrou no For 2");
                        Produto randomRelacionados = ListRelacionados.get(new Random().nextInt(TamanhoLista));

                        String produtoAtualLista = randomRelacionados.getNomeproduto();

                        Log.d("testes", "o nome do produto atual " + nomeProduto + " produto da lista " + randomRelacionados.getNomeproduto());
                        Log.d("teste", "produto selecionado " + produtoSelecionado);
                        if(!nomeProduto.equals( produtoAtualLista)){

                            if(!produtoSelecionado.equals( produtoAtualLista )){
                                if(!randomRelacionadosLista.contains(randomRelacionados)) {

                                    j++;

                                   // Log.d("testes","entrou no IF");
                                    randomRelacionadosLista.add(randomRelacionados);

    //                                Log.d("testes", "Lista " + randomRelacionados.getNomeproduto());

                                    categoriaRecomendado = randomRelacionados.getCategoriaProduto();

                                    if(j == 1){

                                        categoriaRecomendado = randomRelacionados.getCategoriaProduto();
                                        mConstraintRelacionado1.setVisibility(View.VISIBLE );
                                        uidRelacionado1 = randomRelacionados.getUuidProduto();
                                        mTextNomeRelacionado1.setText(randomRelacionados.getNomeproduto());

                                        Log.d( "testes", "primeiro produto relacionado " + randomRelacionados.getNomeproduto() );

                                        Picasso.get()
                                                .load( randomRelacionados.getUrlproduto() )
                                                .into( mImgItemRelacionado1 );

      //                                  Log.d("testes", "Entrou no 1");

                                    }else{
                                        if(j == 2){

                                            categoriaRecomendado = randomRelacionados.getCategoriaProduto();
                                            mConstraintRelacionado2.setVisibility(View.VISIBLE );
                                            uidRelacionado2 = randomRelacionados.getUuidProduto();
                                            mTextNomeRelacionado2.setText(randomRelacionados.getNomeproduto());

                                            Log.d( "testes", "Segundo produto relacionado " + randomRelacionados.getNomeproduto() );

                                            Picasso.get()
                                                    .load( randomRelacionados.getUrlproduto() )
                                                    .into( mImgItemRelacionado2 );
        //                                    Log.d("testes", "Entrou no 2");
                                        }else{
                                            if(j == 3){

                                                categoriaRecomendado = randomRelacionados.getCategoriaProduto();
                                                mConstraintRelacionado3.setVisibility(View.VISIBLE );
                                                uidRelacionado3 = randomRelacionados.getUuidProduto();
                                                mTextNomeRelacionado3.setText(randomRelacionados.getNomeproduto());

                                                Picasso.get()
                                                        .load( randomRelacionados.getUrlproduto() )
                                                        .into( mImgItemRelacionado3 );
          //                                      Log.d("testes", "Entrou no 3");
                                                mConstraintRelacionado4.setVisibility( View.INVISIBLE );
                                            }else{
                                                if(j == 4){

                                                    categoriaRecomendado = randomRelacionados.getCategoriaProduto();
                                                    mConstraintRelacionado4.setVisibility(View.VISIBLE );
                                                    uidRelacionado4 = randomRelacionados.getUuidProduto();
                                                    mTextNomeRelacionado4.setText(randomRelacionados.getNomeproduto());

                                                    Picasso.get()
                                                            .load( randomRelacionados.getUrlproduto() )
                                                            .into( mImgItemRelacionado4 );
            //                                        Log.d("testes", "Entrou no 4");
                                                }
                                            }

                                        }
                                    }

                                    if(TamanhoLista == 2){
                                        if(randomRelacionadosLista.size() == 1 ) {
                                            break;
                                        }
                                    }else {
                                        if (TamanhoLista == 3) {
                                            if (randomRelacionadosLista.size() == 2) {
                                                break;
                                            }
                                        } else {
                                            if (TamanhoLista == 4) {
                                                if (randomRelacionadosLista.size() == 3) {
                                                    break;
                                                }
                                            } else {
                                                if (TamanhoLista >= 4) {
                                                    if (randomRelacionadosLista.size() == 4) {
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                            }else {

                                Log.d("testes", "entou no se não, valor do I " + i );
                                if (TamanhoLista == 1) {

                                    mConstraintRelacionado1.setVisibility( View.GONE );
                                    mConstraintRelacionado2.setVisibility( View.GONE );
                                    mConstraintRelacionado3.setVisibility( View.GONE );
                                    mConstraintRelacionado4.setVisibility( View.GONE );

                                    break;
                                }
                                i--;

                                Log.d("testes", "valor do i " + i);
                            }

                        }
                    }
                } else {
                    Log.d("Teste", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void RecebeDados(){

        mConstraintRelacionado1.setVisibility(View.GONE );
        mConstraintRelacionado2.setVisibility(View.INVISIBLE );
        mConstraintRelacionado3.setVisibility(View.GONE );
        mConstraintRelacionado4.setVisibility(View.GONE );

        uid = FirebaseAuth.getInstance().getUid();

        if(evento.equals( "favorito" )||evento.equals( "destaque" )||evento.equals( "recomendado" )) {

            DocumentReference docRef = db.collection( categoria ).document( uidFavorito );
            docRef.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                          //  Log.d( "testes", "DocumentSnapshot data: " + document.getData() );
                            uidProduto = document.getString( "uuidProduto" );
                            valorProduto = document.getString( "valorProduto" );
                            valorDesconto = document.getString( "valorDesconto" );
                            estadoAtual = document.getString( "estadoAtual" );
                        //    quantidadeFavorito = document.getString("quantidadeFavorito") );
                            Log.d( "testes", "quantidade de favoritos " + quantidadeFavorito );
                            //Integer.parseInt( document.getString( "quantidadeFavorito" ) );
                            nomeProduto = document.getString( "nomeProduto" );
                            urlProduto = document.getString( "urlProduto" );
                            descricaoProduto = document.getString( "descricaoProduto" );
                            quantidade =document.getString( "quantidadeProduto" );


                           // mTextQuantidadeFavoritos.setText( quantidadeFavString);
                            mNomeProduto.setText( nomeProduto );
                            mDescricaoProduto.setText( descricaoProduto );
                            mValorProduto.setText( valorProduto );
                            mValorDesconto.setText( valorDesconto );
                            mTextQuantidade.setText( quantidade );

                            Picasso.get()
                                    .load( urlProduto )
                                    .into( mImgProduto );

                        //    Log.d("testes", "Estado atual: " + estadoAtual);
                        //    Log.d("testes", "UidProduto atual: " + uidProduto);
                        //    Log.d("testes", "Uid Usuario: " + uid);

                            FinalizaCarregamento();

                        } else {
                            Log.d( "testes", "No such document" );
                        }
                    } else {
                        Log.d( "testes", "get failed with ", task.getException() );
                    }
                }
            } );


        }else {
            uidProduto = produto.getUuidProduto();
            categoria = produto.getCategoriaProduto();

            valorDesconto = produto.getValorDesconto();
            estadoAtual = produto.getEstadoAtual();

            quantidadeFavorito = produto.getQuantidadeFavorito();

            nomeProduto = produto.getNomeproduto();
            urlProduto = produto.getUrlproduto();

            uidProduto = produto.getUuidProduto();
            categoria = produto.getCategoriaProduto();

            mNomeProduto.setText( produto.getNomeproduto() );
            mDescricaoProduto.setText( produto.getDescricaoproduto() );
            mValorProduto.setText( produto.getValorproduto() );
            mValorDesconto.setText( produto.getValorDesconto() );
            mTextQuantidade.setText( produto.getQuantidadeproduto() );

            Picasso.get()
                    .load( produto.getUrlproduto() )
                    .into( mImgProduto );
            FinalizaCarregamento();
        }

//        Log.d("testes", "Estado atual: " + estadoAtual);
//        Log.d("testes", "UidProduto atual: " + uidProduto);
//        Log.d("testes", "Uid Usuario: " + uid);


/////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void FinalizaCarregamento(){
        DocumentReference documentRef = db.collection( "Usuarios" ).document( uid )
                .collection( "Favoritos" ).document(uidProduto);
        documentRef.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mButtonFavorito.setBackgroundResource( R.drawable.coracao_preenchido );

                        statusFavorito = "Add";

                    } else {
                        mButtonFavorito.setBackgroundResource( R.drawable.coracao_contorno );

                        statusFavorito = "";
                    }
                } else {
                    Log.d( "teste", "get failed with ", task.getException() );
                }
            }
        } );


////////////////////////////////////////////////////////////////////////////////////////////////////
        if (estadoAtual.equals( "Disponivel" )) {
            mEstadoAtual.setVisibility( View.GONE );
            mTextQuantidade.setVisibility( View.VISIBLE );
            Log.d( "Teste", "Entrou no If disponivel" );
        } else {
            mEstadoAtual.setVisibility( View.VISIBLE );
            mTextQuantidade.setVisibility( View.GONE );

            Log.d( "Teste", "Entrou no If indisponivel" );
        }



        if (valorDesconto == null || valorDesconto.isEmpty()) {
            mValorDesconto.setVisibility( View.GONE );
            mImgTraco.setVisibility( View.GONE );
        } else {
            mValorDesconto.setVisibility( View.VISIBLE );
            mValorProduto.setTextSize( TypedValue.COMPLEX_UNIT_DIP, 15.f );
            mImgTraco.setVisibility( View.VISIBLE );

        }

        AtualizaQuantidadeFavorito();

        mConstraintSecundaria.setVisibility( View.GONE );
        mConstraintPrincipal.setVisibility( View.VISIBLE );
        ///////////////////////////////////////////////////



        mScrool.scrollTo( 0,0 );
    }

    private void CarregaDados() {

        uidProduto = produto.getUuidProduto();
        categoria = produto.getCategoriaProduto();

        valorDesconto = produto.getValorDesconto();
        estadoAtual = produto.getEstadoAtual();

        quantidadeFavorito = produto.getQuantidadeFavorito();

        nomeProduto = produto.getNomeproduto();
        urlProduto = produto.getUrlproduto();
    }

    private void Carrega() {

        Log.d( "teste", "entou em carrega destaque" );

        //Muda o estado de visibilidade das Constraint

        if(evento.equals( "favorito" )||evento.equals( "destaque" )||evento.equals( "recomendado" )) {

            Log.d( "testes", "XXXXXXX em destaque Nome categoria: " + categoria );
            Log.d( "testes", "YYYYYYY em destaque Uid produto Favorito::: " + uidFavorito );




/*
            DocumentReference docRef = db.collection(categoria).document(uidFavorito);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("testes", "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d("testes", "No such document");
                        }
                    } else {
                        Log.d("testes", "get failed with ", task.getException());
                    }
                }
            });




 */
        }else {
            uidProduto = produto.getUuidProduto();
            categoria = produto.getCategoriaProduto();

            valorDesconto = produto.getValorDesconto();
            estadoAtual = produto.getEstadoAtual();

            quantidadeFavorito = produto.getQuantidadeFavorito();

            nomeProduto = produto.getNomeproduto();
            urlProduto = produto.getUrlproduto();

            uidProduto = produto.getUuidProduto();
            categoria = produto.getCategoriaProduto();

            mNomeProduto.setText( produto.getNomeproduto() );
            mDescricaoProduto.setText( produto.getDescricaoproduto() );
            mValorProduto.setText( produto.getValorproduto() );
            mValorDesconto.setText( produto.getValorDesconto() );
            mTextQuantidade.setText( produto.getQuantidadeproduto() );

            Picasso.get()
                    .load( produto.getUrlproduto() )
                    .into( mImgProduto );

        }

        DocumentReference docRef = db.collection( "Usuarios" ).document( uid )
                .collection( "Favoritos" ).document( uidProduto );
        docRef.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mButtonFavorito.setBackgroundResource( R.drawable.coracao_preenchido );

                        statusFavorito = "Add";

                    } else {
                        mButtonFavorito.setBackgroundResource( R.drawable.coracao_contorno );

                        statusFavorito = "";
                    }
                } else {
                    Log.d( "teste", "get failed with ", task.getException() );
                }
            }
        } );

        ////////////////////////////////////////////////////
        if (estadoAtual.equals( "Disponivel" )) {
            mEstadoAtual.setVisibility( View.GONE );
            mTextQuantidade.setVisibility( View.VISIBLE );
            Log.d( "Teste", "Entrou no If disponivel" );
        } else {
            mEstadoAtual.setVisibility( View.VISIBLE );
            mTextQuantidade.setVisibility( View.GONE );

            Log.d( "Teste", "Entrou no If indisponivel" );
        }

        if (valorDesconto == null || valorDesconto.isEmpty()) {
            mValorDesconto.setVisibility( View.GONE );
            mImgTraco.setVisibility( View.GONE );
        } else {
            mValorDesconto.setVisibility( View.VISIBLE );
            mValorProduto.setTextSize( TypedValue.COMPLEX_UNIT_DIP, 15.f );
            mImgTraco.setVisibility( View.VISIBLE );

        }

        mConstraintSecundaria.setVisibility( View.GONE );
        mConstraintPrincipal.setVisibility( View.VISIBLE );
    ///////////////////////////////////////////////////
    }

    private void AtualizaQuantidadeFavorito() {
        if(quantidadeFavorito <= 0){
            mTextQuantidadeFavoritos.setVisibility( View.INVISIBLE );
            Log.d("testes", "esntou no if quantidade favorito");
        }else{
            Log.d("testes", "esntou no else quantidade favorito");
            mTextQuantidadeFavoritos.setText(String.valueOf(quantidadeFavorito));
            mTextQuantidadeFavoritos.setVisibility( View.VISIBLE );
        }
    }

    private void IniciaComponentes() {

        Log.d("teste", "inicia componentes");
        mButtonFavorito = findViewById( R.id.btnFavorito );

        mNomeProduto = findViewById( R.id.textNomeProdutoFavorito );
        mValorProduto = findViewById( R.id.textValorProduto );
        mValorDesconto = findViewById( R.id.textValorDesconto );
        mEstadoAtual = findViewById( R.id.textIndisponivel );
        mDescricaoProduto = findViewById( R.id.textDescricaoProduto );
        mTextQuantidade = findViewById( R.id.textQuantidade );
        mTextQuantidadeFavoritos = findViewById( R.id.textQuandidadeFavoritos );
        mTextNomeRelacionado1 =  findViewById( R.id.textNomeRelacionado1 );
        mTextNomeRelacionado2 =  findViewById( R.id.textNomeRelacionado2 );
        mTextNomeRelacionado3 =  findViewById( R.id.textNomeRelacionado3 );
        mTextNomeRelacionado4 =  findViewById( R.id.textNomeRelacionado4 );

        mImgTraco = findViewById( R.id.img_traco);
        mImgProduto = findViewById( R.id.imageProduto );
        mImgItemRelacionado1 = findViewById( R.id.imgItemRelacionado1 );
        mImgItemRelacionado2 = findViewById( R.id.imgItemRelacionado2 );
        mImgItemRelacionado3 = findViewById( R.id.imgItemRelacionado3 );
        mImgItemRelacionado4 = findViewById( R.id.imgItemRelacionado4 );

        mConstraintSecundaria = findViewById( R.id.constraint_secundaria );
        mConstraintPrincipal = findViewById( R.id.constraint_principal );

        mBotaoPedido = findViewById( R.id.btnPedido );

        mConstraintRelacionado1 = findViewById( R.id.constraintRelacionado1 );
        mConstraintRelacionado2 = findViewById( R.id.constraintRelacionado2 );
        mConstraintRelacionado3 = findViewById( R.id.constraintRelacionado3 );
        mConstraintRelacionado4 = findViewById( R.id.constraintRelacionado4 );
       // mRecyclerRelacionado = findViewById(R.id.recyclerRelacionado );

        mScrool = findViewById( R.id.scrollActivityProduto );
    }

    private void alert(String msg) {
        Toast.makeText( ProdutoActivity.this, msg, Toast.LENGTH_SHORT ).show();
    }

}
