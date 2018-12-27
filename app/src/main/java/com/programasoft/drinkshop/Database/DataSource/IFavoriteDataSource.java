package com.programasoft.drinkshop.Database.DataSource;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.programasoft.drinkshop.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.DELETE;

/**
 * Created by ASUS on 25/12/2018.
 */

public interface IFavoriteDataSource {
    int CountFavorits();
    Flowable<List<Favorite>> getFavorites();
    Flowable<Favorite> getFavoriteByID(int ItemId);
    int IsFavorite(int ItemId);
    void DeleteFavorite(Favorite...favorites);
    void UpdateFavorite(Favorite...favorites);
    void InsertFavorites(Favorite...favorites);

}
