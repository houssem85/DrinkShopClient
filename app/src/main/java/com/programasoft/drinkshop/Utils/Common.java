package com.programasoft.drinkshop.Utils;

import com.programasoft.drinkshop.model.user;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;
import com.programasoft.drinkshop.retrofit.RetrofitClient;

/**
 * Created by ASUS on 16/12/2018.
 */

public class Common {
    public static final String BASE_URL = "https://houssemdaou85.000webhostapp.com/DrinkShop/";
    public static user CorrentUser=null;

    public static IDrinkShopApi getApi()
    {return RetrofitClient.getClient(BASE_URL).create(IDrinkShopApi.class);

    }





}