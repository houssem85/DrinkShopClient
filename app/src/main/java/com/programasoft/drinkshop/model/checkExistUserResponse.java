package com.programasoft.drinkshop.model;

/**
 * Created by ASUS on 16/12/2018.
 */

public class checkExistUserResponse {
    private boolean existe;
    private String error;

    public checkExistUserResponse() {
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
