package com.programasoft.drinkshop.Database.Local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.programasoft.drinkshop.Database.ModelDB.cart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ASUS on 22/12/2018.
 */

@Dao
public interface CartDao {

    @Query("SELECT * FROM cart ")
    Flowable<List<cart>> getCarts();

    @Query("SELECT * FROM cart where id=:ItemId")
    Flowable<cart> getCartById(int ItemId);

    @Query("SELECT COUNT(*) FROM cart")
    int getCartNumbers();

    @Query("DELETE FROM cart")
    void emptyCart();

    @Insert
    void InsertToCart(cart...carts);

    @Update
    void UpdateCart(cart...carts);

    @Delete
    void DeleteCart(cart...carts);



}
