package com.programasoft.drinkshop.retrofit;

import com.programasoft.drinkshop.model.banner;
import com.programasoft.drinkshop.model.checkExistUserResponse;
import com.programasoft.drinkshop.model.drink;
import com.programasoft.drinkshop.model.order;
import com.programasoft.drinkshop.model.store;
import com.programasoft.drinkshop.model.token;
import com.programasoft.drinkshop.model.user;
import com.programasoft.drinkshop.model.menu;

import java.util.List;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by ASUS on 16/12/2018.
 */

public interface IDrinkShopApi {

    @FormUrlEncoded
    @POST("checkExistUser.php")
    Call<checkExistUserResponse> checkExistUser(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("RegisterUser.php")
    Call<user> RegisterUser(@Field("phone") String phone,@Field("name") String name,@Field("birthdate") String birthdate,@Field("address") String address);



    @POST("RegisterUser2.php")
    Call<user> RegisterUser2(@Body user user);

    @FormUrlEncoded
    @POST("getUserInformation.php")
    Call<user> getUserInformation(@Field("phone") String phone);


    @GET("GetBanners.php")
    io.reactivex.Observable<List<banner>> GetBanners();

    @GET("GetMenu.php")
    io.reactivex.Observable<List<menu>> GetMenu();

    @FormUrlEncoded
    @POST("GetDrinks.php")
    io.reactivex.Observable<List<drink>> GetDrinks(@Field("menu_id") String menu_id);

    @Multipart
    @POST("UploadAvar.php")
    Call<String> UploadAvar(@Part("phone") RequestBody phone, @Part MultipartBody.Part file);

    @GET("GetAllDrinks.php")
    io.reactivex.Observable<List<drink>> GetAllDrinks();

    @FormUrlEncoded
    @POST("InsertNewOrder.php")
    Call<String> InsertNewOrder(@Field("status") int status,@Field("price") double price,@Field("amount") int amount,@Field("name") String name,@Field("ice") int ice,@Field("sugar") int sugar,@Field("link") String link,@Field("comment") String comment,@Field("id_user") String id_user,@Field("address") String address);

    @GET("user_avar/{image_name}")
    Call<ResponseBody> DowleadPhotoProfil(@Path("image_name") String image_name);

    @Streaming
    @GET("user_avar/{image_name}")
    Call<ResponseBody> DowleadPhotoProfilStrea(@Path("image_name") String image_name);

    @GET("GetOrdersByUserAndStatus.php")
    Call<List<order>> GetOrdersByUserAndStatus(@Query("phone") String phone,@Query("status") int status);


    @FormUrlEncoded
    @POST("InsertUpdateToken.php")
    Call<token> InsertUpdateToken(@Field("Phone") String Phone,@Field("Token") String Token , @Field("IsServerToken") boolean IsServerToken);

    @FormUrlEncoded
    @POST("UpdateStatusOrder.php")
    Call<Boolean> UpdateStatusOrder(@Field("id") int id,@Field("status") int status);

    @GET("GetNearbyStore.php")
    Call<List<store>> GetNearbyStore(@Query("latitude") double latitude,@Query("longitude") double longitude);

}
