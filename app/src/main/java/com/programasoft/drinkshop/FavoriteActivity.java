package com.programasoft.drinkshop;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import com.programasoft.drinkshop.Adapter.CartAdapter;
import com.programasoft.drinkshop.Adapter.CartViewHolder;
import com.programasoft.drinkshop.Adapter.FavoriteAdapter;
import com.programasoft.drinkshop.Database.DataSource.FavoriteRepository;
import com.programasoft.drinkshop.Database.Local.Database;
import com.programasoft.drinkshop.Database.Local.FavoriteDataSource;
import com.programasoft.drinkshop.Database.ModelDB.Favorite;
import com.programasoft.drinkshop.Database.ModelDB.cart;
import com.programasoft.drinkshop.Utils.RecycleItemTouchHelper;
import com.programasoft.drinkshop.Utils.RecycleItemTouchHelperListner;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteActivity extends AppCompatActivity {
private RecyclerView list_favorites;
CompositeDisposable compositeDisposable;
private List<Favorite> favoriteList;
private FavoriteAdapter favoriteAdapter;
private LinearLayout rootLyout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rootLyout=(LinearLayout)this.findViewById(R.id.favorite_activity);
        list_favorites=(RecyclerView)this.findViewById(R.id.list_favorites);
        list_favorites.setLayoutManager(new LinearLayoutManager(this));
        Database d=Database.getInstance(this);
        final FavoriteRepository  repository=FavoriteRepository.getInstance(FavoriteDataSource.getInstance(d.FavoriteDao()));
        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(repository.getFavorites().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Favorite>>() {
            @Override
            public void accept(List<Favorite> favorites) throws Exception {
                favoriteList=favorites;
                favoriteAdapter=new FavoriteAdapter(FavoriteActivity.this,favorites);
                list_favorites.setAdapter(favoriteAdapter);
                list_favorites.setHasFixedSize(true);
            }
        }));
        //ITEM hELPER
        ItemTouchHelper.SimpleCallback simpleCallback=new RecycleItemTouchHelper(0, ItemTouchHelper.LEFT, new RecycleItemTouchHelperListner() {
            @Override
            public void OnSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                if(viewHolder instanceof CartViewHolder)
                {
                 final Favorite deletedItem=favoriteList.get(viewHolder.getAdapterPosition());
                 int DeletedIndex=viewHolder.getAdapterPosition();

                 //Delete Item FROM adapter
                    //favoriteList.remove(DeletedIndex);
                   // favoriteAdapter.notifyItemRemoved(DeletedIndex);
                 //Delete Item From Room DATABASE
                    repository.DeleteFavorite(deletedItem);
                    Snackbar snackbar=Snackbar.make(rootLyout,new StringBuilder().append(deletedItem.getName()+" Romoved From Favorites List").toString(),Snackbar.LENGTH_LONG);
                    snackbar.setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //favoriteAdapter.notifyDataSetChanged();
                            repository.InsertFavorites(deletedItem);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                }

            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(list_favorites);





    }



    @Override
    protected void onResume() {
        super.onResume();
        Database d=Database.getInstance(this);
        FavoriteRepository  repository=FavoriteRepository.getInstance(FavoriteDataSource.getInstance(d.FavoriteDao()));


        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(repository.getFavorites().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Favorite>>() {
            @Override
            public void accept(List<Favorite> favorites) throws Exception {
                FavoriteAdapter favoriteAdapter=new FavoriteAdapter(FavoriteActivity.this,favorites);
                list_favorites.setAdapter(favoriteAdapter);
                list_favorites.setHasFixedSize(true);
            }
        }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
