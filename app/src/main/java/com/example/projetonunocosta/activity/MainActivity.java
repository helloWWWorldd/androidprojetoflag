package com.example.projetonunocosta.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetonunocosta.R;
import com.example.projetonunocosta.fragment.BookListFragment;
import com.example.projetonunocosta.fragment.DetailFragment;
import com.example.projetonunocosta.fragment.FavoritesListFragment;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements BookListFragment.GetBookCallback {

    public static final String KEY_DIC = "1";
    public static final String KEY_VALUE = "key_value";

    private FavoritesListFragment favoritesListFragment;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRemoveFavorites = findViewById(R.id.btn_remove_favorite);

        Realm.init(this);

        btnRemoveFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFavorites();
            }
        });

        RealmConfiguration mRealmConfiguration = new RealmConfiguration.Builder()
                .name("yourDBName.realm")
                .schemaVersion(1) // skip if you are not managing
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.getInstance(mRealmConfiguration);
        Realm.setDefaultConfiguration(mRealmConfiguration);

        editText = findViewById(R.id.et_pesquisa);

        SharedPreferences valorGuardado = getSharedPreferences(KEY_DIC, 0);

        String valor = valorGuardado.getString(KEY_VALUE, "");

        if (!"".equals(valor)) {

            getResult(valor);
            editText.setText(valor);
        }

    }


    public void getResult(String valueSearch) {


        Bundle args = new Bundle();
        args.putString("search", valueSearch);
        BookListFragment bookListFragment = BookListFragment.newInstance(valueSearch);

        bookListFragment.connect(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction = fragmentTransaction.replace(R.id.container, bookListFragment, "RESULT");
        fragmentTransaction.addToBackStack("list");
        fragmentTransaction.commit();


    }

    @Override
    public void getBook(String id) {


        Bundle args = new Bundle();
        args.putString("id", id);

        DetailFragment detailFragment = DetailFragment.newInstance(id);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, detailFragment, "DETAIL");
        fragmentTransaction.addToBackStack("list");
        fragmentTransaction.commit();
    }



    public void buttonClick(View view) {
        String value = editText.getText().toString();
        getResult(value);

        if (!value.equals("")) {

            saveSearch(value);
        }
    }

    private void saveSearch(String value) {

        SharedPreferences msg = getSharedPreferences(KEY_DIC, 0);
        SharedPreferences.Editor editor = msg.edit();
        editor.putString(KEY_VALUE, value);
        editor.apply();
    }

    public void goToFavorites(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        favoritesListFragment = new FavoritesListFragment();

        fragmentTransaction.replace(R.id.container, favoritesListFragment, "FAVORITES");
        fragmentTransaction.addToBackStack("favorites");
        fragmentTransaction.commit();
    }

    private void clearFavorites() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.deleteAll();
        realm.commitTransaction();

//        runOnUiThread(new Runnable() {
//            public void run() {
//                if (favoritesListFragment != null) {
//                    favoritesListFragment.getList().getAdapter().notifyDataSetChanged();
//                }
//            }
//        });
    }

}
