package com.programasoft.drinkshop.Database.Local;

import com.programasoft.drinkshop.Database.DataSource.IFavoriteDataSource;
import com.programasoft.drinkshop.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ASUS on 25/12/2018.
 */

public class FavoriteDataSource implements IFavoriteDataSource {

    private static  FavoriteDataSource instance;
    private FavoriteDao dao;

    public FavoriteDataSource(FavoriteDao dao) {
        this.dao = dao;
    }

    public static FavoriteDataSource getInstance(FavoriteDao dao)
    {
        if(instance==null)
        {
            instance=new FavoriteDataSource(dao);
        }
        return instance;
    }

    @Override
    public int CountFavorits() {
        return  dao.CountFavorits();
    }

    @Override
    public Flowable<List<Favorite>> getFavorites() {
        return dao.getFavorites();
    }

    @Override
    public Flowable<Favorite> getFavoriteByID(int ItemId) {
        return dao.getFavoriteByID(ItemId);
    }

    @Override
    public int IsFavorite(int ItemId) {
        return dao.IsFavorite(ItemId);
    }

    @Override
    public void DeleteFavorite(Favorite... favorites) {
    dao.DeleteFavorite(favorites);
    }

    @Override
    public void UpdateFavorite(Favorite... favorites) {
    dao.UpdateFavorite(favorites);
    }

    @Override
    public void InsertFavorites(Favorite... favorites) {
    dao.InsertFavorites(favorites);
    }

}
