package com.example.projetonunocosta.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetonunocosta.R;
import com.example.projetonunocosta.adapter.BookAdapter;
import com.example.projetonunocosta.interfaceBook.AdapterCallback;
import com.example.projetonunocosta.interfaceBook.GetBookService;
import com.example.projetonunocosta.model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment implements AdapterCallback {
    private RecyclerView pessoaRecyclerview;
    private BookAdapter bookAdapter;
    private RecyclerView.LayoutManager layoutManager;
    GetBookCallback getBookCallback;

    public void connect(Activity activity){
        getBookCallback = (GetBookCallback) activity;
    }


    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance(String search) {
        BookListFragment bookListFragment = new BookListFragment();
        // Supply index input as an argument.

        Bundle args = new Bundle();
        args.putString("search", search);
        bookListFragment.setArguments(args);

        return bookListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        pessoaRecyclerview = (RecyclerView) view.findViewById(R.id.my_recycler);
        pessoaRecyclerview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        pessoaRecyclerview.setLayoutManager(layoutManager);

        Bundle args = this.getArguments();

        getResult(args.getString("search"));


        return view;
    }

    private void getResult(String id) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://www.googleapis.com/books/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        GetBookService service = retrofit.create(GetBookService.class);

        Call<Result> resultCall = service.getResults(id);

        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.v("RETROFIT","OK");

                if(response.isSuccessful()){
                    bookAdapter = new BookAdapter(response.body().getItems(),getActivity(),BookListFragment.this);
                    pessoaRecyclerview.setAdapter(bookAdapter);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.v("RETROFIT","NOK");
            }
        });
    }

    @Override
    public void selectItemOnclick( String id) { getBookCallback.getBook(id);

    }

    public interface GetBookCallback{
        void getBook(String id);
    }


}