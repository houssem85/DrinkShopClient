package com.programasoft.drinkshop.Database.Local;

import android.arch.lifecycle.LiveData;

import com.programasoft.drinkshop.Database.DataSource.IcartDataSource;
import com.programasoft.drinkshop.Database.ModelDB.cart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ASUS on 22/12/2018.
 */

public class CartDataSource implements IcartDataSource {

    private static CartDataSource instance;

    private CartDao cartDao;


    public CartDataSource(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public static CartDataSource getInstance(CartDao cartDao)
    { if(instance==null)
      {instance=new CartDataSource(cartDao);
      }
        return instance;
    }

    @Override
    public Flowable<List<cart>> getCarts() {
        return this.cartDao.getCarts();
    }

    @Override
    public Flowable<cart> getCartById(int ItemId) {
        return this.cartDao.getCartById(ItemId);
    }

    @Override
    public int getCartNumbers() {
        return this.cartDao.getCartNumbers();
    }

    @Override
    public void emptyCart() {
    this.cartDao.emptyCart();
    }

    @Override
    public void InsertToCart(cart... carts) {
        this.cartDao.InsertToCart(carts);
    }

    @Override
    public void UpdateCart(cart... carts) {
        this.cartDao.UpdateCart(carts);
    }

    @Override
    public void DeleteCart(cart... carts) {
        cartDao.DeleteCart(carts);
    }
}
