package com.programasoft.drinkshop.Database.Local;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.programasoft.drinkshop.Database.ModelDB.Favorite;
import com.programasoft.drinkshop.Database.ModelDB.cart;

/**
 * Created by ASUS on 22/12/2018.
 */


@android.arch.persistence.room.Database(entities = {cart.class, Favorite.class},version = 2)

public abstract class Database extends RoomDatabase {

    public abstract CartDao cartDao();
    public abstract FavoriteDao FavoriteDao();

    private static Database instance;

    public static Database getInstance(Context context)
    {
        if(instance==null)
      {instance= Room.databaseBuilder(context,Database.class,"Drink_Shop").allowMainThreadQueries().build();
      }
      return instance;
    }

}
