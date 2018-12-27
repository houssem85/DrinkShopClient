package com.programasoft.drinkshop.Database.DataSource;

import com.programasoft.drinkshop.Database.Local.FavoriteDataSource;
import com.programasoft.drinkshop.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ASUS on 25/12/2018.
 */

public class FavoriteRepository implements IFavoriteDataSource {

    private IFavoriteDataSource favoriteDataSource;
    private static FavoriteRepository Instance;

    public FavoriteRepository(FavoriteDataSource favoriteDataSource) {
        this.favoriteDataSource = favoriteDataSource;
    }

    public static FavoriteRepository getInstance(FavoriteDataSource favoriteDataSource)
    {  if(Instance==null)
       {Instance=new FavoriteRepository(favoriteDataSource);

       }
       return Instance;

    }

    @Override
    public int CountFavorits() {
        return favoriteDataSource.CountFavorits();
    }

    @Override
    public Flowable<List<Favorite>> getFavorites() {
        return favoriteDataSource.getFavorites();
    }

    @Override
    public Flowable<Favorite> getFavoriteByID(int ItemId) {
        return favoriteDataSource.getFavoriteByID(ItemId);
    }

    @Override
    public int IsFavorite(int ItemId) {
        return favoriteDataSource.IsFavorite(ItemId);
    }

    @Override
    public void DeleteFavorite(Favorite... favorites) {
        favoriteDataSource.DeleteFavorite(favorites);
    }

    @Override
    public void UpdateFavorite(Favorite... favorites) {
        favoriteDataSource.UpdateFavorite(favorites);
    }

    @Override
    public void InsertFavorites(Favorite... favorites) {
        favoriteDataSource.InsertFavorites(favorites);
    }
}
