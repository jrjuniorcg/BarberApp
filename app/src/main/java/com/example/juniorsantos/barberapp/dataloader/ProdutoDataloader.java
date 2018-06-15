package com.example.juniorsantos.barberapp.dataloader;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.juniorsantos.barberapp.Entidades.Produtos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProdutoDataloader implements ValueEventListener {

    ProdutoDataListener listener;

    public ProdutoDataloader(ProdutoDataListener listener) {
        this.listener = listener;
    }

    /**
     * Metodo onde carrega o resultado do firebase
     * @param dataSnapshot
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> iterador = dataSnapshot.getChildren().iterator();
        List<Produtos> produtos = new ArrayList<>();
        while (iterador.hasNext()) {
            DataSnapshot data = iterador.next();
            Produtos p = data.getValue(Produtos.class);
            produtos.add(p);
        }

        listener.onProdutoLoaded(produtos);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(getClass().getCanonicalName(), "onCancelled: ", databaseError.toException());
    }

    /**
     * Listener de callback da chamada do firebase
     */
    public interface ProdutoDataListener {
        void onProdutoLoaded(List<Produtos> list);
    }






}
