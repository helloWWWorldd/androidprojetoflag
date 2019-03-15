package com.example.projetonunocosta.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetonunocosta.R;
import com.example.projetonunocosta.adapter.BookAdapter;
import com.example.projetonunocosta.interfaceBook.AdapterCallback;
import com.example.projetonunocosta.model.ImageLinks;
import com.example.projetonunocosta.model.Item;
import com.example.projetonunocosta.model.VolumeInfo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesListFragment extends Fragment implements AdapterCallback {

    RecyclerView list;
    private BookAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    public FavoritesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites_list, container, false);

        list = view.findViewById(R.id.recycler_favorites);
        list.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        obterDadosSalvos();

        return view;
    }

    private void obterDadosSalvos() {

        Realm realm =  Realm.getDefaultInstance();
        RealmQuery<Item> query = realm.where(Item.class);

        RealmResults<Item> items = query.findAll();

        List<Item> itemsList = new ArrayList<>();
        if (items.size() != 0){

          for (int i = 0 ; i < items.size(); i++){
          Item _item = new Item();
             if (items.get(i).getVolumeInfo().getTitle() != null){
                 // Log.v("USER",users.get(i).getNome());
                _item.setId(items.get(i).getId());
                VolumeInfo volumeInfo = new VolumeInfo();
                ImageLinks imageLinks = new ImageLinks();
                volumeInfo.setTitle(items.get(i).getVolumeInfo().getTitle());
                volumeInfo.setPublishedDate(items.get(i).getVolumeInfo().getPublishedDate());
                imageLinks.setSmallThumbnail(items.get(i).getVolumeInfo().getImageLinks().getSmallThumbnail());
                _item.setVolumeInfo(volumeInfo);
                _item.getVolumeInfo().setImageLinks(imageLinks);
                 itemsList.add(_item);
               }
            }
        }


        adapter = new BookAdapter(itemsList, getActivity(),FavoritesListFragment.this);
            list.setAdapter(adapter);
        }


    @Override
    public void selectItemOnclick(String id) {

    }

//    public RecyclerView getList() {
//        return list;
//    }

}
