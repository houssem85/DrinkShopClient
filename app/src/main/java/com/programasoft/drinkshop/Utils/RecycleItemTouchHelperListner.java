package com.programasoft.drinkshop.Utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by ASUS on 25/12/2018.
 */

public interface RecycleItemTouchHelperListner {

    void OnSwipe(RecyclerView.ViewHolder viewHolder,int direction,int position);
}
