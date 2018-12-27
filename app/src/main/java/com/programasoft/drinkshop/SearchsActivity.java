package com.programasoft.drinkshop;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.programasoft.drinkshop.Adapter.DrinkAdapter;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.model.banner;
import com.programasoft.drinkshop.model.drink;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;
import com.programasoft.drinkshop.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchsActivity extends AppCompatActivity {
private RecyclerView recycleDrinks;
private DrinkAdapter adapter;
private List<drink> drinksList=new ArrayList<drink>();;
private MaterialSearchBar materialSearchBar;
private CompositeDisposable compositeDisposable;
private IDrinkShopApi Mservise;
private List<String> SusgesList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchs);
        materialSearchBar=(MaterialSearchBar)this.findViewById(R.id.SearchBar);
        materialSearchBar.setHint("Please Enter your Favorite Drink");
        materialSearchBar.setCardViewElevation(10);
        recycleDrinks=(RecyclerView)this.findViewById(R.id.RecylceDrinks);
        recycleDrinks.setLayoutManager(new GridLayoutManager(this,2));
        compositeDisposable=new CompositeDisposable();
        Mservise= Common.getApi();
        Mservise.GetAllDrinks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<drink>>() {
            @Override
            public void accept(List<drink> drinks) throws Exception {
                drinksList=drinks;
                adapter=new DrinkAdapter(SearchsActivity.this,drinks);
                recycleDrinks.setAdapter(adapter);
                for(drink d:drinksList)
                {SusgesList.add(d.getName());
                }

            }
        });
        materialSearchBar.setLastSuggestions(SusgesList);

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             List<String> strings=new ArrayList<>();
             for(String name:SusgesList)
             {
                 if(name.toLowerCase().contains(s.toString().toLowerCase())==true)
                 {
                     strings.add(name);
                 }
                 materialSearchBar.setLastSuggestions(strings);
             }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                {
                    recycleDrinks.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                List<drink> result=new ArrayList<drink>();
                for(drink d:drinksList)
                {  if(d.getName().toLowerCase().contains(text.toString().toLowerCase()))
                   {result.add(d);

                   }
                }
               DrinkAdapter resultAdapter=new DrinkAdapter(SearchsActivity.this,result);
                recycleDrinks.setAdapter(resultAdapter);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
