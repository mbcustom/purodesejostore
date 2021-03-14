package br.com.mbcustom.purodesejostore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Splash extends AppCompatActivity {

    private String user, uid, classificacao;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.splash );

        getSupportActionBar().hide();
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        if (FirebaseAuth.getInstance().getUid() == null) {

            Intent i = new Intent( Splash.this, LoginActivity.class);

            startActivity(i);
            finish();

        }else{
            new Handler( ).post( new Runnable() {
                @Override
                public void run() {
                    user = FirebaseAuth.getInstance().getUid();

                    FirebaseFirestore.getInstance().collection( "empresa" ).document( user )
                            .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                Intent i = new Intent( Splash.this, MainActivity.class);

                                startActivity(i);
                                finish();
                            } else {
                                Log.d( "TTestes", "get failed with ", task.getException() );
                            }
                        }
                    } );

                }
            });
        }



    }
}
