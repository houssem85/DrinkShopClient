package com.programasoft.drinkshop.Utils;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.programasoft.drinkshop.Adapter.CartViewHolder;

/**
 * Created by ASUS on 25/12/2018.
 */

public class RecycleItemTouchHelper extends ItemTouchHelper.SimpleCallback {


    private RecycleItemTouchHelperListner listner;

    public RecycleItemTouchHelper(int dragDirs, int swipeDirs,RecycleItemTouchHelperListner listner) {
        super(dragDirs, swipeDirs);
        this.listner=listner;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(listner!=null)
        {listner.OnSwipe(viewHolder,direction,viewHolder.getAdapterPosition());
        }

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(viewHolder!=null) {
            View forgorund = ((CartViewHolder) viewHolder).view_forground;
            getDefaultUIUtil().clearView(forgorund);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder!=null)
        {   View forgorund=((CartViewHolder)viewHolder).view_forground;
            getDefaultUIUtil().onSelected(forgorund);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(viewHolder!=null)
        {   View forgorund=((CartViewHolder)viewHolder).view_forground;
            getDefaultUIUtil().onDraw(c,recyclerView,forgorund,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(viewHolder!=null)
        {   View forgorund=((CartViewHolder)viewHolder).view_forground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,forgorund,dX,dY,actionState,isCurrentlyActive);
        }
    }
}
