package com.programasoft.drinkshop.Database.DataSource;

import android.arch.lifecycle.LiveData;

import com.programasoft.drinkshop.Database.Local.CartDataSource;
import com.programasoft.drinkshop.Database.ModelDB.cart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ASUS on 22/12/2018.
 */

public class CartRepository  implements IcartDataSource{

    private IcartDataSource IcartDataSource;

    private static CartRepository instance;

    public CartRepository(IcartDataSource cartDataSource) {
        this.IcartDataSource = cartDataSource;
    }

    public static CartRepository getInstance(IcartDataSource IcartDataSource)
    {if(instance==null)
      {instance=new CartRepository(IcartDataSource);
      }
     return instance;
    }

    @Override
    public Flowable<List<cart>> getCarts() {
        return IcartDataSource.getCarts();
    }

    @Override
    public Flowable<cart> getCartById(int ItemId) {
        return IcartDataSource.getCartById(ItemId);
    }

    @Override
    public int getCartNumbers() {
        return IcartDataSource.getCartNumbers();
    }

    @Override
    public void emptyCart() {
        IcartDataSource.emptyCart();
    }

    @Override
    public void InsertToCart(cart... carts) {
        IcartDataSource.InsertToCart(carts);
    }

    @Override
    public void UpdateCart(cart... carts) {
        IcartDataSource.UpdateCart(carts);
    }

    @Override
    public void DeleteCart(cart... carts) {
        IcartDataSource.DeleteCart(carts);
    }
}
