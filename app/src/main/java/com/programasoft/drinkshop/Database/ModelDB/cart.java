package com.programasoft.drinkshop.Database.ModelDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

/**
 * Created by ASUS on 22/12/2018.
 */
@Entity(tableName = "cart")
public class cart  {
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAcount(int acount) {
        this.acount = acount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAcount() {
        return acount;
    }

    public double getPrice() {
        return price;
    }

    public int getSugar() {
        return sugar;
    }

    public int getIce() {
        return ice;
    }

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "link")
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @ColumnInfo(name = "acount")
    private int acount;

    @ColumnInfo(name = "price")
    private double price;


    @ColumnInfo(name = "sugar")
    private int sugar;


    @ColumnInfo(name = "ice")
    private int ice;

}
