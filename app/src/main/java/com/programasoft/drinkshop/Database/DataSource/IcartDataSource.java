package com.programasoft.drinkshop.Database.DataSource;

import android.arch.lifecycle.LiveData;

import com.programasoft.drinkshop.Database.ModelDB.cart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ASUS on 22/12/2018.
 */

public interface IcartDataSource {
    Flowable<List<cart>> getCarts();
    Flowable<cart> getCartById(int ItemId);
    int getCartNumbers();
    void emptyCart();
    void InsertToCart(cart...carts);
    void UpdateCart(cart...carts);
    void DeleteCart(cart...carts);
}
