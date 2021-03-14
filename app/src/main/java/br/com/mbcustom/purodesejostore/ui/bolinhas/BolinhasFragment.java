package br.com.mbcustom.purodesejostore.ui.bolinhas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
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

import br.com.mbcustom.purodesejostore.Produto;
import br.com.mbcustom.purodesejostore.ProdutoActivity;
import br.com.mbcustom.purodesejostore.R;

public class BolinhasFragment extends Fragment {

    private BolinhasViewModel myMenuViewModel;

    private GroupAdapter adapter;

    private String uid;

    private Produto categoria;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myMenuViewModel =
                ViewModelProviders.of( this ).get( BolinhasViewModel.class );
        View root = inflater.inflate( R.layout.fragment_bolinhas, container, false );

        RecyclerView rvc = root.findViewById( R.id.recyclerItemBolinhas );

        adapter = new GroupAdapter();
        rvc.setAdapter(adapter);
        rvc.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                Intent intent = new Intent(getActivity(), ProdutoActivity.class);

                ProdutoItem produtoItem = (ProdutoItem) item;

                intent.putExtra("dadosCategoria", produtoItem.produto);
                intent.putExtra( "evento", "categoria" );

                startActivity(intent);
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
        FirebaseFirestore.getInstance().collection("/Bolinhas")
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
                            Produto produto = doc.toObject( Produto.class);
                            uid = FirebaseAuth.getInstance().getUid();

                            adapter.add(new ProdutoItem(produto));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private class ProdutoItem extends Item<ViewHolder> {

        private final Produto produto;

        private ProdutoItem(Produto produto) {
            this.produto = produto;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Log.d("Testes", position + "");

            TextView txtNome = viewHolder.itemView.findViewById( R.id.textNomeProduto );
            TextView txtValor = viewHolder.itemView.findViewById( R.id.textValorItem );
            ImageView imgProduto = viewHolder.itemView.findViewById( R.id.imgItem );

            txtNome.setText( produto.getNomeproduto() );
            txtValor.setText( produto.getValorproduto() );

            Picasso.get()
                    .load(produto.getUrlproduto())
                    .into(imgProduto);

        }

        @Override
        public int getLayout() {
            return R.layout.item_produto;
        }

    }
}