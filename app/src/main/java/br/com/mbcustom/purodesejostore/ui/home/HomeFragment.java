package br.com.mbcustom.purodesejostore.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import br.com.mbcustom.purodesejostore.Destaque;
import br.com.mbcustom.purodesejostore.ProdutoActivity;
import br.com.mbcustom.purodesejostore.R;

public class HomeFragment extends Fragment {

    private HomeViewModel myMenuViewModel;

    private GroupAdapter adapter;

    private String uid;

    private  ProgressBar mProgresseHome;

    private String uidProduto;

    private ImageView mImageInstagram, mImageWhatsapp, mImageFone;
    private ImageView mImageInstagramColorido, mImageWhatsappColorido, mImageFoneColorido;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String urlCapaHome;

    private TextView tvStatus;
    private Button btbPremiun;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myMenuViewModel =
                ViewModelProviders.of( this ).get( HomeViewModel.class );
        View root = inflater.inflate( R.layout.fragment_home, container, false );

        mProgresseHome = root.findViewById( R.id.progressHome );
        mImageInstagram = root.findViewById( R.id.img_instagram );
        mImageWhatsapp = root.findViewById( R.id.img_whatsapp );
        mImageFone = root.findViewById( R.id.img_fone );

        mImageInstagramColorido = root.findViewById( R.id.img_instagram_colorido );
        mImageWhatsappColorido = root.findViewById( R.id.img_whatsapp_colorido );
        mImageFoneColorido = root.findViewById( R.id.img_fone_colorido );

        mImageInstagram.setVisibility( View.VISIBLE );
        mImageWhatsapp.setVisibility( View.VISIBLE );
        mImageFone.setVisibility( View.VISIBLE );

        mImageInstagramColorido.setVisibility( View.GONE );
        mImageWhatsappColorido.setVisibility( View.GONE );
        mImageFoneColorido.setVisibility( View.GONE );

        mProgresseHome.setVisibility( View.VISIBLE );

        RecyclerView rvc = root.findViewById( R.id.recyclerItemHome );

        final ImageView mImageCapa = root.findViewById( R.id.imgCapaHome );

        adapter = new GroupAdapter();
        rvc.setAdapter(adapter);
        rvc.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {


                Intent intent = new Intent(getActivity(), ProdutoActivity.class);

                ProdutoDestaque produtoDestaque = (ProdutoDestaque) item;

                intent.putExtra("dadosNaoCategoria", produtoDestaque.destaque);
                intent.putExtra( "evento", "destaque" );

                    startActivity(intent);
            }
        });



        mImageFone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageFone.setVisibility( View.GONE );
                mImageFoneColorido.setVisibility( View.VISIBLE );

                Uri number = Uri.parse("tel:065984590193");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

                startActivity(callIntent);
            }
        } );

        mImageInstagram.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageInstagram.setVisibility( View.GONE );
                mImageInstagramColorido.setVisibility( View.VISIBLE );

                Uri uri = Uri.parse("https://www.instagram.com/purodesejo_tga/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/purodesejo_tga/")));
                }
            }
        } );

        mImageWhatsapp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageWhatsapp.setVisibility( View.GONE );
                mImageWhatsappColorido.setVisibility( View.VISIBLE );
                String texto;

                texto = ("Olá, estava no aplicativo e gostaria de saber sobre alguns produtos.");

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.putExtra("jid", "556584590193@s.whatsapp.net");
                sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain");

                try {
                    startActivity(sendIntent);
                } catch (ActivityNotFoundException e) {
                    alert( "Você não possue o Whatsapp instalado em seu dispositovo" );
                    mImageWhatsapp.setVisibility( View.VISIBLE);
                    mImageWhatsappColorido.setVisibility( View.GONE );
                }

            }
        } );

        DocumentReference docRef = db.collection( "/Destaque" ).document("MnUJRBcH8HhVa417a868");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d("teste", "entrou no task");

                    if (document.exists()) {
                        Log.d("testes", "DocumentSnapshot data: " + document.getData());

                        urlCapaHome = document.getString( "urlTitulo" );

                        Log.d("teste", "url Imagem capa " + urlCapaHome);

                        Picasso.get()
                                .load( urlCapaHome )
                                .into( mImageCapa );
                    } else {
                        Log.d("testes", "No such document");
                    }
                } else {
                    Log.d("teste", "get failed with ", task.getException());
                }
            }
        });

        fetchCategoria();

        return root;
    }


    private void alert(String msg) {
        Toast.makeText( getActivity(), msg, Toast.LENGTH_SHORT ).show();
    }

    private void fetchCategoria() {


        uid = FirebaseAuth.getInstance().getUid();
        Log.d("XXXX", uid);
        FirebaseFirestore.getInstance().collection("/Destaque")
                .document("/MnUJRBcH8HhVa417a868").collection( "/Fotos" )
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("Testes", e.getMessage(), e);
                            return;
                        }

                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        adapter.clear();
                        for (DocumentSnapshot doc: docs) {
                            Destaque destaque = doc.toObject( Destaque.class);
                            uid = FirebaseAuth.getInstance().getUid();

                            adapter.add(new ProdutoDestaque(destaque));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        mProgresseHome.setVisibility( View.GONE );
    }

    private class ProdutoDestaque extends Item<ViewHolder> {

        private final Destaque destaque;

        private ProdutoDestaque(Destaque destaque) {
            this.destaque = destaque;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Log.d("Testes", position + "");

            ImageView imgDestaque = viewHolder.itemView.findViewById( R.id.imageDestaque );

            Picasso.get()
                    .load(destaque.getUrldestaque())
                    .into(imgDestaque);

        }

        @Override
        public int getLayout() {
            return R.layout.item_destaque;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        mImageInstagram.setVisibility( View.VISIBLE );
        mImageWhatsapp.setVisibility( View.VISIBLE );
        mImageFone.setVisibility( View.VISIBLE );

        mImageInstagramColorido.setVisibility( View.GONE );
        mImageWhatsappColorido.setVisibility( View.GONE );
        mImageFoneColorido.setVisibility( View.GONE );

    }
}
