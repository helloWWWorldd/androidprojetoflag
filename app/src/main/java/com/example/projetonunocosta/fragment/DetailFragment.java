package com.example.projetonunocosta.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetonunocosta.R;
import com.example.projetonunocosta.interfaceBook.GetBookService;
import com.example.projetonunocosta.model.Item;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private ImageView ivDetail;
    private TextView tvTitleDetail;
    private TextView tvSubtitleDetail;
    private TextView tvDescription;
    private Button btnAddFavorite;


    private Item item;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String id) {
        DetailFragment detailFragment = new DetailFragment();
        // Supply index input as an argument.

        Bundle args = new Bundle();
        args.putString("id", id);
        detailFragment.setArguments(args);

        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ivDetail         = (ImageView) view.findViewById(R.id.iv_detail);
        tvTitleDetail    = (TextView) view.findViewById(R.id.tv_title_detail);
        tvSubtitleDetail = (TextView) view.findViewById(R.id.tv_subtitle_detail);
        tvDescription    = (TextView) view.findViewById(R.id.tv_description);
        btnAddFavorite    = (Button) view.findViewById(R.id.btn_add_favorite);


        Bundle args = this.getArguments();

        getItemDetail(args.getString("id"));

        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar(item);
            }
        });


        return view;
    }


    public void prepareView (Item item) {
        tvTitleDetail.setText(item.getVolumeInfo().getTitle());
        tvSubtitleDetail.setText(item.getVolumeInfo().getSubtitle());
        tvDescription.setText(item.getVolumeInfo().getDescription());

        Picasso.get().load(item.getVolumeInfo().getImageLinks().getThumbnail()).into(ivDetail);

    }


    private void getItemDetail(final String id) {
        Retrofit retrofit = new Retrofit.
                Builder().
                baseUrl("https://www.googleapis.com/books/v1/").
                addConverterFactory(GsonConverterFactory.create()).build();

        GetBookService service = retrofit.create(GetBookService.class);

        Call<Item> itemDetail = service.getItemDetail(id);

        itemDetail.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.v("RETROFIT", "OK");
                item = response.body();

                prepareView(response.body());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.v("RETROFIT", "NOK");

            }
        });

    }

    private void guardar(Item item){

        if (item == null) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();
    }
}
