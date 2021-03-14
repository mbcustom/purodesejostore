package br.com.mbcustom.purodesejostore;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView mImagemUsuario;

    private TextView mTextNomeUsuario, mTextEmailUsuario;

    private ImageView mImagem1, mImagem2, mImagem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );




        //FloatingActionButton fab = findViewById( R.id.fab );
        //fab.setOnClickListener( new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
        //                .setAction( "Action", null ).show();
        //    }
        //} );
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorito, R.id.nav_excitantes, R.id.nav_lubrificantes
                , R.id.nav_beijaveis, R.id.nav_massagem, R.id.nav_acessorios, R.id.nav_fantasias
                , R.id.nav_kits, R.id.nav_logout, R.id.nav_excitantes, R.id.nav_bolinhas
                , R.id.nav_sabonetes)
                .setDrawerLayout( drawer )
                .build();
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration );
        NavigationUI.setupWithNavController( navigationView, navController );

//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ExcitantesFragment()).commit();

        //**********************************************************
        //CARREGAR INFORMAÇÕES DO USUARIO NA TELA DE NAVEGAÇÃO
        View headView = navigationView.getHeaderView( 0 );
        mImagemUsuario = headView.findViewById( R.id.imageUsuario );

        mTextEmailUsuario = headView.findViewById( R.id.textEmailUsuario );
        mTextNomeUsuario = headView.findViewById( R.id.textNomeUsuario );

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String nome = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            mTextNomeUsuario.setText( nome );
            mTextEmailUsuario.setText( email );

            Picasso.get()
                    .load( photoUrl )
                    .into( mImagemUsuario );

        }

//        mTextNomeUsuario.get

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        return NavigationUI.navigateUp( navController, mAppBarConfiguration )
                || super.onSupportNavigateUp();
    }
}
