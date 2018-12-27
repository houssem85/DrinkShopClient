package com.programasoft.drinkshop.Database.Local;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.programasoft.drinkshop.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;


/**
 * Created by ASUS on 25/12/2018.
 */
@Dao
public interface FavoriteDao {
    @Query("SELECT * from Favorite")
    Flowable<List<Favorite>> getFavorites();

    @Query("SELECT * FROM Favorite where id=:ItemId")
    Flowable<Favorite> getFavoriteByID(int ItemId);

    @Query("SELECT EXISTS(SELECT * FROM Favorite where id=:ItemId)")
    int IsFavorite(int ItemId);

    @Query("SELECT COUNT(*) FROM Favorite")
    int CountFavorits();

    @Delete
    void DeleteFavorite(Favorite...favorites);

    @Update
    void UpdateFavorite(Favorite...favorites);

    @Insert
    void InsertFavorites(Favorite...favorites);

}
