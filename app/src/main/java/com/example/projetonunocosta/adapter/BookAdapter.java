package com.example.projetonunocosta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetonunocosta.R;
import com.example.projetonunocosta.interfaceBook.AdapterCallback;
import com.example.projetonunocosta.model.Item;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookAdapterViewHolder> {
    private List<Item> items;
    private Context context;
    private AdapterCallback adapterCallback;


    public BookAdapter(List<Item> items, Context context, AdapterCallback adapterCallback) {
        this.items = items;
        this.context = context;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public BookAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,viewGroup,false);
        BookAdapterViewHolder bookAdapterViewHolder = new BookAdapterViewHolder(view);
        return bookAdapterViewHolder;
    }// Resposável por gerar o item da lista, conexao com o ViewHolder

    @Override
    public void onBindViewHolder(@NonNull final BookAdapterViewHolder bookAdapterViewHolder, final int i) {
        //Responsável por popular o item com os dados

        final Item item = items.get(i);

            bookAdapterViewHolder.tvTitle.setText(item.getVolumeInfo().getTitle());
            bookAdapterViewHolder.tvPublishedDate.setText(item.getVolumeInfo().getPublishedDate());

        String url = item.getVolumeInfo().getImageLinks().getSmallThumbnail();

        Picasso.get().load(url).into(bookAdapterViewHolder.ivItem, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                bookAdapterViewHolder.ivItem.setImageResource(R.drawable.ic_launcher_background);
            }
        });

            bookAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.selectItemOnclick(item.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        // Quantidade de itens da lista
        return items.size();
    }


    public static class BookAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvPublishedDate;
        private ImageView ivItem;


        public BookAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle          = itemView.findViewById(R.id.tv_title);
            tvPublishedDate  = itemView.findViewById(R.id.tv_published_date);
            ivItem           = itemView.findViewById(R.id.iv_item);

        }
    }

}